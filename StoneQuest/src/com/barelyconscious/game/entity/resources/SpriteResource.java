package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.entity.graphics.RenderLayer;

public interface SpriteResource {

    String getName();
    Region getRegion();
    RenderLayer getRenderLayer();
}
