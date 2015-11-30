/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Curse.java
 * Author:           Matt Schwartz
 * Date created:     07.20.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.player.activeeffects;

import com.barelyconscious.game.player.StatBonus;

public class Curse extends Debuff {
    public Curse(String name, int dur, StatBonus... affected) {
        super(name, dur, affected);
    } // constructor
} // Curse