package com.barelyconscious.game.entity.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles input from the player for the GUI to process.
 */
public class GUIController {

    private final List<KeyEvent> keyBuffer;

    public GUIController() {
        this.keyBuffer = new ArrayList<>();
    }

    public void injectKeyEvent(final KeyEvent e) {
        this.keyBuffer.add(e);
    }

    public void update() {
        keyBuffer.forEach(e -> {
            // process key input...
        });
    }
}
