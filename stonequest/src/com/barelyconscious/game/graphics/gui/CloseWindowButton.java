/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        CloseWindowButton.java
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
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.input.Interactable;

public class CloseWindowButton extends Button {

    private final UIElement buttonImage;

    public CloseWindowButton(int startX, int startY, ButtonAction action, UIElement buttonImage) {
        super(action, "", Interactable.Z_BUTTON, startX, startY, false);
        super.setWidth(buttonImage.getWidth());
        super.setHeight(buttonImage.getHeight());
        this.buttonImage = buttonImage;
    } // constructor

    @Override
    public void render(Screen screen) {
        if (mouseButtonDown) {
            renderMouseButtonDown(screen);
        } // if
        else if (mouseInFocus) {
            buttonImage.renderHighlighted(screen, x, y);
        } // else if
        else {
            buttonImage.render(screen, x, y);
        } // else
    } // render

    private void renderMouseButtonDown(Screen screen) {
        int xOffs = x + 1;
        int yOffs = y + 1;

        buttonImage.render(screen, xOffs, yOffs);
    }
} // CloseWindowButton
