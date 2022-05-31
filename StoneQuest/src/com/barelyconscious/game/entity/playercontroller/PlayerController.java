package com.barelyconscious.game.entity.playercontroller;

import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.input.KeyInputHandler;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

import javax.annotation.Nullable;
import java.awt.event.KeyEvent;
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

    public PlayerController(final MouseInputHandler mouseHandler, final KeyInputHandler keyHandler) {
        mouseHandler.onMouseClicked.bindDelegate(this::onMouseClicked);
        mouseHandler.onMouseMoved.bindDelegate(this::onMouseMoved);
        mouseHandler.onMouseReleased.bindDelegate(this::onMouseReleased);
        mouseHandler.onMouseExited.bindDelegate(this::onMouseExited);

        keyHandler.onKeyTyped.bindDelegate(this::onKeyTyped);
    }

    // todo(p0) - since this is moving camera outside of game updates, movement is choppy
    private final float moveSpeed = 32f;
    private Void onKeyTyped(KeyEvent keyEvent) {
//        Vector translate = Vector.ZERO;
//
//        if (keyEvent.getKeyChar() == 'd') {
//            translate = translate.plus(moveSpeed, 0);
//        }
//        if (keyEvent.getKeyChar() == 'a') {
//            translate = translate.plus(-moveSpeed, 0);
//        }
//        if (keyEvent.getKeyChar() == 'w') {
//            translate = translate.plus(0, -moveSpeed);
//        }
//        if (keyEvent.getKeyChar() == 's') {
//            translate = translate.plus(0, moveSpeed);
//        }
//
//        final Vector cameraWorldPos = GameInstance.getInstance().getCamera()
//            .transform;
//        GameInstance.getInstance().getCamera().transform = cameraWorldPos.plus(translate);

        return null;
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
