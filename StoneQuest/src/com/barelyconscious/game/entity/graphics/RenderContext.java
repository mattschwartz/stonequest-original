package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.entity.Camera;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

/**
 * An accessor for drawing to the screen. Components can render to specific layers which the
 * RenderContext will collapse into a single image to be drawn to the screen.
 *
 *
 * TODO(idea): commit draws to an internal buffer of instructions which the Screen can interpret
 *  and determine how best to apply these draws to the canvas. Can help abstract away the underlying
 *  rendering implementation as well.
 */
public class RenderContext {

    private static final Color DEBUG_COLOR = Color.red;

    @Getter
    private final BufferStrategy bufferStrategy;
    public final Camera camera;

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
        final BufferedImage renderedImage = new BufferedImage(
            camera.getScreenWidth(),
            camera.getScreenHeight(),
            BufferedImage.TYPE_INT_ARGB);

        final Graphics g = renderedImage.createGraphics();
        for (final RenderLayer layer : RenderLayer.values()) {
            g.drawImage(renderByLayer.get(layer), 0, 0, camera.getScreenWidth(), camera.getScreenHeight(), null);
        }
        g.dispose();

        return renderedImage;
    }

    /**
     * Draws a box to the debug layer using the default debug color.
     */
    public void debugRenderBox(final int worldX, final int worldY, final int width, final int height) {
        final Graphics graphics = graphicsByLayer.get(RenderLayer._DEBUG);
        final Color prev = graphics.getColor();

        final Vector screenPos = camera.worldToScreenPos(worldX, worldY);
        if (inBounds(worldX, worldY, width, height)) {
            graphics.setColor(DEBUG_COLOR);
            graphics.drawRect(
                (int) screenPos.x,
                (int) screenPos.y,
                width,
                height);
            graphics.setColor(prev);
        }
    }

    /**
     * Renders an image to the screen with the given coordinates and size.
     *
     * @param worldX the top-left corner of the image
     * @param worldY the top-left corner of the image
     */
    public void render(
        final Image image,
        final int worldX,
        final int worldY,
        final int width,
        final int height,
        final RenderLayer layer
    ) {
        final Graphics graphics = graphicsByLayer.get(layer);
        final Vector screenPos = camera.worldToScreenPos(worldX, worldY);

        if (inBounds(worldX, worldY, width, height)) {
            graphics.drawImage(image, (int) screenPos.x, (int) screenPos.y, width, height, null);
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
                g.fillRect((int)screenPos.x, (int)screenPos.y, width, height);
            } else {
                g.drawRect((int)screenPos.x, (int)screenPos.y, width, height);
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