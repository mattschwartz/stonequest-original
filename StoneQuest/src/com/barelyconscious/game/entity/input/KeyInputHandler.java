package com.barelyconscious.game.entity.input;

import com.barelyconscious.game.delegate.*;

import java.awt.event.*;

public class KeyInputHandler implements KeyListener {

    private static final class InstanceHolder {
        static final KeyInputHandler instance = new KeyInputHandler();
    }

    public static KeyInputHandler instance() {
        return KeyInputHandler.InstanceHolder.instance;
    }

    private KeyInputHandler() {
    }

    public final Delegate<KeyEvent> onKeyPressed = new Delegate<>();

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        onKeyPressed.call(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
