/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ButtonAction.java
 * Author:           Matt Schwartz
 * Date created:     08.28.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Used to implement a callback function for when a button is
 *                   pressed.
 **************************************************************************** */

package com.barelyconscious.game.graphics.gui;

public interface ButtonAction {
    public void action(Button buttonPressed);
    public void hoverOverAction(Button caller);
} // ButtonAction
