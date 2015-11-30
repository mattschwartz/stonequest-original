/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        BrewingSlotArea.java
 * Author:           Matt Schwartz
 * Date created:     11.05.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.ingamemenu;

import static com.barelyconscious.game.graphics.gui.ingamemenu.InventorySlotArea.ITEM_SLOT_BACKGROUND;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Cauldron;

public class BrewingSlotArea extends ItemSlotArea {

    private int slotId;
    private Cauldron cauldron;
    private Item resultItem; // necessary for results

    public BrewingSlotArea(Cauldron cauldron, int slotId) {
        this.cauldron = cauldron;
        this.slotId = slotId;

        width = ITEM_SLOT_BACKGROUND.getWidth();
        height = ITEM_SLOT_BACKGROUND.getHeight();

        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_TEXT_AREA);
    } // constructor

    @Override
    public boolean itemGoesHere(Item item) {
        return slotId < Cauldron.MAX_INGREDIENTS;
    } // itemGoesHere

    @Override
    public Item getItem() {
        if (slotId < Cauldron.MAX_INGREDIENTS) {
            return cauldron.getIngredient(slotId);
        } // if

        return resultItem;
    } // getItem

    @Override
    public Item setItem(Item item) {
        Item oldItem;

        if (slotId < Cauldron.MAX_INGREDIENTS) {
            oldItem = cauldron.setIngredient(slotId, item);
        } // if
        else {
            oldItem = resultItem;
            resultItem = item;
        } // else

        return oldItem;
    } // setItem

    @Override
    public Item removeItem() {
        Item oldItem;
        if (slotId < Cauldron.MAX_INGREDIENTS) {
            oldItem = cauldron.setIngredient(slotId, null);
        } // if
        else {
            oldItem = resultItem;
            resultItem = null;
        } // else
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
} // BrewingSlotArea
