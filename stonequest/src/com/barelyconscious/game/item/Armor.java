/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Armor.java
 * Author:           Matt Schwartz
 * Date created:     07.09.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: This class is the superclass of all armor-type items, such as:
 *                   Helmets
 *                   Chest pieces
 *                   Off hands like shields
 *                   Belts
 *                   Earrings
 *                   Greaves
 *                   Necklaces
 *                   Boots
 *                   Rings
 *                   All shared attributes exist in this class as well as shared
 *                   functions necessary for most every type of Armor:
 *         -     int slotId: the armor slot that the item fits each slot has a 
 *                    unique integer value
 *         -     int bonusArmor: most armor types give bonus armor to the player, 
 *                    which reduces the amount of physical damage entities can
 *                    deal to the player
 *         - boolean isEquipped: true if the player currently has the item 
 *                    equipped.  Only one piece of armor can fit in a slot at 
 *                    a time
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class Armor extends Equippable {

    private double defenseRating = 0;
    
    /**
     * Create a new piece of armor wearable by the player with the following parameters:
     * 
     * @param name the name of the armor displayable to the player
     * @param itemLevel the level of the Item
     * @param sellValue the value vendors place on the armor. When a player sells Items to the shop, this is the amount of
     * gold credited to them in return
     * @param defenseRating any amount of bonus defense rating afforded by the 
     * Item
     * @param slotId where on the player this piece of armor fits
     * @param itemIcon  the icon corresponding to a Tile that will be drawn if the item is on the ground in the world
     * @param owner the owner (an Entity) whose Inventory this Armor exists in
     * @param itemAffixes if this is not null, is an array of StatBonuses which are bonuses to the player's attributes when
     * the piece of armor is worn
     */
    public Armor(String name, int itemLevel, int sellValue, double defenseRating, int slotId, UIElement itemIcon, Entity owner, AttributeMod... itemAffixes) {
        super(name, itemLevel, sellValue, slotId, itemIcon, owner, itemAffixes);
        this.defenseRating = defenseRating;
    } // constructor
    
    public Armor(String name, int itemLevel, int sellValue, double defenseRating, int slotId, String iconLocation, Entity owner, AttributeMod... itemAffixes) {
        this(name, itemLevel, sellValue, defenseRating, slotId, UIElement.createUIElement(iconLocation), owner, itemAffixes);
    } // constructor
    
    /**
     * 
     * @return the armor's implicit amount of defense rating
     */
    public double getDefenseRating() {
        return defenseRating;
    } // getDefenseRating

    @Override
    public String getDescription() {
        return "Place " + Equippable.slotIdToDescription(slotId) + " for best results.";
    } // getDescription

    @Override
    public String getType() {
        return Equippable.slotIdToEquippableType(slotId);
    } // getType
} // Armor
