/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Food.java
 * Author:           Matt Schwartz
 * Date created:     07.10.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: A subclass of the Item superclass and superclass of all types
 *                   of food in the game.  Every food item is used at minimum to
 *                   eat with the hopes of gaining some health back.  Spoiled food
 *                   will harm the player if ingested and some food is poisonous
 *                   originally.  Some features not yet implemented but that I would
 *                   like implemented:
 *                   A hunger resource: instead of directly healing the player,
 *                    food makes the player less hungry, which over time will
 *                    heal the player (or possibly just keep the player from
 *                    starving that will hurt the player over time; in which case
 *                    the only way for a player to heal is to bandage or drink
 *                    healing salves or possibly pray).  
 *                   Additional effects granted by food: permanently adjust an 
 *                    attribute of the player.  
 *                   Certain types of food can grant effects unique to their type: 
 *                    such as carrots granting increased light radius, bread lowering
 *                    more hunger and certain types of food that the player won't eat
 *                    a lot of because of its type                   
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class Food extends Item {

    /**
     * Change in health when the food is consumed.
     */
    private double changeInHealth;

    /**
     * Creates a new food item with the following parameters
     *
     * @param name the name of the food that is visible to the player
     * @param sellValue the value in gold vendors will give to the player in
     * exchange for the item
     * @param stackSize the initial stack size of the food item
     * @param tileId the id of the Tile that is drawn if the item needs to be
     * drawn to the screen
     * @param healthChange the change in health when the food is consumed
     */
    public Food(String name, int itemLevel, int sellValue, int stackSize, double healthChange, UIElement itemIcon, Entity owner) {
        super(name, itemLevel, sellValue, stackSize, itemIcon, owner, new AttributeMod(Entity.HEALTH_ATTRIBUTE, healthChange));
        this.changeInHealth = healthChange;
    } // constructor

    /**
     *
     * @return the change in health when the food is consumed
     */
    public double getHealthChange() {
        return changeInHealth;
    } // getHealthChange

    @Override
    public String getDescription() {
        return "Eat me.";
    } // getDescription

    /**
     *
     */
    @Override
    public void onUse() {
        if (owner.getAttribute(Entity.HEALTH_ATTRIBUTE) + changeInHealth > owner.getMaxHealth()) {
            owner.changeHealthBy(owner.getMaxHealth() - owner.getCurrentHealth());
        } // if
        else {
            owner.changeHealthBy(changeInHealth);
        } // else

        adjustStackBy(-1);
    } // onUse
} // Food
