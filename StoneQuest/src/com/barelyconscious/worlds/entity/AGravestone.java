package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.entity.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.resources.ResourceSprite;
import com.barelyconscious.worlds.entity.resources.Resources;
import com.barelyconscious.worlds.shape.Vector;

public class AGravestone extends Actor {

    public AGravestone(Vector transform) {
        super(transform);
        addComponent(new SpriteComponent(
            this,
            Resources.getSprite(ResourceSprite.GRAVESTONE),
            RenderLayer.DOODADS));
    }
}
