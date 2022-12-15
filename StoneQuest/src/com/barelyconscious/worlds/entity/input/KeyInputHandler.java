package com.barelyconscious.worlds.entity.input;

import com.barelyconscious.worlds.delegate.*;

import java.awt.event.*;

// todo(p0): doesn't handle multiple keys at once
public class KeyInputHandler implements KeyListener {

    private static final class InstanceHolder {
        static final KeyInputHandler instance = new KeyInputHandler();
    }

    public static KeyInputHandler instance() {
        return KeyInputHandler.InstanceHolder.instance;
    }

    private KeyInputHandler() {
    }

    public final Delegate<KeyEvent> delegateOnKeyPressed = new Delegate<>();
    public final Delegate<KeyEvent> delegateOnKeyReleased = new Delegate<>();

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        delegateOnKeyPressed.call(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        delegateOnKeyReleased.call(e);
    }
}
