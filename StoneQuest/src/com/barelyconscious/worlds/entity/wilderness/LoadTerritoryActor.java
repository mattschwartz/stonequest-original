package com.barelyconscious.worlds.entity.wilderness;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.CollisionData;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.systems.GuiSystem;
import com.barelyconscious.worlds.game.systems.WildernessSystem;

import java.awt.event.MouseEvent;

/**
 * todo: how to reload territories visited by the player so the state is tracked
 * <p>
 * todo(bug): you can click to load the territory, move the hero away without moving the mouse,
 *  and then click again to load the territory again
 *  i think this bug is because the mouse component is not being removed at the right time
 */
public class LoadTerritoryActor extends Actor {
    private int numHeroesNearby = 0;
    private final Object lock = new Object();
    private final int size = 32;
    private final MouseListenerComponent mouse;
    private final SpriteComponent sprite;
    private final Vector facing;
    private final Territory territoryToLoad;

    public LoadTerritoryActor(
        Territory territoryToLoad,
        Vector transform,
        Vector facing
    ) {
        super("Load Territory", transform);

        this.territoryToLoad = territoryToLoad;
        this.facing = facing;

        String spriteName;
        if (facing == Vector.LEFT) {
            spriteName = "gui::world_move_btn_left";
        } else if (facing == Vector.RIGHT) {
            spriteName = "gui::world_move_btn_right";
        } else if (facing == Vector.UP) {
            spriteName = "gui::world_move_btn_up";
        } else if (facing == Vector.DOWN) {
            spriteName = "gui::world_move_btn_down";
        } else {
            throw new IllegalArgumentException("Invalid facing: " + facing);
        }
        sprite = new SpriteComponent(this,
            new BetterSpriteResource(spriteName),
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
            var currentTerritory = GameInstance.instance().getWorld()
                .getWildernessLevel().getTerritory();

            var sys = GameInstance.instance()
                .getSystem(WildernessSystem.class);
            var wild = sys.getWildernessLevel(
                currentTerritory.getTransform(),
                territoryToLoad.getTransform(),
                territoryToLoad.getTerritoryLevel());

            GameInstance.instance().getWorld()
                .setWildernessLevel(wild);
        }

        return null;
    }
}
