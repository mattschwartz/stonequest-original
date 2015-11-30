/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        InterfaceWindowButton.java
 * Author:           Matt Schwartz
 * Date created:     08.29.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.windows.InterfaceDelegate;
import com.barelyconscious.game.input.Interactable;

public class InterfaceWindowButton extends Button {

    private final UIElement buttonImage;

    public InterfaceWindowButton( int startX, int startY, ButtonAction action, UIElement buttonImage) {
        super(action, "", Interactable.Z_BUTTON, startX, startY, false);
        super.setWidth(buttonImage.getWidth());
        super.setHeight(buttonImage.getHeight());
        this.buttonImage = buttonImage;
    } // constructor

    @Override
    public void mouseExited() {
        super.mouseExited(); //To change body of generated methods, choose Tools | Templates.
        InterfaceDelegate.getInstance().clearTooltipText();
    } // mouseExited
    
    @Override
    public void render(Screen screen) {
        if (mouseButtonDown) {
            buttonImage.renderShaded(screen, x, y);
        } // if
        else if (mouseInFocus) {
            renderHighlighted(screen);
        } // if
        else {
            buttonImage.render(screen, x, y);
        } // else
    } // render

    @Override
    protected void renderHighlighted(Screen screen) {
        buttonImage.renderHighlighted(screen, x, y);
    } // renderHighlighted
} // InterfaceWindowButton
