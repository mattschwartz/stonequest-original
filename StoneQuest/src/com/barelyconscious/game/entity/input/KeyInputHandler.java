package com.barelyconscious.game.entity.input;

import com.barelyconscious.game.delegate.Delegate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputHandler implements KeyListener {

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
