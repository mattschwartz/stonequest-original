/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Inventory.java
 * Author:           Matt Schwartz
 * Date created:     07.05.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.player;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.item.*;

public class Inventory {
    private final int INVENTORY_SLOTS = 20;
    private int currentSlot = 0;
    private Item[] inventory = new Item[INVENTORY_SLOTS];
    public int gold = 0;
    
    public Inventory() {
        Common.generateGibberish();
    } // constructor
    
    public boolean addItem(Item item) {
        if ( (item.getInternalName()).equals("gold")) {
            gold += item.getStackSize();
            return true;
        } // if
        
        if (currentSlot + 1 > INVENTORY_SLOTS) {
            return false;
        } // if
        
        if (item instanceof Scroll) {
            ((Scroll)item).checkIdStatus();
        } // if
        
        if (existsSimilarItem(item) >= 0) {
            inventory[existsSimilarItem(item)].adjustStackBy(item.getStackSize());
            return true;
        } // if
        
        inventory[currentSlot++] = item;
        
        return true;
    } // addItem
    
    public Item getItemAt(int index) {
        return inventory[index];
    } // getItemAt
    
    public Item removeItemAt(int index) {
        Item removed = inventory[index];
        
        if ( (removed instanceof Armor) || (removed instanceof Weapon) ) {
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
    
    public int getCurrentSlot() {
        return currentSlot;
    } // getCurrentSlot
    
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