/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        AntimagicPotionEffect.java
 * Author:           Matt Schwartz
 * Date created:     05.27.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.player.activeeffects;

import com.barelyconscious.game.Game;

public class AntimagicPotionEffect extends PotionEffect {

    public AntimagicPotionEffect(String displayName) {
        super(displayName, PotionEffect.ANTIMAGIC);
    } // constructor

    /**
     * Removes all Magic debuffs from the player when the potion is
     * used.
     */
    @Override
    public void onApplication() {
        // Remove Magic debuffs
        for (int i = 0; i < Game.player.getNumDebuffs(); i++) {
            if (Game.player.getDebuffAt(i).getDebuffType() == Debuff.CURSE) {
                Game.player.removeDebuffAt(i--);
            } // if
        } // for
    } // onApplication

    @Override
    public String toString() {
        return "Quaff to cleanse your afflictions.";
    } // toString
} // AntimagicPotionEffect