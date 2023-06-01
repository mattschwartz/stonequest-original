package com.barelyconscious.worlds.engine.graphics;

import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.game.resources.FontResource;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.common.UColor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.GrayFilter;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.util.EnumMap;
import java.util.Map;

/**
 * An accessor for drawing to the screen. Components can render to specific layers which the
 * RenderContext will collapse into a single image to be drawn to the screen.
 * <p>
 * <p>
 * TODO(idea): commit draws to an internal buffer of instructions which the Screen can interpret
 *  and determine how best to apply these draws to the canvas. Can help abstract away the underlying
 *  rendering implementation as well.
 */
public class RenderContext implements IRenderContext {

    private static final Color DEBUG_COLOR = Color.red;

    @Getter
    private final BufferStrategy bufferStrategy;
    public final Camera camera;

    public FontContext getFontContext() {
        return new FontContext(
            Color.yellow,
            FontResource.FONTIN_REGULAR,
            RenderLayer.GUI,
            this);
    }

    private final Map<RenderLayer, BufferedImage> renderByLayer;
    private final Map<RenderLayer, Graphics2D> graphicsByLayer;

    public RenderContext(
        final BufferStrategy bufferStrategy,
        final Camera camera
    ) {
        this.bufferStrategy = bufferStrategy;
        this.camera = camera;

        renderByLayer = new EnumMap<>(RenderLayer.class);
        graphicsByLayer = new EnumMap<>(RenderLayer.class);

        for (final RenderLayer layer : RenderLayer.values()) {
            final BufferedImage renderedImage = new BufferedImage(
                camera.getScreenWidth(),
                camera.getScreenHeight(),
                BufferedImage.TYPE_INT_ARGB);
            renderByLayer.put(layer,
                renderedImage);
            graphicsByLayer.put(layer, renderedImage.createGraphics());
        }
    }

    /**
     * @return a rendered image applying renders to every RenderLayer
     */
    BufferedImage getRenderedImage() {
        final BufferedImage prelitRender = new BufferedImage(
            camera.getScreenWidth(),
            camera.getScreenHeight(),
            BufferedImage.TYPE_INT_ARGB);

        final Graphics g = prelitRender.createGraphics();
        for (final RenderLayer layer : RenderLayer.layersToBeLit()) {
            g.drawImage(renderByLayer.get(layer), 0, 0, camera.getScreenWidth(), camera.getScreenHeight(), null);
        }
        g.dispose();

        final BufferedImage renderedImage = new BufferedImage(
            camera.getScreenWidth(),
            camera.getScreenHeight(),
            BufferedImage.TYPE_INT_ARGB);

        BufferedImage lightmap = renderByLayer.get(RenderLayer.LIGHTMAP);

        int[] prelitPixels = ((DataBufferInt) prelitRender.getRaster().getDataBuffer()).getData();
        int[] lightPixels = ((DataBufferInt) lightmap.getRaster().getDataBuffer()).getData();
        int[] renderedImagePixels = ((DataBufferInt) renderedImage.getRaster().getDataBuffer()).getData();

        // TODO hopefully temporary because resizing kinda crashes the program otherwise
        if (prelitPixels.length == lightPixels.length) {
            for (int i = 0; i < prelitPixels.length; ++i) {
                final Color prelitColor = UColor.toColor(prelitPixels[i]);
                final Color lightColor = UColor.toColor(lightPixels[i]);
                final Color maxColor = UColor.combine(prelitColor, lightColor);

                renderedImagePixels[i] = maxColor.getRGB();
            }
        }

        final Graphics gg = renderedImage.createGraphics();
        for (final RenderLayer layer : RenderLayer.layersAboveLight()) {
            gg.drawImage(renderByLayer.get(layer), 0, 0, camera.getScreenWidth(), camera.getScreenHeight(), null);
        }
        gg.dispose();

        return renderedImage;
    }

    Graphics getGraphics(final RenderLayer renderLayer) {
        return graphicsByLayer.get(renderLayer);
    }

    public void debugRenderBox(final Color color, final int worldX, final int worldY, final int width, final int height) {
        final Graphics graphics = graphicsByLayer.get(RenderLayer._DEBUG);
        final Color prev = graphics.getColor();

        final Vector screenPos = camera.worldToScreenPos(worldX, worldY);
        if (inBounds(worldX, worldY, width, height)) {
            graphics.setColor(color);
            graphics.drawRect(
                (int) screenPos.x,
                (int) screenPos.y,
                width,
                height);
            graphics.setColor(prev);
        }
    }

    /**
     * Draws a box to the debug layer using the default debug color.
     */
    public void debugRenderBox(final int worldX, final int worldY, final int width, final int height) {
        debugRenderBox(DEBUG_COLOR, worldX, worldY, width, height);
    }

