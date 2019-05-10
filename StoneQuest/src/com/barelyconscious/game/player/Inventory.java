/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Inventory.java
 * Author:           Matt Schwartz
 * Date created:     07.05.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email schwamat@gmail.com for issues or concerns.
 * File description: The inventory for the player.  Holds the player's items and
 *                   manages adding, removing and using the items.
 **************************************************************************** */
package com.barelyconscious.game.player;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Sound;
import com.barelyconscious.game.item.*;
import com.barelyconscious.game.spawnable.Loot;

public class Inventory {

    private final int INVENTORY_SLOTS = 43;
    private int currentSlot = 0;
    private Item[] inventory = new Item[INVENTORY_SLOTS];
    public int gold = 0;

    /**
     * Adds item to the player's Inventory; if the item being added is gold, it
     * is added to the variable representing the amount of gold that a player
     * has; if the Inventory is full, the Item is not picked up; if the Item
     * exists in the Inventory already, its stack size is increased, otherwise
     * the Item is added to the bottom of the Inventory
     *
     * @param item the Item to attempt to be added to the Inventory
     * @return true if the Item was successfully added to the Inventory, false
     * if it was unable to add it
     */
    public boolean addItem(Item item) {
        /* If the Item to be added is gold */
        if ((item.getDisplayName()).equals("gold")) {
            gold += item.getStackSize();
            Sound.LOOT_COINS.play();
            return true;
        } // if

        /* Doesn't add the item if the inventory is full */
        if (currentSlot + 1 > INVENTORY_SLOTS) {
            return false;
        } // if

        /* If the Item is already in the player's Inventory */
        if (existsSimilarItem(item) >= 0) {
            inventory[existsSimilarItem(item)].adjustStackBy(item.getStackSize());
            return true;
        } // if

        inventory[currentSlot++] = item;

        return true;
    } // addItem

    /**
     * Uses an Item at location
     *
     * @index in the Player's Inventory.
     * @param index the index location of the Item within the Player's inventory
     */
    public void useItem(int index) {
        String str;
        Item item = getItemAt(index);

        if (item == null) {
            System.err.println(" [ERR:useItem()] Attempting to access item at "
                    + "location [" + index + "].  Max item is " + currentSlot);
            return;
        } // if
        
        item.onUse();

        // Item is a piece of armor
//        if (item instanceof Armor || item instanceof Weapon) {
//            Game.player.equipItem(item);
//            Sound.EQUIP_ITEM.play();
//        } // if
//        // Item is food
//        else if (item instanceof Food) {
//            Game.player.eat((Food) item);
//            Game.textLog.writeFormattedString("You regain some health.",
//                    Common.FONT_NULL_RGB);
//
//            item.adjustStackBy(-1);
//        } // else if
//        // Item is a potion
//        else if (item instanceof Potion) {
//            Game.player.quaff(new Potion(item.getDisplayName(),
//                    item.getSellValue(), 1, ((Potion) item).
//                    getDurationInTicks(), ((Potion) item).getPotionType(),
//                    item.getAllAffixes()));
//            str = item.getDisplayName();
//
//            /* Print the message that gets displayed to the screen when the 
//             player uses the potion */
//            switch (((Potion) item).getPotionType()) {
//                case Potion.STATBUFF:
//                    str += " grants ";
//                    for (int i = 0; i < item.getNumAffixes() - 1; i++) {
//                        str += item.getAffixAt(i) + " (" + (int) item.
//                                getAffixAt(i).getAttributeModifier() + "), ";
//                    } // for
//
//                    str += item.getAffixAt(item.getNumAffixes() - 1) + "("
//                            + (int) item.getAffixAt(item.getNumAffixes() - 1).getAttributeModifier() + ").";
//                    break;
//
//                case Potion.ANTIMAGIC:
//                    str += " cleanses your afflictions.";
//                    break;
//
//                case Potion.ANTIVENOM:
//                    str += " cures your infections.";
//            } // switch
//
//            Game.textLog.writeFormattedString(str, Common.FONT_LOOT_LABEL_RGB,
//                    new LineElement(item.getDisplayName(), true,
//                    item.getRarityColor()));
//
//            item.adjustStackBy(-1);
//            Sound.DRINK_POTION.play();
//            Sound.SIGH.play();
//
//            if (!addItem(new Item("Glass Bottle", 15,
//                    Tile.GLASS_BOTTLE_TILE_ID))) {
//                Loot droppedLoot = new Loot(new Item("Glass Bottle", 15,
//                        Tile.GLASS_BOTTLE_TILE_ID), Game.world.getPlayerX(),
//                        Game.world.getPlayerY());
//                Game.world.addLoot(droppedLoot);
//            }
//        } // else if
//        // Item is a scroll
//        else if (item instanceof Scroll) {
//            Game.textLog.writeFormattedString("The scroll crumbles to dust...",
//                    Common.FONT_NULL_RGB);
//
//            Game.player.read((Scroll) item);
//
//            item.adjustStackBy(-1);
//            Sound.READ_SCROLL.play();
//        } // else if
//
//        if (item.getStackSize() <= 0) {
//            removeItemAt(index);
//        } // if
    } // useItem

