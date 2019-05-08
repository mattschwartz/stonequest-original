/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        PopupMenu.java
 * Author:           Matt Schwartz
 * Date created:     04.21.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.Sound;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.MouseHandler;

public class PopupMenu extends Interactable {
    private int startX;
    private int startY;
    private int width;
    private int height;
    private int selectedOption;
    private int numOptions;
    private String[] options;
    private Interactable spawnClass;
    
    public PopupMenu(int startX, int startY, Interactable spawnClass, String... options) {
        this.startX = startX;
        this.startY = startY;
        this.spawnClass = spawnClass;
        numOptions = options.length + 2;
        this.options = new String[numOptions];
        
        System.arraycopy(options, 0, this.options, 1, options.length);
        this.options[0] = "Choose Option";
        this.options[numOptions - 1] = "cancel";
    } // constructor
    
    public void init() {
        width = options[0].length();
        height = options.length * (Font.CHAR_HEIGHT + 1);
        
        for (String string : options) {
            if (string.length() > width) {
                width = string.length();
            } // if
        } // for
        
        width *= Font.CHAR_WIDTH;
        width ++;
        
        while (startX + width > Game.getGameWidth() - Font.CHAR_WIDTH) {
            startX--;
        } // while
        
        defineMouseZone(startX, startY, width, height);
        Game.mouseHandler.registerClickableListener(this);
        Game.mouseHandler.registerHoverableListener(this);
    } // init
    
    @Override
    public void mouseMoved(int x, int y) {
        selectedOption = (y - startY) / (Font.CHAR_HEIGHT + 1);
    } // mouseMoved

    @Override
    public void mouseClicked(int button, int x, int y) {
        if (button == MouseHandler.LEFT_CLICK) {
            spawnClass.parentCallback(selectedOption-1);
        } // if
        
        mouseExited();
        spawnClass.mouseMoved(x, y);
    } // mouseClicked

    @Override
    public void mouseExited() {
        spawnClass.enableMouse();
        disableMouse();
    } // mouseExited

    @Override
    public void disableMouse() {
        Game.mouseHandler.removeClickableListener(this);
        Game.mouseHandler.removeHoverableListener(this);
    } // disableMouse
    
    public void render(Screen screen) {
        
        screen.fillTransluscentRectangle(startX - 1, startY - 1, width + 3, height + 3);
        screen.fillRectangle(Common.themeForegroundColor, startX, startY, width, Font.CHAR_HEIGHT + 1);
        screen.drawRectangle(Common.themeForegroundColor, startX, startY, width, height);
        
        Font.drawOutlinedMessage(screen, options[0], Common.FONT_WHITE_RGB, false, startX, startY);
        
        for (int i = 1; i < options.length; i++) {
            if (i == selectedOption) {
                Font.drawMessage(screen, options[i], Common.WORLD_TILE_SELECT_GOOD, false, startX+1, startY + i * (Font.CHAR_HEIGHT + 1));
            } // if
            
            else {
                Font.drawMessage(screen, options[i], Common.FONT_WHITE_RGB, false, startX+1, startY + i * (Font.CHAR_HEIGHT + 1));
            } // else
        } // for
    } // render
} // PopupMenu