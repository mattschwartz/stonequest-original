/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Debuff.java
 * Author:           Matt Schwartz
 * Date created:     07.25.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Debuffs are harmful effects associated with a Player.  Each
                     Debuff lasts for some temporary amount of game time after 
                     which the Debuff naturally dissolves.  Players can alteratively
                     drink potions to remove Debuffs.  
 **************************************************************************** */

package com.barelyconscious.game.player.activeeffects;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;

public class Debuff {
    public static final int CURSE = 0;
    public static final int TOXIN = 1;
    
    protected int duration;
    private int numAffectedAttributes;
    private int debuffType;
    private String name;
    private AttributeMod [] affectedStats;
    private UIElement debuffIcon;
    
    /**
     * Create a new Debuff with the following parameters
     * @param name the name visible to the player of the debuff
     * @param dur how long the debuff lasts before it dissolves
     * @param affectedAttributes an array of attributes that are affected
     * for the duration of the Debuff
     */
    public Debuff(String name, int dur, int debuffType, UIElement icon, AttributeMod... affectedAttributes) {
        this.name = name;
        duration = dur;
        this.debuffType = debuffType;
        debuffIcon = icon;
        
        if (affectedAttributes != null) {
            numAffectedAttributes = affectedAttributes.length;
            affectedStats = affectedAttributes;
        } // if
    } // constructor
    
    /**
     * 
     * @return the name visible to the player of the debuff
     */
    public String getDisplayName() {
        return name;
    } // getDisplayName
    
    /**
     * 
     * @return the number of attributes that the Debuff affects during its duration
     */
    public int getNumAffectedAttributes() {
        return numAffectedAttributes;
    } // getNumAffectedAttributes
    
    /**
     * 
     * @param index the index requested
     * @return the affected attribute at index
     */
    public AttributeMod getAffectedAttributeAt(int index) {
        return affectedStats[index];
    } // getAffectedAttributeAt
    
    public int getDebuffType() {
        return debuffType;
    } // getDebuffType
    
    public UIElement getDebuffIcon() {
        return debuffIcon;
    } // getDebuffIcon
    
    /**
     * While active, the Debuff's duration decreases by 1 every tick of the game
     * until it hits 0 and is removed.
     */
    public void decrDuration() {
        duration--;
    } // decrDuration
    
    /**
     * 
     * @return the remaining duration of the Debuff in game time
     */
    public int getDuration() {
        return duration;
    } // getDuration
    
    public void tick() {
        if (--duration <= 0) {
            onRemoval();
        } // if
    } // tick
    
    /**
     * Extra effects that the debuff will apply when it is inflicted upon
     * the player.
     */
    public void onApplication() {
        
    } // onApplication
    
    /**
     * When the debuff is removed for any reason, this function will be called
     * so that the debuff can do or undo any effects that it needs to.
     */
    public void onRemoval() {
        Game.player.removeDebuff(this);
    } // onRemoval
} // Debuff
