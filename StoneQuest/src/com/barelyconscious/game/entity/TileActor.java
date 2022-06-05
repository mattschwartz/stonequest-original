package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.RenderString;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.Interactable;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.tile.Tile;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;
import lombok.NonNull;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TileActor extends Actor implements Interactable {

    @Getter
    private Tile tile;

    private Box mouseCaptureBounds;

    public TileActor(
        final Vector transform,
        @NonNull final Tile tile,
        final int width,
        final int height,
        final MouseInputHandler mouseInputHandler
    ) {
        super(tile.getName(), transform);
        this.tile = tile;

        configure(width, height);
        mouseInputHandler.registerInteractable(this, InputLayer.GAME_WORLD);
    }

    private void configure(final int width, final int height) {
        final Sprite tileSprite = Resources.getSprite(tile.getSpriteResource());
        addComponent(new SpriteComponent(this, tileSprite));

        mouseCaptureBounds = new Box(
            0, width,
            0, height);
        addComponent(new TileInfoTextComponent(this));
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
    public void onMouseOver(MouseEvent e) {
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
    }

    @Override
    public void onMouseEntered(MouseEvent e) {
        isMouseOver = true;
    }

    @Override
    public void onMouseExited(MouseEvent e) {
        isMouseOver = false;
    }

    // todo: should not make this part of every single tile
    private final class TileInfoTextComponent extends Component {

        public TileInfoTextComponent(Actor parent) {
            super(parent);
        }

        @Override
        public void guiRender(EventArgs eventArgs, RenderContext renderContext) {
            if (isMouseOver && eventArgs.getMouseScreenPos() != null) {
                final Vector screenPos = renderContext.camera.worldToScreenPos(transform);
                renderContext.renderRect(Color.yellow, false, (int) transform.x, (int) transform.y, 32, 32,
                    RenderLayer.SKY);
            }
        }
    }
}
