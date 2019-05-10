/* *****************************************************************************
 * File Name:         PotionEffect.java
 * Author:            Matt Schwartz
 * Date Created:      02.19.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.player.activeeffects;

import com.barelyconscious.game.graphics.UIElement;

public class PotionEffect extends Buff {

    public static final int STATBUFF = 0;
    public static final int ANTIMAGIC = 1;
    public static final int ANTITOXIN = 2;
    private int potionType;
    private String name;

    public PotionEffect(String displayName, int potionType) {
        super(Buff.POTION, UIElement.POTION_ICON);
        name = displayName;
        this.potionType = potionType;
    } // constructor

    public String getDisplayName() {
        return name;
    } // getDisplayName
    
    public int getPotionType() {
        return potionType;
    } // getPotionType

    /**
     * Called when the potion effects go into effect on the player.
     */
    public void onApplication() {
        // Overridden by specific types of potions
    } // onApplication

} // PotionEffect
