/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         ButtonCallback.java
   * Author:            Matt Schwartz
   * Date Created:      05.09.2014 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
   *                    this file.  You are not allowed to take credit for code
   *                    that was not written fully by yourself, or to remove 
   *                    credit from code that was not written fully by yourself.  
   *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.graphics.gui;

public interface ButtonCallback {
    public void action(BetterButton buttonPressed);
    public void hoverOverAction(BetterButton caller);
} // ButtonCallback
