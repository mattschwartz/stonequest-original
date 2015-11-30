/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Debuff.java
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

import com.barelyconscious.game.player.StatBonus;

public class Debuff {
    private int duration;
    private int numAffected;
    private String name;
    private StatBonus [] affectedStats;
    
    public Debuff(String name, int dur, StatBonus... affected) {
        this.name = name;
        duration = dur;
        
        if (affected != null) {
            numAffected = affected.length;
            affectedStats = affected;
        } // if
    } // constructor
    
    public String getName() {
        return name;
    } // getName
    
    // Legit don't know what this was supposed to do
    public int getNumAffected() {
        return numAffected;
    } // getNumAffected
    
    public StatBonus getAffectedStatAt(int index) {
        return affectedStats[index];
    } // getAffectedStatAt
    
    public void decrDuration() {
        duration--;
    } // decrDuration
    
    public int getDuration() {
        return duration;
    } // getDuration
} // Debuff