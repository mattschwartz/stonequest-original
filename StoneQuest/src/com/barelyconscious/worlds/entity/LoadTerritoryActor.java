package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.CollisionData;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.rng.TerritoryGeneration;
import com.barelyconscious.worlds.game.systems.GuiSystem;

import java.awt.event.MouseEvent;

/**
 * todo: how to reload territories visited by the player so the state is tracked
 */
public class LoadTerritoryActor extends Actor {
    private int numHeroesNearby = 0;
    private final Object lock = new Object();
    private final int size = 32;
    private final MouseListenerComponent mouse;
    private final SpriteComponent sprite;

    public LoadTerritoryActor(Vector transform) {
        super("Load Territory", transform);

        sprite = new SpriteComponent(this,
            new BetterSpriteResource("gui::world_move_btn"),
            RenderLayer.SKY);
        sprite.setOpacity(0.3);

        mouse = new MouseListenerComponent(this, Box.square(size));

        mouse.delegateOnMouseOver.bindDelegate(this::onMouseOver);
        mouse.delegateOnMouseClicked.bindDelegate(this::onMouseClicked);

        var collider = new BoxColliderComponent(this, false, true, new Box(
            -size, size * 3,
            -size, size * 3
        ));
        collider.delegateOnEnter.bindDelegate(this::onEnter);
        collider.delegateOnLeave.bindDelegate(this::onLeave);

        addComponent(sprite);
        addComponent(mouse);
        addComponent(collider);
    }

    private void updateTooltip() {
        var gui = GameInstance.instance()
            .getSystem(GuiSystem.class);
        gui.showTooltip(
            "Move to a new territory",
            numHeroesNearby == 0 ? "Move closer" : null,
            numHeroesNearby == 0 ? null : "Click to move",
            null);

    }

    private Void onEnter(CollisionData collisionData) {
        if (collisionData.causedByActor instanceof Hero) {
            synchronized (lock) {
                numHeroesNearby++;
                updateTooltip();
            }
            sprite.setOpacity(1);
        }
        return null;
    }

    private Void onLeave(Actor actor) {
        if (actor instanceof Hero) {
            synchronized (lock) {
                numHeroesNearby--;
                updateTooltip();
            }
            if (numHeroesNearby == 0) {
                sprite.setOpacity(0.3);
            }
        }

        return null;
    }

    private Void onMouseOver(boolean isMouseOver) {
        sprite.setOpacity(isMouseOver ? 1 : 0.6);
        if (isMouseOver) {
            updateTooltip();
        } else {
            var gui = GameInstance.instance()
                .getSystem(GuiSystem.class);
            gui.hideTooltip();
        }
        return null;
    }

    private Void onMouseClicked(MouseEvent mouseEvent) {
        if (numHeroesNearby > 0) {
            WildernessLevel wild = new TerritoryGeneration()
                .generateTerritory(GameInstance.instance().getWorld()
                    .getTerritories().get(1));

            GameInstance.instance().getWorld()
                .setWildernessLevel(wild);
        }

        return null;
    }
}
