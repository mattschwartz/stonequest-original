/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Equippable.java
 * Author:           Matt Schwartz
 * Date created:     09.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class Equippable extends Item {

    protected int slotId;
    protected boolean equipped;

    /**
     * Creates a new equippable item (i.e., weapons and armor) with the
     * following values:
     *
     * @param name the name of the equippable item
     * @param itemLevel the level of the Item
     * @param sellValue the amount for which the equippable item will sell to a
     * vendor
     * @param slotId the slot on the entity's person in which the equippable
     * item will fit
     * @param itemIcon the icon which will be rendered to the screen rendering
     * to the screen in the player's inventory, should it find itself there
     * @param itemAffixes the item affixes which alter the entity's attributes,
     * either positively or negatively
     */
    public Equippable(String name, int itemLevel, int sellValue, int slotId, UIElement itemIcon, Entity owner, AttributeMod... itemAffixes) {
        super(name, itemLevel, sellValue, 1, itemIcon, owner, itemAffixes);
        this.slotId = slotId;
    }

    /**
     * Equippable items take up one item slot on the player and only one
     * equippable item per item slot can be on the player at any one time.
     *
     * @return into which player equippable item slot the item fits
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     *
     * @return true if the Equippable item is currently equipped on the entity's
     * person
     */
    public boolean isEquipped() {
        return equipped;
    }

    @Override
    public String getDescription() {
        return "You can equip it. What more could you ask from an item?";
    }

    /**
     * When the player interacts with an Equippable item, that item is either
     * equipped or unequipped.
     */
    @Override
    public void onUse() {
        Item item;

        equipped = !equipped;

        if (equipped) {
            item = equip();
        }
        else {
            item = unequip();
        }

        if (item != null) {
            owner.getInventory().addItem(item);
        }
    }

    /**
     * Attempts to equip an Equippable item to the entity's person, improving
     * the entity's attributes as necessary.
     */
    public Item equip() {
        Equippable currentlyEquippedItem = owner.getEquippedItemAt(slotId);

        if (currentlyEquippedItem != null) {
            currentlyEquippedItem.unequip();
        }

        owner.getInventory().removeItem(this);
        owner.equipItem(this);
        equipped = true;

        for (AttributeMod mod : itemAffixes) {
            owner.adjustAttribute(mod.getAttributeId(), mod.getAttributeModifier());
        }

        return currentlyEquippedItem;
    }

    /**
     * Removes the Equippable from the entity's person as well as any benefits
     * granted by the Equippable.
     */
    public Item unequip() {
        Item item = owner.getEquippedItemAt(slotId);
        owner.unequipItem(slotId);
        equipped = false;

        for (AttributeMod mod : itemAffixes) {
            owner.adjustAttribute(mod.getAttributeId(), -mod.getAttributeModifier());
        }

        return item;
    }

    /**
     * Returns the default description associated with the slot the Equippable
     * fits into.
     *
     * @param slotId the slot id of a piece of armor
     * @return a description unique to the given slot id
     */
    public static String slotIdToDescription(int slotId) {
        switch (slotId) {
            case Entity.NECK_SLOT_ID:
                return "around neck";
            case Entity.HELMET_SLOT_ID:
                return "on head";
            case Entity.EARRING_SLOT_ID:
                return "in ear";
            case Entity.CHEST_SLOT_ID:
                return "on chest";
            case Entity.OFF_HAND_SLOT_ID:
                return "in off hand";
            case Entity.BELT_SLOT_ID:
                return "around waist";
            case Entity.GREAVES_SLOT_ID:
                return "on legs";
            case Entity.RING_SLOT_ID:
                return "on finger";
            case Entity.BOOTS_SLOT_ID:
                return "on feet";
            default:
                return "{ERR:UNDEF}";
        }
    }

    /**
     * Returns the item type associated with the supplied slot id.
     *
     * @param slotId the slot id for which an item type is desired
     * @return a string which describes what type of item fits into the supplied
     * slot id
     */
    public static String slotIdToEquippableType(int slotId) {
        switch (slotId) {
            case Entity.NECK_SLOT_ID:
                return "necklace";
            case Entity.HELMET_SLOT_ID:
                return "helmet";
            case Entity.EARRING_SLOT_ID:
                return "earring";
            case Entity.CHEST_SLOT_ID:
                return "chest";
            case Entity.OFF_HAND_SLOT_ID:
                return "off-hand";
            case Entity.BELT_SLOT_ID:
                return "belt";
            case Entity.GREAVES_SLOT_ID:
                return "greaves";
            case Entity.RING_SLOT_ID:
                return "ring";
            case Entity.BOOTS_SLOT_ID:
                return "boots";
            default:
                return "{ERR:UNDEF}";
        }
    }

    /**
     * The compareTo functionality is used to compare to pieces of armor to each
     * other for stacking purposes when the Item is added to the entity's
     * inventory; since armor never stacks, compareTo returns -1 every time
     *
     * @param item the Item to compare to
     * @return
     */
    @Override
    public int compareTo(Item item) {
        return -1;
    }
}
