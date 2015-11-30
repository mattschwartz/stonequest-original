/* *****************************************************************************
   * File Name:         BuffBar.java
   * Author:            Matt Schwartz
   * Date Created:      03.10.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  In charge of drawing any active effects to the screen.
   ************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.Icon;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.activeeffects.Curse;
import com.barelyconscious.game.player.activeeffects.Poison;

public class BuffBar implements Menu {
    private final Player player;
    
    private int xOffs;
    private int yOffs;
    
    public BuffBar(Player p) {
        player = p;
        xOffs = 0;
        yOffs = 0;
    } // constructor

    @Override
    public void resizeMenu(int w, int h) {
        xOffs = Game.textLog.getOffsX();
        yOffs = Game.textLog.getOffsY() - (Game.textLog.getHeight() + 1) * Font.CHAR_HEIGHT - 2;
        
        yOffs -= yOffs % Common.TILE_SIZE;
    } // resizeMenu

    @Override
    public int getWidth() {
        return Common.TILE_SIZE;
    } // getWidth

    @Override
    public int getHeight() {
        return Common.TILE_SIZE;
    } // getHeight

    @Override
    public int getOffsX() {
        return xOffs;
    } // getOffsX

    @Override
    public int getOffsY() {
        return yOffs;
    } // getOffsY

    @Override
    public void moveUp() {
    } // moveUp

    @Override
    public void moveDown() {
    } // move down

    @Override
    public void select() {
    } // select

    @Override
    public boolean isActive() {
        return true;
    } // isActive

    @Override
    public void setActive() {
    } // setActive

    @Override
    public void clearFocus() {
    } // clearFocus

    @Override
    public void render(Screen screen) {
        int walletSize = ("$" + Game.inventory.gold).length();
        int barSize = player.getNumDebuffs();
        barSize += player.getPotionEffects() != null ? 1 : 0;
        
        if (barSize > 0) {
            screen.fillRectangle(Common.THEME_BG_COLOR_RGB, xOffs - 1, yOffs - (barSize - 1) * Common.TILE_SIZE - 1, 3 * Common.TILE_SIZE + 1, barSize * (Common.TILE_SIZE) + 1);
            screen.drawRectangle(Common.THEME_FG_COLOR_RGB, xOffs - 1, yOffs - (barSize - 1) * Common.TILE_SIZE - 1, 3 * Common.TILE_SIZE + 1, barSize * (Common.TILE_SIZE) + 1);
        } // if
        
        // Draw debuffs
        for (int i = 0; i < player.getNumDebuffs(); i++) {
            if (player.getDebuffAt(i) instanceof Curse) {
                Icon.CURSE_ICON.render(screen, xOffs, yOffs - i * Common.TILE_SIZE);
                Font.drawMessage(screen, "" + player.getDebuffAt(i).getDuration(), Common.CURSE_COLOR_RGB, false, xOffs + 2 + Common.TILE_SIZE, yOffs - i * Common.TILE_SIZE + 5);
            } // if
            
            else if (player.getDebuffAt(i) instanceof Poison) {
                Icon.POISON_ICON.render(screen, xOffs, yOffs - i * Common.TILE_SIZE);
                Font.drawMessage(screen, "" + player.getDebuffAt(i).getDuration(), Common.POISON_COLOR_RGB, false, xOffs + 2 + Common.TILE_SIZE, yOffs - i * Common.TILE_SIZE + 5);
            } // else if
        } // for
        
        // Draw buffs
        if (player.getPotionEffects() != null) {
            Icon.POTION_ICON.render(screen, xOffs, yOffs - player.getNumDebuffs() * Common.TILE_SIZE);
            Font.drawMessage(screen, "" + player.getPotionEffects().getDuration(), Common.POTION_COLOR_RGB, false, xOffs + 2 + Common.TILE_SIZE, yOffs - player.getNumDebuffs() * Common.TILE_SIZE + 5);
        } // if
        
        // Gold pouch!
        screen.fillRectangle(Common.THEME_BG_COLOR_RGB, xOffs + 
                (Game.textLog.getWidth() - walletSize) * Font.CHAR_WIDTH - 
                Common.TILE_SIZE - 2, yOffs - 1, walletSize * Font.CHAR_WIDTH + 
                Common.TILE_SIZE + 2, Common.TILE_SIZE + 1);
        screen.drawRectangle(Common.THEME_FG_COLOR_RGB, xOffs + 
                (Game.textLog.getWidth() - walletSize) * Font.CHAR_WIDTH - 
                Common.TILE_SIZE - 2, yOffs - 1, walletSize * Font.CHAR_WIDTH + 
                Common.TILE_SIZE + 2, Common.TILE_SIZE + 1);
        Icon.COIN_POUCH_ICON.render(screen, xOffs + (Game.textLog.getWidth()) * Font.CHAR_WIDTH - Common.TILE_SIZE, yOffs);
        Font.drawMessage(screen, "$" + Game.inventory.gold, Common.PLAYER_GOLD_TEXT_COLOR_RGB, 
                false, xOffs + (Game.textLog.getWidth() - walletSize) * Font.CHAR_WIDTH - 
                Common.TILE_SIZE - 2, yOffs + 5);
    } // render
} // BuffBar
