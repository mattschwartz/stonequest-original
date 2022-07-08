package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Camera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public final class Screen {

    public final Delegate<ResizeEvent> onResize = new Delegate<>();

    @AllArgsConstructor
    public static final class ResizeEvent {
        public final int prevWidth;
        public final int prevHeight;
        public final int newWidth;
        public final int newHeight;
    }

    private final Camera camera;
    @Getter
    private final Canvas canvas;
    private BufferedImage viewport;

    public Screen(
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
                    g.setColor(Color.black);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.drawImage(
                        renderContext.getRenderedImage(),
                        0, 0,
                        getWidth(), getHeight(),
                        null);
                    g.setColor(Color.WHITE);
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
