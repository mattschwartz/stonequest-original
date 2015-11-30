/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Poison.java
 * Author:           Matt Schwartz
 * Date created:     07.25.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.player.activeeffects;

public class Poison extends Debuff {
    private float tickDamage; 
    private int tick;
    private int damageFrequency;
    
    public Poison(String name, int dur, float damage, int freq) {
        super(name, dur);
        tickDamage = damage;
        damageFrequency = freq;
        tick = damageFrequency;
    } // constructor
    
    // Countdown until the next damage tick
    public boolean nextTick() {
        if (--tick == 0) {
            tick = damageFrequency;
            return true;
        } // if
        
        return false;
    } // nextTick
    
    // How much damage the poison deals each tick
    public float getTickDamage() {
        return tickDamage;
    } // getTickDamage
    
    // How often the poison deals damage (in seconds)
    public int getDamageFrequency() {
        return damageFrequency;
    } // getDamageFrequency

    @Override
    public String toString() {
        return super.getName() + " inflicts " + (int)tickDamage + " damage.";
    } // toString
} // Poison