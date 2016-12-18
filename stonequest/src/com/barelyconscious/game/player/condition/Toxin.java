/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Toxin.java
 * Author:           Matt Schwartz
 * Date created:     07.25.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Poisons are Debuffs that deal damage over time to the player
 **************************************************************************** */
package com.barelyconscious.game.player.condition;

import com.barelyconscious.game.spawnable.Entity;

public class Toxin extends Condition {

    private double damagerPerTick;
    private int tick;
    private int damageFrequency;
    
    public Toxin(int duration, double damagePerTick, int frequency, String name, Entity affectedEntity) {
        super(duration, Condition.DETRIMENT_TOXIN_TYPE, name, affectedEntity, null);
        this.damagerPerTick = damagePerTick;
        damageFrequency = frequency;
    }

    @Override
    public String getDescription() {
        return "A harsh toxin that inflicts " + String.format("%.1f", damagerPerTick) + " damage as poison every " + damageFrequency + " turns.";
    }

    @Override
    public void tick() {
        super.tick();
        // Does nothing until damage frequency has been reached
        if (++tick < damageFrequency) {
            return;
        }
        
        tick = 0;
        affectedEntity.changeHealthBy(-damagerPerTick);
        
        // Temporary - will ultimately write to the text log
        System.out.println(name + " inflicts " + String.format("%.1f", damagerPerTick) + " damage (Poison).");
    }
}
