/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ItemSlotArea.java
 * Author:           Matt Schwartz
 * Date created:     09.03.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.windows.InterfaceDelegate;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Inventory;
import java.awt.Color;

public class ItemSlotArea extends Interactable implements Components {

    protected static final UIElement ITEM_SLOT_BACKGROUND = new UIElement("/gfx/gui/components/itemSlot/itemSlot.png");
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int inventorySlotNumber;
    private boolean destroy = false;
    protected InterfaceDelegate delegate;
    private Inventory playerInventory;

    public ItemSlotArea() {
    }

    /**
     * Create a new ItemSlotArea which represents one of the player's inventory
     * slots.
     *
     * @param delegate the InterfaceDelegate which allows communication with
     * other GUI elements, such as Tooltips
     * @param inventory the player's inventory which contains the items carried
     * by the player
     * @param slotNumber which slot within the player inventory that this
     * ItemSlotArea represents
     * @param startX the x coordinate of where to draw the slot
     * @param startY the y coordinate of where to draw the slot
     */
    public ItemSlotArea(Inventory inventory, int slotNumber, int startX, int startY) {
        delegate = InterfaceDelegate.getInstance();
        playerInventory = inventory;
        inventorySlotNumber = slotNumber;
        x = startX;
        y = startY;
        width = ITEM_SLOT_BACKGROUND.getWidth();
        height = ITEM_SLOT_BACKGROUND.getHeight();

        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_TEXT_AREA);
    } // constructor

    public void resize(int startX, int startY) {
        x = startX;
        y = startY;

        super.setRegion(x, y, width, height);
    } // resize

    @Override
    public int getX() {
        return x;
    } // getX

    @Override
    public int getY() {
        return y;
    } // getY

    @Override
    public void setX(int newX) {
        x = newX;
    } // setX

    @Override
    public void setY(int newY) {
        y = newY;
    } // setY

    @Override
    public int getWidth() {
        return width;
    } // getWidth

    @Override
    public int getHeight() {
        return height;
    } // getHeight

    public boolean itemGoesHere(Item item) {
        return true;
    }

    public Item getItem() {
        return playerInventory.getItem(inventorySlotNumber);
    } // getItem

    public Item setItem(Item item) {
        Item oldItem;
        oldItem = playerInventory.setItem(inventorySlotNumber, item);

        return oldItem;
    } // setItem

    public Item removeItem() {
        Item item;
        item = playerInventory.removeItem(inventorySlotNumber);

        return item;
    } // removeItem

    public void useItem() {
        Item item = getItem();

        if (item != null) {
            item.onUse();
        } // if
    } // useItem

    @Override
    public void dispose() {
        destroy = true;
    } // 

    @Override
    public boolean shouldRemove() {
        return destroy;
    } // shouldRemove
    
    @Override
    public void mouseClicked(int buttonClicked, int clickCount, int x, int y) {
        Item item, oldItem;
        super.mouseClicked(buttonClicked, clickCount, x, y);

        if (delegate.isSalvageActive() && playerInventory != null) {
            System.err.println(getItem() + " salvaged.");
            delegate.setSalvageActive(false);
            return;
        } // if

        if (buttonClicked == Interactable.MOUSE_LEFT_CLICK) {
            item = removeItem();

            if (item != null) {
                oldItem = delegate.putItemOnCursor(item);

                // swap items if there was already an item on the cursor
                if (oldItem != null) {
                    setItem(oldItem);
                } // if
            } // if
            else {
                item = delegate.putItemOnCursor(null);
                playerInventory.setItem(inventorySlotNumber, item);
            } // else
        } // else
        else {
            useItem();
        } // else
    } // mouseClicked

    @Override
    public void mouseEntered() {
        Item item;

        super.mouseEntered();
        item = getItem();

        if (item == null) {
            return;
        } // if

        delegate.setTooltipText(item + "\n" + item.getType());
    } // mouseEntered

    @Override
    public void mouseExited() {
        super.mouseExited();
        
        delegate.clearTooltipText();
    }

    @Override
    public void render(Screen screen) {
        Item item = getItem();

        if (item != null) {
            item.render(screen, x, y);

            if (mouseInFocus) {
                screen.drawRectangle(Common.GOLD_TEXT_COLOR.getRGB(), x, y, width, height);
            } // if
        } // if
        else {
            ITEM_SLOT_BACKGROUND.render(screen, x, y);
        } // else

        item = delegate.getItemOnCursor();

        if (item != null) {
            if (mouseInFocus) {
                if (itemGoesHere(item)) {
                    screen.drawRectangle(Common.GOLD_TEXT_COLOR.getRGB(), x, y, width, height);
                } // if
                else {
                    screen.drawRectangle(Color.red.getRGB(), x, y, width, height);
                }
            } // if
        } // if 
    } // render
} // ItemSlotArea
