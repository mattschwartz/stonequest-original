/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        LootMenu.java
 * Author:           Matt Schwartz
 * Date created:     02.23.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Displays a list of loot underneath the player.  Shows when
                     the player presses the loot-pickup button.
 **************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Screen;

public class LootMenu implements Menu {
    private boolean hasFocus;

    @Override
    public void resizeMenu(int w, int h) {
    }
    
    @Override
    public void moveUp() {
    }

    @Override
    public void moveDown() {
    }

    @Override
    public void select() {
    }

    @Override
    public boolean isActive() {
        return hasFocus;
    }

    @Override
    public void setActive() {
    }

    @Override
    public void clearFocus() {
    }

    @Override
    public void render(Screen screen) {
        if (!hasFocus) {
            return;
        }
    }

    @Override
    public int getWidth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getOffsX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getOffsY() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

} // LootMenu