/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        KeyHandler.java
 * Author:           Matt Schwartz
 * Date created:     07.06.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    
    @Override
    public void keyPressed(KeyEvent e) {
        KeyAction keyAction = KeyMap.getAction(e.getKeyCode());
        
        if (keyAction != null) {
            keyAction.action(e);
        } // if
        
        else {
            System.err.println("Unknown key pressed: " + e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    } // keyReleased

    @Override
    public void keyTyped(KeyEvent e) {
    } // keyTyped
} // KeyHandler
