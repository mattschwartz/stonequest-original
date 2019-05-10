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

package com.barelyconscious.game.player.activeeffects;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;

public class Curse extends Debuff {
    /**
     * Creates a new Curse with the following parameters
     * @param name the name of the Curse
     * @param dur how long the Curse lasts
     * @param affectedAttributes an array of affected attributes
     */
    public Curse(String name, int dur, AttributeMod... affectedAttributes) {
        super(name, dur, Debuff.CURSE, UIElement.CURSE_ICON, affectedAttributes);
    } // constructor
} // Curse