    /**
     * Renders an image to the screen with the given coordinates and size.
     *
     * @param worldX the top-left corner of the image
     * @param worldY the top-left corner of the image
     */
    public void render(
        @NonNull final Image image,
        final int worldX,
        final int worldY,
        final int width,
        final int height,
        @NonNull final RenderLayer layer
    ) {
        final Graphics graphics = graphicsByLayer.get(layer);
        final Vector screenPos = camera.worldToScreenPos(worldX, worldY);

        if (inBounds(worldX, worldY, width, height)) {
            graphics.drawImage(image, (int) screenPos.x, (int) screenPos.y, width, height, null);
        }
    }

    @Builder
    public static class RenderRequest {

        private final Resources.Sprite_Resource spriteResource;
        private final int width;
        private final int height;
        private final int worldX;
        private final int worldY;
        private final float renderOpacity;
        private final RenderLayer renderLayer;
    }

    public void render(final RenderRequest rr) {
        final Graphics2D g = graphicsByLayer.get(rr.renderLayer);
        final Vector screenPos = camera.worldToScreenPos(rr.worldX, rr.worldY);

        if (inBounds(rr.worldX, rr.worldY, rr.width, rr.height)) {
            Composite prevComposite = g.getComposite();

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rr.renderOpacity));

            g.drawImage(rr.spriteResource.getTexture(), (int) screenPos.x, (int) screenPos.y, rr.width, rr.height, null);

            g.setComposite(prevComposite);
        }
    }

    public void renderGui(
        final Image image,
        final Box screenBounds
    ) {
        renderImage(image, screenBounds, RenderLayer.GUI);
    }

    public void renderImage(
        final Image image,
        final Box screenBounds,
        final RenderLayer renderLayer
    ) {
        final Graphics g = graphicsByLayer.get(renderLayer);
        g.drawImage(image, screenBounds.left, screenBounds.top, screenBounds.width, screenBounds.height, null);
    }

    public void renderGuiRect(
        final Color color,
        final boolean fill,
        final Box screenBounds
    ) {
        this.renderGuiRect(color, fill, screenBounds.left, screenBounds.top,
            screenBounds.width, screenBounds.height);
    }

    public void renderGuiGrayscale(
        final Image image,
        final Box screenBounds
    ) {
        ImageFilter filter = new GrayFilter(true, 50);
        ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
        final Image grayscale = Toolkit.getDefaultToolkit().createImage(producer);

        final Graphics graphics = graphicsByLayer.get(RenderLayer.GUI);
        graphics.drawImage(grayscale, screenBounds.left, screenBounds.top,
            screenBounds.width, screenBounds.height, null);
    }

    public void renderGuiRect(
        final Color color,
        final boolean fill,
        final int screenX,
        final int screenY,
        final int width,
        final int height
    ) {
        final Graphics g = graphicsByLayer.get(RenderLayer.GUI);

        final Color prev = g.getColor();

        g.setColor(color);
        if (fill) {
            g.fillRect(screenX, screenY, width, height);
        } else {
            g.drawRect(screenX, screenY, width, height);
        }

        g.setColor(prev);
    }

    public void renderRect(final Color color, final boolean fill, final Box screenBounds, final RenderLayer layer) {
        final Vector worldPos = camera.screenToWorldPos(new Vector(screenBounds.left, screenBounds.top));
        this.renderRect(color, fill, (int) worldPos.x, (int) worldPos.y, screenBounds.width, screenBounds.height, layer);
    }

    public void renderCircle(final Color color, final boolean fill, final int worldX, final int worldY,
                             final int radius, final RenderLayer layer) {
        final Graphics g = graphicsByLayer.get(layer);
        if (inBounds(worldX, worldY, radius * 2, radius * 2)) {
            final Color prevColor = g.getColor();
            final Vector screenPos = camera.worldToScreenPos(worldX, worldY);
            g.setColor(color);
            if (fill) {
                g.fillOval(
                    (int) screenPos.x,
                    (int) screenPos.y,
                    radius * 2, radius * 2
                );
            } else {
                g.drawOval(
                    (int) screenPos.x,
                    (int) screenPos.y,
                    radius * 2, radius * 2
                );
            }
        }
    }

    public void renderRect(
        final Color color,
        final boolean fill,
        final int worldX,
        final int worldY,
        final int width,
        final int height,
        final RenderLayer layer
    ) {
        final Graphics g = graphicsByLayer.get(layer);

        if (inBounds(worldX, worldY, width, height)) {
            final Color prev = g.getColor();
            final Vector screenPos = camera.worldToScreenPos(worldX, worldY);

            g.setColor(color);
            if (fill) {
                g.fillRect((int) screenPos.x, (int) screenPos.y, width, height);
            } else {
                g.drawRect((int) screenPos.x, (int) screenPos.y, width, height);
            }

            g.setColor(prev);
        }
    }

    private boolean isDisposing = false;

    void dispose() {
        if (!isDisposing) {
            graphicsByLayer.values().forEach(Graphics::dispose);

            isDisposing = true;
        }
    }

    private boolean inBounds(final int worldX, final int worldY, final int width, final int height) {
        return camera.getWorldBounds()
            .intersects(new Box(worldX, worldX + width, worldY, worldY + height));
    }
}
