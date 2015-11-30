package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.spawnable.Loot;
import java.util.ArrayList;

public class LootPickupMenu implements Menu {
    private int xOffs;
    private int yOffs;
    private int menuLineWidth;
    private int menuLineHeight;
    private boolean hasFocus;
    
    private ArrayList<Loot> loot = null;
    
    @Override
    public void resizeMenu(int w, int h) {
        menuLineWidth = Game.invenMenu.getWidth();
        menuLineHeight = 20;
        
        xOffs = (w - menuLineWidth * Font.CHAR_WIDTH) / 2;
        yOffs = (h - menuLineHeight * Common.TILE_SIZE) / 2;
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
    } // moveDown

    @Override
    public void select() {
        // unused
    } // select

    @Override
    public boolean isActive() {
        return hasFocus;
    } // isActive

    @Override
    public void setActive() {
        hasFocus = true;
    } // setActive

    @Override
    public void clearFocus() {
        hasFocus = false;
    } // clearFocus
    
    /**
     * Sets the list to what needs to be drawn to the loot window
     * @param l 
     */
    public void setList(ArrayList<Loot> l) {
        loot = l;
    } // setList

    @Override
    public void render(Screen screen) {
        if (!isActive()) {
            return;
        } // if
        
        screen.fillRectangle(Common.THEME_BG_COLOR_RGB, xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT + (menuLineHeight - 1) * Common.TILE_SIZE);
        screen.fillRectangle(Common.THEME_FG_COLOR_RGB, xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT);
        Font.drawMessage(screen, Common.centerString("Pickup What", " ", menuLineWidth), Common.THEME_BG_COLOR_RGB, true, xOffs, yOffs);
        
        for (int i = 0; i < loot.size(); i++) {
            Tile.getTile(loot.get(i).getItem().getTileId()).render(screen, xOffs, yOffs + Font.CHAR_HEIGHT + i * Common.TILE_SIZE);
            Font.drawMessage(screen, "" + loot.get(i), Common.THEME_FG_COLOR_RGB, false, xOffs + Common.TILE_SIZE + 2, yOffs + Font.CHAR_HEIGHT + i * Common.TILE_SIZE + 5);
        } // for
        
    } // render   
} // LootPickupMenu
