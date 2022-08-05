package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.Interactable;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.tile.Tile;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.awt.event.MouseEvent;

public class TileActor extends Actor implements Interactable {

    @Getter
    private Tile tile;
    private Resources.Sprite_Resource spriteResource;
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

    public void setOpacity(final OpacityPresets opacityPresets) {
        spriteComponent.setOpacity(opacityPresets.opacity);
    }

    public TileActor(
        final Vector transform,
        @NonNull final Tile tile,
        final Resources.Sprite_Resource spriteResource,
        final int width,
        final int height,
        final MouseInputHandler mouseInputHandler
    ) {
        super(tile.getName(), transform);
        this.tile = tile;
        this.spriteResource = spriteResource;
        this.spriteComponent = new SpriteComponent(this, spriteResource, width, height);

        if (tile.isBlocksMovement()) {
            addComponent(new BoxColliderComponent(this, true, false, new Box(
                0,
                width,
                0,
                height)));
        }

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
        if (GameInstance.getInstance() != null) {
            final Camera camera = GameInstance.getInstance().getCamera();
            if (camera != null) {
                final Vector screenPos = camera.worldToScreenPos(transform);
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
