/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Potion.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
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
import com.barelyconscious.game.player.condition.PotionEffect;
import com.barelyconscious.game.spawnable.Entity;

public class Potion extends Item {

    private static final UIElement DEFAULT_POTION_ICON = UIElement.createUIElement("/gfx/items/potions/potionIcon.png");
    private PotionEffect effects;

    /**
     * Creates a new Potion with the default Item icon and the other following
     * values:
     *
     * @param name the name of the Potion which is visible to the Player
     * @param duration how long the Potion's effects last when used
     * @param sellValue for how much the Potion sells to vendors
     * @param stackSize the amount of Potion's of this type
     * @param owner the Entity to which this Potion belongs
     * @param affectedAttributes which attributes of the Entity are affected
     * when the Potion is consumed
     */
    public Potion(String name, int duration, int sellValue, int stackSize, Entity owner, AttributeMod... affectedAttributes) {
        super(name, 0, sellValue, stackSize, DEFAULT_POTION_ICON, owner, affectedAttributes);
        this.effects = new PotionEffect(duration, name, owner, affectedAttributes);
    } // constructor

    /**
     * Creates a new Potion with a supplied Item icon and the other following
     * values:
     *
     * @param name the name of the Potion which is visible to the Player
     * @param duration how long the Potion's effects last when used
     * @param sellValue for how much the Potion sells to vendors
     * @param stackSize the amount of Potion's of this type
     * @param itemIcon the Item icon to be used when rendering this Item
     * @param owner the Entity to which this Potion belongs
     * @param affectedAttributes which attributes of the Entity are affected
     * when the Potion is consumed
     */
    public Potion(String name, int duration, int sellValue, int stackSize, UIElement itemIcon, Entity owner, PotionEffect effects) {
        super(name, 0, sellValue, stackSize, itemIcon, owner, effects.getAffectedAttributes());
        this.effects = effects;
    } // constructor

    /**
     *
     * @return how long the Potion's effects last (in game ticks)
     */
    public int getEffectsDuration() {
        return effects.getDuration();
    } // getEffectsDuration

    @Override
    public String getDescription() {
        return "Quaff to temporarily increase your attribute levels. Lasts for " + effects.getDuration() + " turns.";
    } // getDescription

    /**
     * When an Entity uses a Potion, it benefits from its effects and consumes
     * part of the stack size. If the stack size is reduced to 0, the Item is
     * removed from the Entity's inventory.
     */
    @Override
    public void onUse() {
        effects.apply();
        adjustStackBy(-1);
    } // onUse

    @Override
    public void salvage() {
        // Salvages into glass or maybe bottles of water and herbs?
        // Create the salvage and add it to the owner's inventory
        // Remove 1 from the stack size
        adjustStackBy(-1);
    } // salvage
} // Potion
