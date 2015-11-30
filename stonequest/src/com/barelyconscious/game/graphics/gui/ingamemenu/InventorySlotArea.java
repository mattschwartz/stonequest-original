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
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Component;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyHandler;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.util.ColorHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class InventorySlotArea extends Interactable implements Component {

    protected static final UIElement ITEM_SLOT_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/itemSlot/itemSlot.png");
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int inventorySlotNumber;
    private boolean destroy = false;
    private Inventory playerInventory;

    public InventorySlotArea() {
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
    public InventorySlotArea(Inventory inventory, int slotNumber, int startX, int startY) {
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

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        Item item, cursorItem;
//
//        if (delegate.isSalvageActive()) {
//            TextLog.INSTANCE.append(getItem() + " salvaged.");
//            delegate.setSalvageActive(false);
//            return;
//        } // if
//
//        if ((e.getButton() == Interactable.MOUSE_LEFT_CLICK) && KeyHandler.isShiftKeyDown) {
//            cursorItem = delegate.getItemOnCursor();
//            item = getItem();
//
//            if (item == null) {
//                return;
//            } // if
//
//            if (cursorItem == null) {
//                int stackSize = item.getStackSize();
//                int split = (int) Math.ceil(stackSize * 1.0 / 2);
//
//                cursorItem = item.clone();
//                if (cursorItem != null) {
//                    cursorItem.setStackSize(stackSize - split);
//                    if (cursorItem.getStackSize() <= 0) {
//                        cursorItem = null;
//                    } // if
//                    item.setStackSize(split);
//                    setItem(item);
//                } // if
//
//                delegate.putItemOnCursor(cursorItem);
//            } // if
//        } // if
//        else if (e.getButton() == Interactable.MOUSE_LEFT_CLICK) {
//            if (stackItem()) {
//                return;
//            } // if
//
//            item = removeItem();
//
//            if (item != null) {
//                cursorItem = delegate.putItemOnCursor(item);
//
//                // swap items if there was already an item on the cursor
//                if (cursorItem != null) {
//                    setItem(cursorItem);
//                } // if
//            } // if
//            else {
//                item = delegate.putItemOnCursor(null);
//                playerInventory.setItem(inventorySlotNumber, item);
//            } // else
//        } // else
//        else {
//            useItem();
//        } // else
//    } // mouseClicked

//    public boolean stackItem() {
//        Item item, cursorItem;
//        
//        cursorItem = delegate.getItemOnCursor();
//        item = getItem();
//
//        if (cursorItem != null && item != null && item.compareTo(cursorItem) == 0) {
//            item = getItem();
//            item.adjustStackBy(cursorItem.getStackSize());
//            delegate.putItemOnCursor(null);
//            setItem(item);
//            return true;
//        } // if
//        
//        return false;
//    } // stackItem

    @Override
    public void mouseEntered() {
//        Item item;
//
//        super.mouseEntered();
//        item = getItem();
//
//        if (item == null) {
//            return;
//        } // if
//
//        delegate.setTooltipText(item + "\n" + item.getType());
    } // mouseEntered

//    @Override
//    public void mouseExited() {
//        super.mouseExited();
//
//        delegate.clearTooltipText();
//    }

    @Override
    public void render() {
//        String stack;
//        Item item = getItem();
//
//        if (item != null) {
//            item.render(screen, x, y);
//
//            if (item.getStackSize() > 1) {
//                stack = "" + item.getStackSize();
//                screen.fillTransluscentRectangle(x + width - Font.getStringWidth(screen, stack) - 2, y + height - Font.CHAR_HEIGHT + 3, Font.getStringWidth(screen, stack), Font.CHAR_HEIGHT - 5);
//                Font.drawFont(screen, stack, Color.white, true, x + width - Font.getStringWidth(screen, stack) - 3, y + height - 3);
//            } // if
//
//            if (isMouseInFocus()) {
//                screen.drawRectangle(ColorHelper.TILE_SELECT_CAN_MOVE, x, y, width, height);
//            } // if
//        } // if
//        else {
//            ITEM_SLOT_BACKGROUND.render(screen, x, y);
//        } // else
//
//        item = delegate.getItemOnCursor();
//
//        if (item != null) {
//            if (isMouseInFocus()) {
//                if (itemGoesHere(item)) {
//                    screen.drawRectangle(ColorHelper.TILE_SELECT_CAN_MOVE, x, y, width, height);
//                } // if
//                else {
//                    screen.drawRectangle(ColorHelper.TILE_SELECT_CANNOT_MOVE, x, y, width, height);
//                }
//            } // if
//        } // if 
    } // render
} // ItemSlotArea
