package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.rng.TerritoryGeneration;
import com.barelyconscious.worlds.game.systems.GuiSystem;

/**
 * todo: how to reload territories visited by the player so the state is tracked
 */
public class LoadTerritoryActor extends Actor {

    private int size = 32;

    private final MouseListenerComponent mouse;

    private final SpriteComponent sprite;

    public LoadTerritoryActor(Vector transform) {
        super("Load Territory", transform);

        sprite = new SpriteComponent(this,
            new BetterSpriteResource("gui::world_move_btn"),
            RenderLayer.SKY);
        sprite.setOpacity(0.3);

        mouse = new MouseListenerComponent(this, Box.square(size));
        mouse.delegateOnMouseOver.bindDelegate((isMouseOver) -> {
            sprite.setOpacity(isMouseOver ? 1 : 0.6);
            if (isMouseOver) {
                var gui = GameInstance.instance()
                    .getSystem(GuiSystem.class);
                gui.showTooltip(
                    "Move to a new territory",
                    null,
                    "Click to move",
                    null);
            } else {
                var gui = GameInstance.instance()
                    .getSystem(GuiSystem.class);
                gui.hideTooltip();
            }
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

        var collider = new BoxColliderComponent(this, false, true, new Box(
            -size, size * 3,
            -size, size * 3
        ));
        addComponent(collider);

        addComponent(mouse);
        addComponent(sprite);
    }
}
