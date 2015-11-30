/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        TextLog.java
 * Author:           Matt Schwartz
 * Date created:     08.30.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics.gui.windows;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.gui.TextArea;

public class TextLog extends TextArea {

    public TextLog(int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
        super.setText("Welcome to StoneQuest v0.7.0!\nPress ? to open the help menu.\nYou hit Sewer Rat for 12 (physical).\nSewer Rat is killed.\nYou wait a turn.\nYou drink Potion of Might (STR+12, HP+18, AGI+11).\nYou become afflicted with Curse of Thumul.");
    } // constructor
    
    public void resize(int startX, int startY, int width, int height) {
        super.setWidth(width);
        super.setHeight(height);
        super.setX(startX);
        super.setY(startY);
    } // resize

    @Override
    public void render(Screen screen) {
        super.render(screen);
    }
} // TextLog
