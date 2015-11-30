/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Food.java
 * Author:           Matt Schwartz
 * Date created:     07.10.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.StatBonus;

public class Food extends Item {
    private float healthGain; // health gained from using this item
    
    public Food(String name, int sellV, int stackSize, int tileId, float healthGain) {
        super(name, sellV, stackSize, tileId);
        super.setItemDescription("Eat me.");
        this.healthGain = healthGain;
        options[USE] = "eat";
        super.addAffix(new StatBonus(Player.HITPOINTS, healthGain));
    } // constructor
    
    public float getHealthGain() {
        return healthGain;
    } // getHealthGain

    @Override
    public String toString() {
        return "an " + super.getDisplayName() + " that heals for " + getAffixAt(0);
    } // toString
} // Food