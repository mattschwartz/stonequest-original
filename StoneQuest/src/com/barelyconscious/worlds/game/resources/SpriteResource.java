package com.barelyconscious.worlds.game.resources;

import com.barelyconscious.worlds.engine.graphics.RenderLayer;

public interface SpriteResource {

    String getName();

    Region getRegion();

    RenderLayer getRenderLayer();

    default int getWidth() {
        return getRegion().getWidth();
    }

    default int getHeight() {
        return getRegion().getHeight();
    }
}
