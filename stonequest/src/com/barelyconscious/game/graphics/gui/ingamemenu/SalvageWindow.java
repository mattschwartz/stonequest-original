/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        SalvageWindow.java
 * Author:           Matt Schwartz
 * Date created:     09.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.graphics.gui.ingamemenu.InterfaceWindowButton;
import com.barelyconscious.game.services.WindowManager;

public class SalvageWindow extends Window implements ButtonAction {

    protected InterfaceWindowButton windowButton;
    
    public SalvageWindow() {
    }
    
    /**
     * Resize elements as necessary when the application is resized.
     *
     * @param windowButtonX the new windowOffsX position of the upgrade item
     * window's button
     * @param windowButtonY the new windowOffsY position of the upgrade item
     * window's button
     */
    public void resize(int windowButtonX, int windowButtonY) {
        /* Relocate (if necessary) the button in the interface which toggles the 
         * showing of the Upgrade Item window */
        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);
    }

    @Override
    public void action(Button buttonPressed) {
//        InterfaceDelegate.getInstance().setSalvageActive(!InterfaceDelegate.getInstance().isSalvageActive);
    }

    @Override
    public void hoverOverAction(Button caller) {
        if (caller == null) {
            WindowManager.INSTANCE.clearTooltipText();
            return;
        }

        WindowManager.INSTANCE.setTooltipText("Click to salvage\nan item");
    }

    @Override
    public void render() {
        windowButton.render();
    }
}
