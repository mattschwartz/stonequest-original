package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.Interactable;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.tile.Tile;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TileActor extends Actor implements Interactable {

    @Getter
    private Tile tile;

    private Box mouseCaptureBounds;

    public TileActor(
        final Vector transform,
        final Tile tile,
        final int width,
        final int height,
        final MouseInputHandler mouseInputHandler
    ) {
        super(transform);
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
                    return mouseCaptureBounds.plus(screenPos).contains(screenX, screenY);
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

                final Box b = mouseCaptureBounds.plus(screenPos);

                final Vector renderPos = new Vector(
                    b.left,
                    b.bottom + 2);

                renderContext.renderString(tile.getName(),
                    Color.YELLOW,
                    (int) renderPos.x,
                    (int) renderPos.y,
                    RenderLayer.GUI);
            }
        }
    }
}