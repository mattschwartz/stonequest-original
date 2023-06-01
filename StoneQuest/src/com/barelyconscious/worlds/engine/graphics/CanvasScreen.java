package com.barelyconscious.worlds.engine.graphics;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.engine.Camera;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.val;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public final class CanvasScreen implements Screen {

    public final Delegate<ResizeEvent> onResize = new Delegate<>();

    private final Camera camera;
    @Getter
    private final Canvas canvas;
    private BufferedImage viewport;

    public CanvasScreen() {
        camera = null;
        canvas = null;
    }

    public CanvasScreen(
        final Canvas canvas,
        final int width,
        final int height
    ) {
        this.canvas = canvas;
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

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public int getWidth() {
        return canvas.getWidth();
    }

    @Override
    public int getHeight() {
        return canvas.getHeight();
    }

    @Override
    public synchronized void resizeScreen(final int newWidth, final int newHeight) {
        synchronized (this) {
            final int prevWidth = getWidth();
            final int prevHeight = getHeight();

            canvas.setSize(newWidth, newHeight);
            viewport = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_ARGB);
            camera.resize(newWidth, newHeight);

            onResize.call(new ResizeEvent(prevWidth, prevHeight, newWidth, newHeight));
        }
    }

    @Override
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
    @Override
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
    @Override
    public synchronized void render(final RenderContext renderContext) {
        synchronized (this) {
            final BufferStrategy bufferStrategy = renderContext.getBufferStrategy();
            Graphics2D g = null;

            do {
                try {
                    g = (Graphics2D) bufferStrategy
                        .getDrawGraphics();
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.drawImage(
                        renderContext.getRenderedImage(),
                        0, 0,
                        getWidth(), getHeight(),
                        null);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
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
