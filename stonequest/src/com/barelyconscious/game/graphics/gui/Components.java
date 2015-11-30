/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Components.java
 * Author:           Matt Schwartz
 * Date created:     08.27.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Screen;

public interface Components {
    public int getX();
    public int getY();
    public void setX(int newX);
    public void setY(int newY);
    public int getWidth();
    public int getHeight();
    public void dispose();
    public boolean shouldRemove();
    public void render(Screen screen);
} // Components
