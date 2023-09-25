package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.LightSourceComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.engine.input.InputLayer;
import com.barelyconscious.worlds.engine.input.Interactable;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.GameInstance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.awt.event.MouseEvent;

public class TileActor extends Actor implements Interactable {

    @Getter
    private Tile tile;
    private BetterSpriteResource spriteResource;
    private final SpriteComponent spriteComponent;

    private Box mouseCaptureBounds;

    @AllArgsConstructor
    public enum OpacityPresets {
        NOT_VISIBLE(0),
        HEAVILY_OBSCURED(0.25f),
        LIGHTLY_OBSCURED(0.5f),
        VISIBLE(.88f);

        private final float opacity;
    }

    public TileActor(
        final Vector transform,
        @NonNull final Tile tile,
        final BetterSpriteResource spriteResource,
        final int width,
        final int height,
        final MouseInputHandler mouseInputHandler
    ) {
        super(tile.getName(), transform);
        this.tile = tile;
        this.spriteResource = spriteResource;
        this.spriteComponent = new SpriteComponent(this, spriteResource, width, height);

        final BoxColliderComponent lightReceiver = new BoxColliderComponent(this, tile.isBlocksMovement(), true, new Box(0, width, 0, height));
        lightReceiver.delegateOnEnter.bindDelegate(collisionData -> {
            Component component = collisionData.triggeredByComponent;
            if (component instanceof LightSourceComponent) {
                spriteComponent.setOpacity(0f);
            }
            return null;
        });
        addComponent(lightReceiver);

        configure(width, height);
        mouseInputHandler.registerInteractable(this, InputLayer.GAME_WORLD);
    }

    private void configure(final int width, final int height) {
        addComponent(spriteComponent);
        mouseCaptureBounds = new Box(
            0, width,
            0, height);
    }

    private boolean isMouseOver = false;

    @Override
    public boolean contains(final int screenX, final int screenY) {
        if (GameInstance.instance() != null) {
            final Camera camera = GameInstance.instance().getCamera();
            if (camera != null) {
                final Vector screenPos = camera.worldToScreenPos(getTransform());
                if (screenPos != null) {
                    return mouseCaptureBounds.boxAtPosition(screenPos).contains(screenX, screenY);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isMouseOver() {
        return isMouseOver;
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        isMouseOver = true;
        return false;
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        isMouseOver = false;
        return false;
    }
}
