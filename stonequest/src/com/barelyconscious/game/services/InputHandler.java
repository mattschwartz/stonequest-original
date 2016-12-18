/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         InputHandler.java
 * Author:            Matt Schwartz
 * Date Created:      02.21.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.services;

import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyHandler;
import com.barelyconscious.game.input.MouseHandler;
import java.awt.Component;

public class InputHandler implements Service {

    public static final InputHandler INSTANCE = new InputHandler();
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    private InputHandler() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been instantiated.");
        }

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    public void addListeners(Component component) {
        component.addKeyListener(keyHandler);
        component.addMouseListener(mouseHandler);
        component.addMouseMotionListener(mouseHandler);
        component.addMouseWheelListener(mouseHandler);
    }
    
    public void addKeyListener(Interactable interactableObject) {
        keyHandler.addInteractable(interactableObject);
    }
    
    public void removeKeyListener(Interactable interactableObject) {
        keyHandler.removeInteractable(interactableObject);
    }
    
    public void addMouseListener(Interactable interactableObject, int zLevel) {
        mouseHandler.addInteractable(interactableObject, zLevel);
    }
    
    public void removeMouseListener(Interactable interactableObject) {
        mouseHandler.removeInteractable(interactableObject);
    }
    
    public int getMouseX() {
        return mouseHandler.getMouseX();
    }
    
    public int getMouseY() {
        return mouseHandler.getMouseY();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void restart() {
        stop();
        start();
    }
}
