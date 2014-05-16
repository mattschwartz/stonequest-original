/* *****************************************************************************
 * Project:           stonequest
 * File Name:         KeyboardArgs.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.input;

import com.barelyconscious.gamestate.ClientBase;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;

public class KeyboardArgs {

    public GameContainer container;
    public ClientBase game;
    public int keyCode;
    public char keyChar;
    public boolean isLShiftDown = false;
    public boolean isRShiftDown = false;
    public boolean isLCtrlDown = false;
    public boolean isRCtrlDown = false;

    public KeyboardArgs() {
        setMetaKeys();
    }
    
    public KeyboardArgs(GameContainer gc, ClientBase game, int keyCode, char keyChar) {
        this.container = gc;
        this.game = game;
        this.keyCode = keyCode;
        this.keyChar = keyChar;
        
        setMetaKeys();
    }
    
    private void setMetaKeys() {
        isLShiftDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        isRShiftDown = Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
        isLCtrlDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
        isRCtrlDown = Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }
    
} // KeyboardArgs
