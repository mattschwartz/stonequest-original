package com.barelyconscious.worlds.entity.resources;

import com.barelyconscious.worlds.entity.graphics.RenderLayer;

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
