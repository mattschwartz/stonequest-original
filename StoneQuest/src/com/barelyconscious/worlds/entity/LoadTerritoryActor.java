package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.rng.TerritoryGeneration;
import com.barelyconscious.worlds.game.types.Biome;
import com.barelyconscious.worlds.game.types.Climate;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import com.barelyconscious.worlds.gamedata.GameItems;
import com.google.common.collect.Lists;

public class LoadTerritoryActor extends Actor {

    private int size = 32;

    private final MouseListenerComponent mouse;

    public LoadTerritoryActor(Vector transform) {
        super("Load Territory", transform);

        mouse = new MouseListenerComponent(this, Box.square(size));
        mouse.delegateOnMouseOver.bindDelegate((args) -> {
            return null;
        });
        mouse.delegateOnMouseClicked.bindDelegate((args) -> {
            WildernessLevel wild = new TerritoryGeneration()
                .generateTerritory(GameInstance.instance().getWorld()
                    .getTerritories().get(1));

            GameInstance.instance().getWorld()
                .setWildernessLevel(wild);
            return null;
        });

        addComponent(mouse);
        addComponent(new SpriteComponent(this,
            new BetterSpriteResource("gui::world_move_btn"),
            RenderLayer.SKY));
    }
}
