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
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener {

    public static boolean isShiftKeyDown = false;
    public static boolean isControlKeyDown = false;
    public static boolean isAltKeyDown = false;
    
    private List<Interactable> interactables = new ArrayList<Interactable>();
    
    public void addInteractable(Interactable interactable) {
        interactables.add(interactable);
    } // addInteractable
    
    public void removeInteractable(Interactable interactable) {
        interactables.remove(interactable);
    } // removeInteractable

    @Override
    public void keyPressed(KeyEvent e) {
        KeyAction keyAction;

        isShiftKeyDown = e.isShiftDown();
        isControlKeyDown = e.isControlDown();
        isAltKeyDown = e.isAltDown();
        
        for (Interactable interactable : interactables) {
            if (interactable.hasFocus()) {
                interactable.keyPressed(e);
                return;
            } // if
        } // for

        keyAction = KeyMap.getAction(e.getKeyCode());

        if (keyAction != null) {
            keyAction.action(e);
        } // if
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isShiftKeyDown = false;
        isControlKeyDown = false;
        isAltKeyDown = false;
        
        for (Interactable interactable : interactables) {
            if (interactable.hasFocus()) {
                interactable.keyReleased(e);
                return;
            } // if
        } // for
    } // keyReleased

    @Override
    public void keyTyped(KeyEvent e) {
        for (Interactable interactable : interactables) {
            if (interactable.hasFocus()) {
                interactable.keyTyped(e);
                return;
            } // if
        } // for
    } // keyTyped
} // KeyHandler
