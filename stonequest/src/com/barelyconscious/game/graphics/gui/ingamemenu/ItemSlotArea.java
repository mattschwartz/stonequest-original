/* *****************************************************************************
 *   Project:        StoneQuest
 *   File name:      ItemSlotArea.java
 *   Author:         Matt Schwartz
 *   Date:           12.14.2013
 *   License:        You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email mattschwartz@utexas.edu for issues or concerns.
 *   Description:    
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.util.ColorHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ItemSlotArea extends InventorySlotArea {

    private Item slottedItem;
    public List<Item> allowedItems = new ArrayList<Item>();

    public ItemSlotArea() {
        width = ITEM_SLOT_BACKGROUND.getWidth();
        height = ITEM_SLOT_BACKGROUND.getHeight();

        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_TEXT_AREA);
    } // constructor

    @Override
    public boolean itemGoesHere(Item item) {
        return true;
    } // itemGoesHere

    @Override
    public Item getItem() {
        return slottedItem;
    } // getItem

    @Override
    public Item setItem(Item item) {
        Item oldItem = slottedItem;

        slottedItem = item;

        return oldItem;
    } // setItem

    public void onHide() {
//        delegate.getPlayerInventory().addItem(removeItem());
    } // onHide

    @Override
    public Item removeItem() {
        Item oldItem = slottedItem;
        slottedItem = null;
        return oldItem;
    } // removeItem

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        Item item, cursorItem;
//
//        if (e.getButton() == Interactable.MOUSE_LEFT_CLICK) {
//            if (stackItem()) {
//                return;
//            } // if
//
//            cursorItem = delegate.getItemOnCursor();
//
//            if (cursorItem != null) {
//                if (itemGoesHere(cursorItem)) {
//                    item = removeItem();
//                    delegate.putItemOnCursor(item);
//                    setItem(cursorItem);
//                } // if
//            } // if
//            else {
//                item = removeItem();
//                delegate.putItemOnCursor(item);
//            } // else
//        } // else
//        else {
//            item = getItem();
//            if (delegate.getPlayerInventory().addItem(item)) {
//                removeItem();
//            } // if
//        } // else
//    } // mouseClicked

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
