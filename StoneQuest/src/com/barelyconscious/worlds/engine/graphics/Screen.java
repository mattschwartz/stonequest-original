package com.barelyconscious.worlds.engine.graphics;

import com.barelyconscious.worlds.engine.Camera;
import lombok.AllArgsConstructor;

public interface Screen {

    Camera getCamera();

    int getWidth();

    int getHeight();

    void resizeScreen(int newWidth, int newHeight);

    void clear();

    RenderContext createRenderContext();

    void render(RenderContext renderContext);

    @AllArgsConstructor
    public static final class ResizeEvent {
        public final int prevWidth;
        public final int prevHeight;
        public final int newWidth;
        public final int newHeight;
    }
}
