/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        EquipmentSlotArea.java
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

import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Equippable;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Player;
import java.awt.event.MouseEvent;

public class EquipmentSlotArea extends InventorySlotArea {

    private final Player player;
    private int equipmentSlotId;

    /**
     * Creates a new ItemSlotArea which represents an Item that is currently
     * equipped by the player.
     *
     * @param delegate the InterfaceDelegate which allows communication with
     * other GUI elements, such as Tooltips
     * @param equipmentSlotId the equipment slot referred to by the ItemSlotArea
     * @param startX the x coordinate of where to draw the slot
     * @param startY the y coordinate of where to draw the slot
     */
    public EquipmentSlotArea(Player player, int equipmentSlotId, int startX, int startY) {
        this.player = player;
        this.equipmentSlotId = equipmentSlotId;
        x = startX;
        y = startY;
        width = ITEM_SLOT_BACKGROUND.getWidth();
        height = ITEM_SLOT_BACKGROUND.getHeight();

        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_TEXT_AREA);
    } // constructor

    @Override
    public boolean itemGoesHere(Item item) {
        return item instanceof Equippable && ((Equippable) item).getSlotId() == equipmentSlotId;
    } // itemGoesHere

    @Override
    public Item getItem() {
        return player.getEquippedItemAt(equipmentSlotId);
    } // getItem

    @Override
    public Item setItem(Item item) {
        // If supplied item is null or not an instance of an Equippable, this 
        // function does nothing
        if (item == null || !(item instanceof Equippable)) {
            return null;
        } // if
        Item oldItem;

        // Item must be same type of equipment slot
        if (((Equippable) item).getSlotId() != equipmentSlotId) {
            return null;
        } // if

        oldItem = ((Equippable) item).equip();

        return oldItem;
    } // setItem

    @Override
    public Item removeItem() {
        Item item = player.getEquippedItemAt(equipmentSlotId);
        if (item == null) {
            return null;
        } // if

        return ((Equippable) item).unequip();
    } // removeItem

    @Override
    public void useItem() {
        Item item = removeItem();
        if (item != null) {
            player.getInventory().addItem(item);
        } // if
    } // useItem

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        Item item, cursorItem;
//
//        if (e.getButton() == Interactable.MOUSE_LEFT_CLICK) {
//            cursorItem = delegate.getItemOnCursor();
//
//            if (cursorItem != null) {
//                if (cursorItem instanceof Equippable && itemGoesHere(cursorItem)) {
//                    item = removeItem();
//                    delegate.putItemOnCursor(item);
//                    setItem(cursorItem);
//                } // if
//            } // if
//            else {
//                item = removeItem();
//                delegate.putItemOnCursor(item);
//            } // else
//        } // if
//        else {
//            useItem();
//        } // else
//    } // mouseClicked
} // EquipmentSlotArea
