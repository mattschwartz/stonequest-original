/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Curse.java
 * Author:           Matt Schwartz
 * Date created:     07.20.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Curses are harmful magical effects placed on the Player and
                     last for a temporary amount of time.  Curse extends the 
                     Debuff class and all types of Curses extend the Curse class.
                     Curses typically just lower attributes but some powerful
                     Curses can cause unique effects:
                    -Blindness: the player cannot see past a few tiles
                    -Teleporting: the player is randomly teleported to some other
                     tile within the map
                    -Confusion: movement becomes difficult as left, right, up and
                     down randomly cause the player to move in a different direction
                    -
 **************************************************************************** */

package com.barelyconscious.game.player.condition;

import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class Curse extends Condition {
    
    public Curse(int duration, String name, Entity affectedEntity, AttributeMod... affectedAttributes) {
        super(duration, Condition.DETRIMENT_CURSE_TYPE, name, affectedEntity, null, affectedAttributes);
    } // constructor

    @Override
    public String getDescription() {
        return "A spiritual affliction that affects your attributes.";
    } // getDescription
} // Curse
