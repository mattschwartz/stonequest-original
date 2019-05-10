/* *****************************************************************************
   * File Name:         Buff.java
   * Author:            Matt Schwartz
   * Date Created:      02.19.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  Buffs are temporary boosts to a Player's attributes or
                        provide some other bonus for the Player.  Such benefits
                        which extend the Buff class are:
                        Potions
                        Auras
                        Beneficial Spells
                        Powerful effects from Scrolls
   ************************************************************************** */

package com.barelyconscious.game.player.activeeffects;

import com.barelyconscious.game.graphics.UIElement;

public class Buff {
    public static final int POTION = 0;
    public static final int AURA = 1;
    public static final int MAGIC_EFFECT = 2;
    public static final int SCROLL_EFFECT = 3;
    
    protected int buffType;
    protected int duration;
    protected UIElement buffIcon;

    public Buff(int buffType, UIElement icon) {
        this.buffType = buffType;
        buffIcon = icon;
    } // construtcor
    
    public int getBuffType() {
        return buffType;
    } // getBuffType
    
    public UIElement getBuffIcon() {
        return buffIcon;
    } // getBuffIcon
    
    /**
     * 
     * @return how long the potion's effects last on the player,
     * in number of game ticks
     */
    public int getDurationInTicks() {
        return duration;
    } // getDurationInTicks
    
    public void tick() {
        
    } // tick
} // Buff
