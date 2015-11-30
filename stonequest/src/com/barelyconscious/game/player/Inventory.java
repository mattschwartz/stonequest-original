/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Inventory.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.player;

import com.barelyconscious.game.item.Item;

public class Inventory {

    public static final int NUM_INVENTORY_SLOTS = 24;
    private Item[] inventorySlots;
    private int gold;

    public Inventory() {
        gold = 0;
        inventorySlots = new Item[NUM_INVENTORY_SLOTS];

        for (int i = 0; i < NUM_INVENTORY_SLOTS; i++) {
            inventorySlots[i] = null;
        } // for
    } // constructor

    /**
     *
     * @return the amount of gold being carried in the Entity's inventory
     */
    public int getGold() {
        return gold;
    } // getGold

    /**
     * Adjusts the amount of gold being carried by the Entity by amount.
     *
     * @param amount the amount of gold being added to or subtracted from the
     * amount of gold carried by the Entity
     */
    public void adjustGoldBy(int amount) {
        gold += amount;
    } // adjustGoldBy

    /**
     * If index is out of bounds, returns null
     *
     * @param index the location of the Item
     * @return the item located at index, if one exists
     */
    public Item getItem(int index) {
        if (index < 0 || index >= NUM_INVENTORY_SLOTS) {
            return null;
        } // if

        return inventorySlots[index];
    } // getItem

    public Item setItem(int index, Item item) {
        if (index < 0 || index >= NUM_INVENTORY_SLOTS) {
            return null;
        } // if
        
        Item temp = getItem(index);
        inventorySlots[index] = item;
        return temp;
    } // setItem

    /**
     *
     * @return the entire Inventory as an array of Items
     */
    public Item[] getItemList() {
        Item[] temp = new Item[NUM_INVENTORY_SLOTS];
        System.arraycopy(inventorySlots, 0, temp, 0, NUM_INVENTORY_SLOTS);
        
        return temp;
    } // getItemList

    /**
     * Searches through the inventory for the Item based on the Item's compareTo
     * function which decides two Items are equivalent based on various factors.
     *
     * @param item the Item to search for
     * @return the index of the Item that already exists
     */
    public int contains(Item item) {
        for (int i = 0; i < NUM_INVENTORY_SLOTS; i++) {
            if (inventorySlots[i] == null) {
                continue;
            } // if

            if (inventorySlots[i].compareTo(item) == 0) {
                return i;
            } // if
        } // for

        return -1;
    } // contains

    /**
     * Adds an Item to the first available inventory slot
     *
     * @param item
     */
    public boolean addItem(Item item) {
        int slot;
        
        if (item == null) {
            return false;
        } // if
        
        slot = contains(item);

        // If item already exists, increase that item's stack size
        if (slot >= 0) {
            inventorySlots[slot].adjustStackBy(item.getStackSize());
            return true;
        } // if 

        for (int i = 0; i < NUM_INVENTORY_SLOTS; i++) {
            if (inventorySlots[i] == null) {
                inventorySlots[i] = item;
                return true;
            } // if
        } // for

        return false;
    } // addItem

    public void addItemAt(int index, Item item) {
        int slot = contains(item);

        if (slot >= 0) {
            inventorySlots[slot].adjustStackBy(item.getStackSize());
            return;
        } // if

        inventorySlots[index] = item;
    } // addItemAt

    /**
     * Removes the specified Item from the inventory.
     *
     * @param item the Item to be removed
     */
    public Item removeItem(Item item) {
        int slot = -1;
        
        for (int i = 0; i < NUM_INVENTORY_SLOTS; i++) {
            if (inventorySlots[i] == item) {
                slot = i;
            }
        }
        
        if (slot >= 0) {
            inventorySlots[slot] = null;
            return item;
        } // if
        
        return null;
    } // removeItem
    
    public Item removeItem(int index) {
        Item oldItem = inventorySlots[index];
        inventorySlots[index] = null;
        return oldItem;
    }

    /**
     * Removes an item at index from the inventory and drops it on the ground.
     * That is, it duplicates the item and spawns it in the world.
     *
     * @param index the integer location of where the Item exists within the
     * inventory
     */
    public void dropItem(int index) {
        Item droppedItem = inventorySlots[index];

        droppedItem.onDrop();

        // Add the Item to the world which, without specifying x and y coordinates, puts the item at the Entity's feet
        // addItemToWorld(droppedItem); 

        inventorySlots[index] = null;
    } // dropItem

    /**
     * Searches and removes item from the inventory and drops it on the ground.
     * That is, it duplicates the item and spawns it in the world.
     *
     * @param item the item to drop
     */
    public void dropItem(Item item) {
        Item droppedItem = item;

        droppedItem.onDrop();

        // Add the Item to the world which, without specifying x and y coordinates, puts the item at the Entity's feet
        // addItemToWorld(droppedItem); 

        removeItem(item);
    } // dropItem
} // Inventory
