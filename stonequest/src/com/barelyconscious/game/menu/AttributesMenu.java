/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        AttributesMenu.java
 * Author:           Matt Schwartz
 * Date created:     07.06.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: This class is responsible for drawing PLAYER_ICON stats to the
                     game's screen.  It prints Basic and Advanced stats, prints
                     highlighting of stats and shows immediate changes to a
                     PLAYER_ICON's stats. 
 **************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.player.Player;

public class AttributesMenu implements Menu {
    private final Player PLAYER;
    
    // Used for stat select
    private int selectedStat;
    private boolean hasFocus;
    private static final int MIN_SELECTABLE_STAT = 0;
    private static final int MAX_SELECTABLE_STAT = 9;
    
    // These values are not accurate
    private int xOffs = 25;
    private int yOffs = 50;
    private int menuLineWidth = 49;
    private int menuLineHeight = 20;
    
    private String[] attrListBuffer;
    
    public AttributesMenu(Player player) {
        PLAYER = player;
    } // constructor
    
    @Override
    public void resizeMenu(int width, int height) {
        xOffs = width - (menuLineWidth + 1) * Font.CHAR_WIDTH;
        yOffs = height - menuLineHeight * Font.CHAR_HEIGHT - 33;
    } // resizeMenu
    
    @Override
    public int getWidth() {
        return menuLineWidth;
    } // getWidth
    
    @Override
    public int getHeight() {
        return menuLineHeight;
    } // getHeight
    
    @Override
    public int getOffsX() {
        return xOffs;
    } // getWidth
    
    @Override
    public int getOffsY() {
        return yOffs;
    } // getHeight

    /**
     * Moves cursor up one stat line; does nothing if cursor is at the top stat 
     * or if focus is not on the basic pane.
     */
    @Override
    public void moveUp() {
        if (selectedStat - 1 < MIN_SELECTABLE_STAT) {
            return;
        } // if
        
        selectedStat--;
    } // moveUp

    /**
     * Moves cursor down one stat line; does nothing if cursor is at bottom stat 
     * or if focus is not on the basic pane.
     */
    @Override
    public void moveDown() {
        if (selectedStat + 1 > MAX_SELECTABLE_STAT) {
            return;
        } // if
        
        selectedStat++;
    } // moveDown

    /**
     * Select the currently selected stat to level up.
     */
    @Override
    public void select() {
        PLAYER.addTalentPointTo(selectedStat);
    } // select

    @Override
    public boolean isActive() {
        return hasFocus;
    } // isActive

    @Override
    public void setActive() {
        selectedStat = 0;
        hasFocus = true;
        Game.toolTipMenu.setItem(null);
        Game.toolTipMenu.setActive();
    } // setActive

    @Override
    public void clearFocus() {
        hasFocus = false;
        Game.toolTipMenu.clearFocus();
    } // clearFocus
    
    /* Writes the PLAYER_ICON's basic stat information to the game's screen. */
    public final void drawStats() {
        int lineNum = 0;
        
        attrListBuffer = new String[menuLineHeight];
        
        attrListBuffer[lineNum++] = Common.centerString("Player Stats", " ", 
                menuLineWidth);
        
        for (int i = Player.HITPOINTS; i <= Player.HOLY_MAGIC; i++) {
            attrListBuffer[lineNum++] = Common.alignToSides(Player.idToString(i), 
                    (int)PLAYER.getAttribute(i + 10) + "/" + (int)PLAYER.getAttribute(i), 
                    menuLineWidth);
        } // for
        
        attrListBuffer[lineNum++] = Common.alignToSides(Player.idToString(
                Player.PLUS_ALL_MAGIC), "" + (int)PLAYER.getAttribute(
                Player.PLUS_ALL_MAGIC), menuLineWidth);
        
        lineNum += 6;
        
        attrListBuffer[lineNum++] = Common.alignToSides("Unspent points", "" + 
                PLAYER.getAttributePoints(), menuLineWidth);
        
        attrListBuffer[lineNum++] = Common.alignToSides("Level", "" + 
                PLAYER.getLevel(), menuLineWidth);
        
        attrListBuffer[lineNum] = Common.alignToSides("XP to level", "" + 
                (PLAYER.getExperienceReq(PLAYER.getLevel() + 1) - 
                PLAYER.getCurrentExp()), menuLineWidth);
    } // drawStats
    
    @Override
    public void render(Screen screen) {
        int fg = Common.THEME_FG_COLOR_RGB;
        int bg = Common.THEME_BG_COLOR_RGB;
        
        drawStats();
        
        screen.fillRectangle(fg, xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT + 3);
        screen.drawLine(bg, xOffs, yOffs + (menuLineHeight - 3) * Font.CHAR_HEIGHT + 1, menuLineWidth * Font.CHAR_WIDTH);
        
        // Draw the selected stat if the menu is active
        if (isActive()) {
            screen.fillRectangle(bg, xOffs, 
                    yOffs + ((selectedStat + 1) * Font.CHAR_HEIGHT) + 3, 
                    menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT);
        } // if
        
        screen.drawLine(bg, xOffs, yOffs + Font.CHAR_HEIGHT + 2, menuLineWidth * Font.CHAR_WIDTH);
        Font.drawMessage(screen, attrListBuffer[0], bg, true, xOffs, yOffs);

        for (int i = 1; i < menuLineHeight; i++) {
            if (isActive() && i == (selectedStat + 1) ) {
                Font.drawMessage(screen, attrListBuffer[i], fg, true, xOffs + 1, yOffs + 3 + i * Font.CHAR_HEIGHT);
                continue;
            } // if

            Font.drawMessage(screen, attrListBuffer[i], bg, false, xOffs + 2, yOffs + 3 + i * Font.CHAR_HEIGHT);
        } // for
        
        Game.toolTipMenu.render(screen);
    } // update
} // AttributesMenu
