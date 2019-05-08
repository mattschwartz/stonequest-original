/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        StatPotionEffect.java
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
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.player.Player;
import java.util.ArrayList;
import java.util.Arrays;

public class StatPotionEffect extends PotionEffect {
    private ArrayList<AttributeMod> affixes;

    public StatPotionEffect(String displayName, int duration, AttributeMod... affixes) {
        super(displayName, PotionEffect.STATBUFF);
        super.duration = duration;
        this.affixes = new ArrayList(Arrays.asList(affixes));
    } // constructor
    
    public int getNumAffixes() {
        return affixes.size();
    } // getNumAffixes

    public AttributeMod getAffectedAttributeAt(int index) {
        return affixes.get(index);
    } // getAffectedAttributeAt
    
    public AttributeMod[] getAffixesAsArray() {
        AttributeMod[] mods = new AttributeMod[affixes.size()];
        
        // Copy 
        for (int i = 0; i < affixes.size(); i++) {
            mods[i] = new AttributeMod(affixes.get(i).getStatId()-10, affixes.get(i).getAttributeModifier());
        } // for
        
        return mods;
    } // getAffixesAsArray

    @Override
    public int getDurationInTicks() {
        return duration;
    } // getDurationInTicks
    
    /**
     * Applies the potion's attribute modifiers to the player to give both negative
     * and positive attribute boosts.
     */
    @Override
    public void onApplication() {
        Game.player.applyBuff(this);
        
        for (AttributeMod mod : affixes) {
            Game.player.setTemporaryAttribute(mod.getStatId(), mod.getAttributeModifier());
        } // for
    } // onApplication

    @Override
    public void tick() {
        if (--duration <= 0) {
            onRemoval();
        } // if
    } // tick
    
    /**
     * Called to remove the potion's effects from the player, such as when the effects
     * end or the effect is forcefully removed early.
     */
    public void onRemoval() {
        Game.player.removeBuff(this);
        
        for (AttributeMod mod : affixes) {
            Game.player.setTemporaryAttribute(mod.getStatId(), -mod.getAttributeModifier());
        } // for
    } // onRemoval

    @Override
    public String toString() {
        return "Quaff to provide a temporary change in attributes.";
    } // toString
} // StatPotionEffect
