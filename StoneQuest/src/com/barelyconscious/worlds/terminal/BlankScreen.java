package com.barelyconscious.worlds.terminal;

import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.Screen;

public class BlankScreen implements Screen {

    static class MockRenderContext extends RenderContext {

        public MockRenderContext(Camera camera) {
            super(null, camera);
        }
    }

    final Camera blindCamera = new Camera(0, 0);

    @Override
    public Camera getCamera() {
        return blindCamera;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void resizeScreen(int newWidth, int newHeight) {

    }

    @Override
    public void clear() {

    }

    @Override
    public RenderContext createRenderContext() {
        return new MockRenderContext(blindCamera);
    }

    @Override
    public void render(RenderContext renderContext) {
    }
}
