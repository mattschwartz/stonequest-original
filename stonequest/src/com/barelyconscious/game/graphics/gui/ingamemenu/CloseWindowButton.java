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
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.input.Interactable;

public class CloseWindowButton extends Button {

    private final UIElement buttonImage;

    public CloseWindowButton(ButtonAction action, UIElement buttonImage) {
        super(action, "", Interactable.Z_BUTTON, 0, 0, false);
        super.setWidth(buttonImage.getWidth());
        super.setHeight(buttonImage.getHeight());
        this.buttonImage = buttonImage;
    }

    @Override
    public void render() {
        if (isMouseButtonDown()) {
            renderMouseButtonDown();
        }
        else if (isMouseInFocus()) {
            buttonImage.renderHighlighted(x, y);
        }
        else {
            buttonImage.render(x, y);
        }
    }

    private void renderMouseButtonDown() {
        int xOffs = x + 1;
        int yOffs = y + 1;

        buttonImage.render(xOffs, yOffs);
    }
}
