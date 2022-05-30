package com.barelyconscious.game.entity;

import java.awt.*;

public class RenderContext {

    private final Graphics2D graphics2D;
    public final Camera camera;

    public RenderContext(
        final Graphics2D graphics2D,
        final Camera camera
    ) {
        this.graphics2D = graphics2D;
        this.camera = camera;
    }

    private static final Color DEBUG_COLOR = Color.red;

    public void debugRenderBox(final int startX, final int startY, final int width, final int height) {
        final Color prev = graphics2D.getColor();

        final int x = startX - camera.getViewX();
        final int y = startY - camera.getViewY();

        if (inBounds(startX, startY)) {
            graphics2D.setColor(DEBUG_COLOR);
            graphics2D.drawRect(
                x,
                y,
                width,
                height);
            graphics2D.setColor(prev);
        }
    }

    /**
     * Renders an image to the screen with the given coordinates and size.
     *
     * @param xStart the top-left corner of the image
     * @param yStart the top-left corner of the image
     */
    public void render(
        final Image image,
        final int xStart,
        final int yStart,
        final int width,
        final int height
    ) {
        final int x = xStart - camera.getViewX();
        final int y = yStart - camera.getViewY();

        if (inBounds(xStart, yStart)) {
            graphics2D.drawImage(image, x, y, width, height, null);
        }
    }

    public boolean setPixel(final Color color, int x, int y) {
        x += camera.getViewX();
        y += camera.getViewY();

        if (!inBounds(x, y)) {
            return false;
        }

        final Color prevColor = graphics2D.getColor();

        graphics2D.setColor(color);
        graphics2D.drawLine(x, y, x, y);

        graphics2D.setColor(prevColor);

        return true;
    }

    // package private bc easier...
    private boolean isDisposing = false;

    void dispose() {
        if (graphics2D != null && !isDisposing) {
            graphics2D.dispose();
            isDisposing = true;
        }
    }

    // todo: will not render partial views where x starts outside, but part of that image should still be drawn
    private boolean inBounds(final int xStart, int yStart) {
        return camera.getWorldBounds().contains(xStart, yStart);
    }
}