    /**
     * Drops the Item located at
     *
     * @index in the Player's inventory.
     * @param index the index location of the Item within the Player's inventory
     */
    public void dropItem(int index) {
        Item item = getItemAt(index);
        Item droppedItem;
        Loot droppedLoot;

        // If selected item to drop exists, drop it in the world
        if (item != null) {
            item.adjustStackBy(-1);

            if (item.getStackSize() <= 0 || item instanceof Projectile) {
                removeItemAt(index);
            } // if

            if (item instanceof Armor) {
                droppedItem = new Armor(item.getDisplayName(), item.getSellValue(), ((Armor) item).getBonusArmor(), ((Armor) item).getArmorType(), item.getTileId(), item.getAllAffixes());
            } // if
            else if (item instanceof Weapon) {
                droppedItem = new Weapon(item.getDisplayName(), item.getSellValue(), ((Weapon) item).getMinDamageBonus(), ((Weapon) item).getMaxDamageBonus(), item.getTileId(), item.getAllAffixes());
            } // else if
            else if (item instanceof Food) {
                droppedItem = new Food(item.getDisplayName(), item.getSellValue(), 1, item.getTileId(), ((Food) item).getHealthChange());
            } // else if
            else if (item instanceof Potion) {
                droppedItem = new Potion(item.getDisplayName(), item.getSellValue(), 1, ((Potion)item).getEffects());
            } // else if
            else if (item instanceof Projectile) {
                droppedItem = new Projectile(item.getDisplayName(), item.getSellValue(), item.getStackSize() + 1, item.getTileId(), ((Projectile) item).doesRequireBow(), ((Projectile) item).getMetal());
            } // else if 
            else if (item instanceof Scroll) {
                droppedItem = new Scroll(item.getInternalName(), item.getSellValue(), ((Scroll) item).getScrollId(), item.getAllAffixes());
            } // else if
            else {
                droppedItem = new Item(item.getDisplayName(), item.getSellValue(), item.getTileId());
            } // else

            droppedItem.setRarityColor(item.getRarityColor());
            droppedLoot = new Loot(droppedItem, Game.world.getPlayerX(), Game.world.getPlayerY());
            Game.world.addLoot(droppedLoot);
        } // if
    } // dropItem

    public void examineItem(int index) {
        Item item = getItemAt(index);

        if (item == null) {
            System.err.println(" [ERR:examineItem()] Attempting to access item at "
                    + "location [" + index + "].  Max is " + currentSlot);
            return;
        } // if

        Game.textLog.writeFormattedString(item.getItemDescription(), Common.FONT_NULL_RGB);
    } // examineItem

    /**
     * Salvages the Item located at @index within the Player's Inventory.
     * Salvaging an Item turns it into craftable components which can then be 
     * used to improve other Items.
     * @param index the index location of the Item within the Player's inventory
     */
    public void salvageItem(int index) {
        Game.textLog.writeFormattedString("Salvaging is not yet implemented.", Common.FONT_DEFAULT_RGB);
    } // salvageItem

    /**
     * Returns the Item at index and null if the index is attempting to access
     * an out of bounds Item slot
     *
     * @param index the index of the Item to get
     * @return the item, if one exists, at index
     */
    public Item getItemAt(int index) {
        if (index < 0 || index >= currentSlot) {
            return null;
        } // if
        
        return inventory[index];
    } // getItemAt
    
    public void removeItem(Item item) {
        for (int i = 0; i < currentSlot; i++) {
            if (item == getItemAt(i)) {
                removeItemAt(i);
            } // if
        } // for
    } // removeItem

    /**
     * Removes an Item at index from the Inventory
     *
     * @param index the index of the Item to be removed from the Inventory
     * @return the Item removed, if successful
     */
    public Item removeItemAt(int index) {
        Item removed = inventory[index];

        if ((removed instanceof Armor && ((Armor) removed).isEquipped()) || (removed instanceof Weapon && ((Weapon) removed).isEquipped())) {
            Game.player.unequipItem(removed);
        } // if

        for (int i = index; i < (INVENTORY_SLOTS - 1) && inventory[i + 1] != null; i++) {
            inventory[i] = inventory[i + 1];
        } // for

        currentSlot--;

        if (currentSlot <= 0) {
            currentSlot = 0;
            inventory[index] = null;
        } // if

        return removed;
    } // removeItemAt

    /**
     *
     * @return the number of Items currently in the player's Inventory
     */
    public int getNumItems() {
        return currentSlot;
    } // getNumItems

    /**
     * Compares an Item to every Item in the player's Inventory to see if the
     * added Item should be stacked with another Item or added separately to the
     * Inventory
     *
     * @param item the Item to check against the Inventory
     * @return 0 if a similar Item exists, -1 otherwise
     */
    private int existsSimilarItem(Item item) {
        // To be the same item, everything must be equal
        for (int i = 0; i < currentSlot; i++) {
            if (item.compareTo(inventory[i]) == 0) {
                return i;
            } // if
        } // for

        return -1;
    } // itemExists
} // Inventory
