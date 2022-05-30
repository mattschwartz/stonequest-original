package com.barelyconscious.game.entity;

import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public final class Screen {

    private final Camera camera;
    private final Canvas canvas;
    private BufferedImage viewport;

    public Screen(
        final int width,
        final int height
    ) {
        this.canvas = new Canvas();
        this.canvas.setSize(width, height);
        this.camera = new Camera(width, height);

        viewport = new BufferedImage(
            width,
            height,
            BufferedImage.TYPE_INT_ARGB);
    }

    public void addToFrame(final JFrame frame) {
        frame.add(canvas);
    }

    public Camera getCamera() {
        return camera;
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public synchronized void resizeScreen(final int newWidth, final int newHeight) {
        synchronized (this) {
            canvas.setSize(newWidth, newHeight);
            viewport = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_ARGB);
            camera.resize(newWidth, newHeight);
        }
    }

    public synchronized void clear() {
        val g = viewport.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
    }

    public synchronized RenderContext createRenderContext() {
        return new RenderContext(viewport.createGraphics(), camera);
    }

    /**
     * Commits the new render to the screen.
     *
     * @param renderContext the render context containing all new draws.
     */
    public synchronized void render(final RenderContext renderContext) {
        synchronized (this) {
            final BufferStrategy bufferStrategy = canvas.getBufferStrategy();
            if (bufferStrategy == null) {
                canvas.createBufferStrategy(3);
                canvas.requestFocus();
                return;
            }

            final Graphics bufferGraphics = bufferStrategy.getDrawGraphics();
            bufferGraphics.setColor(Color.black);
            bufferGraphics.fillRect(0, 0, getWidth(), getHeight());
            bufferGraphics.drawImage(viewport,
                0, 0,
                getWidth(), getHeight(),
                null);

            bufferGraphics.dispose();
            renderContext.dispose();
            bufferStrategy.show();
        }
    }
}
