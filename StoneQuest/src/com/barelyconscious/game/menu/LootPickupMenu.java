/* *****************************************************************************
 * Project:          StoneQuest
 * File displayName:        LootPickupMenu.java
 * Author:           Matt Schwartz
 * Date created:     03.11.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Displays a list of loot underneath the player.  Shows when
                     the player presses the loot-pickup button and more than one
                     item beneath the player exist.  This allows the player to
                     choose which, if any, Items to pick up.
 **************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyMap;
import com.barelyconscious.game.input.MouseHandler;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.gui.IWidget;

import java.util.ArrayList;

public class LootPickupMenu extends Interactable
    implements Menu, IWidget {

    private int xOffs;
    private int yOffs;
    private int menuLineWidth;
    private int menuLineHeight;
    private int selectedLoot;
    private int itemListLineStart;

    private ArrayList<Item> itemList = null;

    private final ToolTipMenu toolTipMenu;
    private final TextLog textLog;

    public LootPickupMenu(
        final ToolTipMenu toolTipMenu,
        final TextLog textLog
    ) {
        this.toolTipMenu = toolTipMenu;
        this.textLog = textLog;
    }

    /**
     * Adjust offset variables for the Menu based on the new width and height of
     * the Game window
     *
     * @param w the new width of the game window
     * @param h the new height of the game window
     */
    @Override
    public void resize(int w, int h) {
        menuLineWidth = 49;
        menuLineHeight = 18;

        xOffs = (w - menuLineWidth * Font.CHAR_WIDTH) / 2;
        yOffs = 4;

        defineMouseZone(xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Common.TILE_SIZE);
    } // resize

    public void setPosition(int x, int y) {
        xOffs = x;
        yOffs = y;

        defineMouseZone(xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Common.TILE_SIZE);
    } // setPosition

    /**
     * @return the width of the Menu in pixels
     */
    @Override
    public int getPixelWidth() {
        return menuLineWidth * Font.CHAR_WIDTH;
    } // getPixelWidth

    /**
     * @return the height of the Menu in pixels
     */
    @Override
    public int getPixelHeight() {
        return menuLineHeight * Font.CHAR_HEIGHT;
    } // getPixelHeight

    /**
     * @return the x offset of the Menu
     */
    @Override
    public int getOffsX() {
        return xOffs;
    } // getOffsX

    /**
     * @return the y offset of the Menu
     */
    @Override
    public int getOffsY() {
        return yOffs;
    } // getOffsY

    /**
     * Move the hightlight cursor up in the loot Menu to choose which item
     * to pick up; does nothing if the cursor is already at the top of the
     * list.
     */
    @Override
    public void moveUp() {
        selectedLoot--;

        if (selectedLoot < 0) {
            itemListLineStart--;

            if (itemListLineStart < 0) {
                itemListLineStart = 0;
            } // if

            selectedLoot++;
        } // if
    } // moveUp

    /**
     * Move the hightlight cursor down in the loot Menu to choose which item
     * to pick up; does nothing if the cursor is already at the top of the
     * list.
     */
    @Override
    public void moveDown() {
        selectedLoot++;

        if (itemList != null && (selectedLoot >= itemList.size())) {
            selectedLoot--;
        } // if

        else if (selectedLoot >= menuLineHeight) {
            selectedLoot--;
            itemListLineStart++;

            if (itemList != null && (itemListLineStart + selectedLoot) >= itemList.size()) {
                itemListLineStart--;
            } // if
        } // if
    } // moveDown

    /**
     * Selects the currently highlighted Item to pick up, add to the player's
     * inventory, and removes it from the world; also causes the world to tick(),
     * when the player picks up an Item.
     */
    @Override
    public void select() {
        if (Game.inventory.addItem(itemList.get(selectedLoot + itemListLineStart))) {
            itemList.remove(itemList.get(selectedLoot + itemListLineStart));

            moveUp();

            if (itemList.isEmpty()) {
                itemList = null;
                clearFocus();
            } // if
        } // if

        Game.world.tick();
    } // select

    public void examineItem() {
        Item item = itemList.get(selectedLoot + itemListLineStart);
        textLog.writeFormattedString(item.getItemDescription(), Common.FONT_NULL_RGB);
    } // examineItem

    @Override
    public boolean mouseInFocus(int x, int y) {
        return isActive() && super.mouseInFocus(x, y); //To change body of generated methods, choose Tools | Templates.
    } // mouseInFocus

    @Override
    public void mouseMoved(int x, int y) {
        if (!mouseInFocus(x, y)) {
            return;
        } // if

        selectedLoot = (y - Font.CHAR_HEIGHT) / Common.TILE_SIZE;

        if (selectedLoot >= itemList.size()) {
            selectedLoot = itemList.size() - 1;
        } // if
    } // mouseMoved

    @Override
    public void mouseClicked(int button, int x, int y) {
        if (!mouseInFocus(x, y)) {
            return;
        } // if

        if (button == MouseHandler.LEFT_CLICK) {
            select();
        } // if

        else if (button == MouseHandler.RIGHT_CLICK) {
            examineItem();
        } // else if
    } // mouseClicked

    @Override
    public void mouseExited() {
        clearFocus();
    } // mouseExited

    @Override
    public void keyPressed(int key) {
        int selectItem = KeyMap.getKeyCode(KeyMap.Key.PICKUP_ITEM);
        int selectItemAlt = KeyMap.getKeyCode(KeyMap.Key.MENU_SELECT);
        int examineItem = KeyMap.getKeyCode(KeyMap.Key.EXAMINE_ITEM);
        int moveUp = KeyMap.getKeyCode(KeyMap.Key.MENU_MOVE_UP);
        int moveDown = KeyMap.getKeyCode(KeyMap.Key.MENU_MOVE_DOWN);
        int clear = KeyMap.getKeyCode(KeyMap.Key.CLEAR_FOCUS);

        if (key == clear) {
            clearFocus();
        } // if

        else if (key == selectItem || key == selectItemAlt) {
            select();
        } // if

        else if (key == examineItem) {
            examineItem();
        } // else if

        else if (key == moveUp) {
            moveUp();
        } // else if

        else if (key == moveDown) {
            moveDown();
        } // else if
    } // keyPressed

    /**
     * Activates the Menu so that it can be seen by the player.
     */
    @Override
    public void setActive() {
//        xOffs = mouseX;
//        yOffs = mouseY;
        selectedLoot = 0;
        itemListLineStart = 0;
        super.setActive();
    } // setActive

    /**
     * Removes focus from the Menu so that it is hidden from the player.
     */
    @Override
    public void clearFocus() {
        itemList = null;
        super.clearFocus();
    } // clearFocus

    /**
     * Sets the item list to what needs to be drawn to the loot window
     *
     * @param newItemList
     */
    public void setItemList(ArrayList<Item> newItemList) {
        itemList = newItemList;
    } // setItemList

    /**
     * Draws the Menu to the Screen if the loot window is active; if not, nothing
     * is drawn and this function returns immediately
     *
     * @param screen the Screen to draw the Menu to
     */
    @Override
    public void render(Screen screen) {
        if (!isActive() || itemList == null) {
            toolTipMenu.clearFocus();
            clearFocus();
            return;
        } // if

        // Render the aesthetically pleasing frame
        renderFrame(screen);

        // Render the list of item names along with their icon
        renderList(screen);

        // Render tooltip for the item
//        renderToolTip(screen);
    } // render

    private void renderFrame(Screen screen) {
        /* Draw a filled rectangle representing the background for the loot pickup
            menu to make it easier for the user to see the Items in the window */
        screen.fillRectangle(Common.THEME_BG_COLOR_RGB, xOffs, yOffs, menuLineWidth *
            Font.CHAR_WIDTH, Font.CHAR_HEIGHT + menuLineHeight * Common.TILE_SIZE);

        /* Draw a border around the loo menu for aesthetics */
        screen.drawRectangle(Common.themeForegroundColor, xOffs, yOffs, menuLineWidth *
            Font.CHAR_WIDTH, Font.CHAR_HEIGHT + menuLineHeight * Common.TILE_SIZE);
        
        /* Draw a filled rectangle at the top of the Menu, representing a title 
            bar for aesthetics */
        screen.fillRectangle(Common.themeForegroundColor, xOffs, yOffs,
            menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT);

        /* Draws the title for the loot window */
        Font.drawMessage(screen, Common.centerString("Take What", " ", menuLineWidth),
            Common.THEME_BG_COLOR_RGB, true, xOffs, yOffs);

        /* Draw a highlight bar around the selected Item */
        screen.fillRectangle(Common.themeForegroundColor, xOffs, yOffs + Font.CHAR_HEIGHT +
                selectedLoot * Common.TILE_SIZE + 5, menuLineWidth * Font.CHAR_WIDTH,
            Font.CHAR_HEIGHT);
    } // renderFrame

    private void renderList(Screen screen) {
        /* If the list given is an Item list instead of a loot list, draw it to the
            window */
        for (int i = itemListLineStart; i < itemListLineStart + menuLineHeight && i < itemList.size(); i++) {

            /* Draw the artwork for the loot object */
            Tile.getTile(itemList.get(i).getTileId()).render(screen, xOffs,
                yOffs + Font.CHAR_HEIGHT + (i - itemListLineStart) * Common.TILE_SIZE);

            if ((i - itemListLineStart) == selectedLoot) {
                /* Draw the display displayName for the loot object */
                Font.drawMessage(screen, "" + itemList.get(i).getDisplayName(), Common.THEME_BG_COLOR_RGB,
                    false, xOffs + Common.TILE_SIZE + 2, yOffs + Font.CHAR_HEIGHT +
                        (i - itemListLineStart) * Common.TILE_SIZE + 5);
                continue;
            } // if

            /* Draw the display displayName for the loot object */
            Font.drawMessage(screen, "" + itemList.get(i).getDisplayName(), Common.themeForegroundColor,
                false, xOffs + Common.TILE_SIZE + 2, yOffs + Font.CHAR_HEIGHT +
                    (i - itemListLineStart) * Common.TILE_SIZE + 5);
        } // for
    } // renderList

    private void renderToolTip(Screen screen) {
        /* Draw the tooltip for the currently selected item */
        toolTipMenu.setItem(itemList.get(selectedLoot + itemListLineStart));

        toolTipMenu.setActive();
        toolTipMenu.render(screen);
    } // renderToolTip
} // LootPickupMenu
