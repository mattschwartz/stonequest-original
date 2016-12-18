/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Cauldron.java
 * Author:           Matt Schwartz
 * Date created:     11.06.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: A Cauldron is where the user can combine 2-4 items together
 *                   to produce a potion or salve of some sort, whose affect is
 *                   dependent on the input items.
 **************************************************************************** */
package com.barelyconscious.game.player;

import com.barelyconscious.game.graphics.gui.ingamemenu.TextLog;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.salvage.Salvage;

public class Cauldron {

    public static final int MAX_INGREDIENTS = 4;
    private Item[] ingredients;

    /**
     * Creates a Cauldron object which becomes associated with an Entity (i.e.,
     * the Player).
     *
     * @param owner The Entity who has taken ownership of this Cauldron object
     */
    public Cauldron() {
        ingredients = new Item[MAX_INGREDIENTS];
    }

    /**
     * Returns the Item (if any) at
     * <code>index</code> and returns null if no object exists there or if the
     * supplied index is out of bounds.
     *
     * @param index The index of the Item that is requested
     * @return Returns an Item if one exists at the supplied index and null if
     * no item exists or if the index is out of bounds
     */
    public Item getIngredient(int index) {
        return index < 0 || index >= MAX_INGREDIENTS ? null : ingredients[index];
    }

    /**
     * Adds an Item at the first available Item slot in the Cauldron and returns
     * true if the add was successful.
     *
     * @param ingredient The Item to be added to the Cauldron. Only certain
     * types of Items can be added to the Cauldron
     * @return Returns true if <code>ingredient</code> was successfully added to
     * Cauldron and false if there was no room or if <code>ingredient</code> was
     * not a valid Item.
     */
    public boolean addIngredient(Item ingredient) {
        if (!isIngredient(ingredient)) {
            return false;
        }

        for (int i = 0; i < MAX_INGREDIENTS; i++) {
            if (ingredients[i] == null) {
                ingredients[i] = ingredient;
                return true;
            }
        }

        return false;
    }

    /**
     * Sets the Item slot, located at
     * <code>index</code>, to
     * <code>ingredient</code> if the index supplied is valid. Returns the Item
     * that previously occupied the Item slot if any.
     *
     * @param index The Item slot index within the Cauldron
     * @param ingredient The new Item that will occupy this slot within the
     * Cauldron
     * @return Returns the Item that previously occupied the Cauldron at this
     * Item slot if any; returns null if no Item was there or *
     * if <code>index</code> was out of bounds.
     */
    public Item setIngredient(int index, Item ingredient) {
        Item oldItem;
        if (index < 0 || index >= MAX_INGREDIENTS) {
            return null;
        }
        
        oldItem = ingredients[index];
        ingredients[index] = ingredient;
        return oldItem;
    }

    public Item brew() {
        TextLog.INSTANCE.append("Bubbles be bubblin.");
        // Consume ingredients
        for (int i = 0; i < MAX_INGREDIENTS; i++) {
            ingredients[i] = null;
        }
        
        Item item = new Item("Essence of Mind", 0, 155, 3, Item.ITEM_ROOT_FILE_PATH + "salvage/essenceOfMind.png", null);
        return  item;
    }

    /**
     * Determines if
     * <code>item</code> is a type of ingredient that can be thrown into the
     * Cauldron and brewed into a potion.
     *
     * @param item The Item in question
     * @return Returns true if the supplied Item is a type of ingredient that
     * can be thrown into the Cauldron and false otherwise
     */
    public boolean isIngredient(Item item) {
        return (item instanceof Salvage);
    }
}
