/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         KeyInteractable.java
 * Author:            Matt Schwartz
 * Date Created:      05.10.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.input;

import java.awt.event.KeyEvent;

public interface KeyInteractable {

    public boolean hasFocus();

    public void keyPressed(KeyEvent key);

    public void keyReleased(KeyEvent key);

    public void keyTyped(KeyEvent key);
} // KeyInteractable
