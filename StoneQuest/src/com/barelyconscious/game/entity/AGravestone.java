package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Vector;

public class AGravestone extends Actor {

    public AGravestone(Vector transform) {
        super(transform);
        addComponent(new SpriteComponent(
            this,
            Resources.getSprite(ResourceSprite.GRAVESTONE),
            RenderLayer.DOODADS));
    }
}
