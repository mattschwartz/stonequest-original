package com.barelyconscious.game.entity.playercontroller;

import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.event.MouseEvent;

/**
 * Facilitates interactions between the player and the game, such as handling keyboard and mouse
 * input.
 */
public class PlayerController {

    @Getter
    @Nullable
    private Vector mouseScreenPos;
    @Getter
    @Nullable
    private Vector mouseWorldPos;

    @Getter
    @Nullable
    private Vector mouseClickedScreenPos;
    @Getter
    @Nullable
    private Vector mouseClickedWorldPos;

    public PlayerController(final MouseInputHandler inputHandler) {
        inputHandler.onMouseClicked.bindDelegate(this::onMouseClicked);
        inputHandler.onMouseMoved.bindDelegate(this::onMouseMoved);
        inputHandler.onMouseReleased.bindDelegate(this::onMouseReleased);
        inputHandler.onMouseExited.bindDelegate(this::onMouseExited);
    }

    private Void onMouseExited(MouseEvent mouseEvent) {
        mouseScreenPos = null;
        mouseWorldPos = null;
        mouseClickedScreenPos = null;
        mouseClickedWorldPos = null;

        return null;
    }

    private Void onMouseReleased(MouseEvent mouseEvent) {
        mouseClickedScreenPos = mouseClickedWorldPos = null;
        return null;
    }

    private Void onMouseMoved(MouseEvent mouseEvent) {
        mouseScreenPos = new Vector(mouseEvent.getX(), mouseEvent.getY());
        mouseWorldPos = GameInstance.getInstance().getCamera().screenToWorldPos(mouseScreenPos);
        return null;
    }

    private Void onMouseClicked(final MouseEvent mouseEvent) {
        mouseClickedScreenPos = mouseScreenPos = new Vector(mouseEvent.getX(), mouseEvent.getY());
        mouseClickedWorldPos = mouseWorldPos = GameInstance.getInstance().getCamera().screenToWorldPos(mouseScreenPos);
        return null;
    }

}
