package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Vector;

public class AGravestone extends Actor {

    public AGravestone(Vector transform) {
        super(transform);
        addComponent(new SpriteComponent(
            this,
            Resources.getSprite(ResourceSprite.GRAVESTONE),
            RenderLayer.DOODADS));
    }
}
