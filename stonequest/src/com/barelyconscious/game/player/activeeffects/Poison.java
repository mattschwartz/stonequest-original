/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Poison.java
 * Author:           Matt Schwartz
 * Date created:     07.25.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Poisons are Debuffs that deal damage over time to the player
 **************************************************************************** */
package com.barelyconscious.game.player.activeeffects;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.UIElement;

public class Poison extends Debuff {

    private float tickDamage;
    private int tick;
    private int damageFrequency;

    /**
     * Create a new Poison with the following parameters
     *
     * @param name the name visible to the player of the Poison
     * @param dur how long the Poison lasts before it is removed
     * @param damage the amount of damage dealt to the Player every freq tick
     * @param freq how often the Poison deals damage to the Player
     */
    public Poison(String name, int dur, float damage, int freq) {
        super(name, dur, TOXIN, UIElement.POISON_ICON);
        tickDamage = damage;
        damageFrequency = freq;
        tick = damageFrequency;
    } // constructor

    /**
     * Counts down until the next time the poison is to deal damage to the player
     *
     * @return true if the poison is to deal damage to the player on this tick
     */
    public boolean nextTick() {
        if (--tick == 0) {
            tick = damageFrequency;
            return true;
        } // if

        return false;
    } // nextTick

    /**
     *
     * @return how much damage the Poison deals to the Player each tick
     */
    public float getTickDamage() {
        return tickDamage;
    } // getTickDamage

    /**
     *
     * @return how often the poison deals damage to the Player
     */
    public int getDamageFrequency() {
        return damageFrequency;
    } // getDamageFrequency

    @Override
    public void tick() {
        if (nextTick()) {
            Game.player.changeHealthBy(-tickDamage);
            Game.textLog.writeFormattedString(toString(), Common.FONT_DAMAGE_TEXT_RGB,
                    new LineElement(getDisplayName(), true,
                    Common.FONT_POISON_LABEL_RGB));
        } // if
        
        super.tick();
    } // tick

    /**
     * The message written to the TextLog when the Poison deals damage to the Player; the Player class actually writes
     * the message to the TextLog
     *
     * @return
     */
    @Override
    public String toString() {
        return super.getDisplayName() + " inflicts " + (int) tickDamage + " damage.";
    } // toString
} // Poison
