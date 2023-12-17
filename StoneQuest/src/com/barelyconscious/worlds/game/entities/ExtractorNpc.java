package com.barelyconscious.worlds.game.entities;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.entity.components.NametagComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;

public class ExtractorNpc extends Actor {

    public ExtractorNpc(Vector transform) {
        super("Extractor", transform);

        addComponent(new SpriteComponent(this,
            new BetterSpriteResource("entities::extractor_npc1"),
            RenderLayer.ENTITIES));
        addComponent(new NametagComponent(this, Box.square(64)));

        var mlc = new MouseListenerComponent(this, Box.square(64));
        mlc.delegateOnMouseClicked.bindDelegate((eventArgs) -> {
            System.out.println("Clicked on extractor");
            return null;
        });
        addComponent(mlc);
    }
}
