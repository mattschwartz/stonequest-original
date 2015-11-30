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
        } // if

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    } // constructor

    public void addListeners(Component component) {
        component.addKeyListener(keyHandler);
        component.addMouseListener(mouseHandler);
        component.addMouseMotionListener(mouseHandler);
        component.addMouseWheelListener(mouseHandler);
    } // addListeners
    
    public void addKeyListener(Interactable interactableObject) {
        keyHandler.addInteractable(interactableObject);
    } // addKeyListener
    
    public void removeKeyListener(Interactable interactableObject) {
        keyHandler.removeInteractable(interactableObject);
    } // removeKeyListener
    
    public void addMouseListener(Interactable interactableObject, int zLevel) {
        mouseHandler.addInteractable(interactableObject, zLevel);
    } // addMouseListener
    
    public void removeMouseListener(Interactable interactableObject) {
        mouseHandler.removeInteractable(interactableObject);
    } // removeMouseListener
    
    public int getMouseX() {
        return mouseHandler.getMouseX();
    } // getMouseX
    
    public int getMouseY() {
        return mouseHandler.getMouseY();
    } // getMouseY

    @Override
    public void start() {
    } // start

    @Override
    public void stop() {
    } // stop

    @Override
    public void restart() {
        stop();
        start();
    } // restart
} // InputHandler
