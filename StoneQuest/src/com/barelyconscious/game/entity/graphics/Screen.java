package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.entity.Camera;
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

    /**
     * Creates a RenderContext that can be used by components to render to the screen.
     * @return the new render context
     */
    public synchronized RenderContext createRenderContext() {
        BufferStrategy bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(3);
            canvas.requestFocus();
            bs = canvas.getBufferStrategy();
        }

        return new RenderContext(bs, camera);
    }

    /**
     * Commits the new render to the screen.
     *
     * @param renderContext the render context containing all new draws.
     */
    public synchronized void render(final RenderContext renderContext) {
        synchronized (this) {
            final BufferStrategy bufferStrategy = renderContext.getBufferStrategy();
            Graphics2D g = null;

            do {
                try {
                    g = (Graphics2D) bufferStrategy
                        .getDrawGraphics();
                    g.drawImage(
                        renderContext.getRenderedImage(),
                        0, 0,
                        getWidth(), getHeight(),
                        null);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
                bufferStrategy.show();
            } while (bufferStrategy.contentsLost());

            renderContext.dispose();
        }
    }
}
