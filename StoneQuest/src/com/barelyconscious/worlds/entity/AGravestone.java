package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.entity.components.NametagComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Vector;

public class AGravestone extends Actor {

    public AGravestone(String name, Vector transform) {
        super(name, transform);
        addComponent(new SpriteComponent(
            this,
            new BetterSpriteResource("doodads::blood_splatter"),
            RenderLayer.DOODADS));
        addComponent(new NametagComponent(this, Box.square(32)));
    }
}
