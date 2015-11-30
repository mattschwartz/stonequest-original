/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Weapon.java
 * Author:           Matt Schwartz
 * Date created:     07.27.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.player.StatBonus;

public class Weapon extends Item {
    private float minimumDamageBonus;
    private float maximumDamageBonus;
    private boolean isEquipped;
    
    public Weapon(String name, int sellV, float min, float max, int tileId, StatBonus... effects) {
        super(name, sellV, 1, tileId, effects);
        super.setItemDescription("Hold the pointy end away from you and you'll be fine. Swing wildly for best results.");
        super.options[USE] = "equip";
        
        minimumDamageBonus = min;
        maximumDamageBonus = max;
        isEquipped = false;
    } // constructor
    
    public boolean isEquipped() {
        return isEquipped;
    } // isEquipped
    
    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
        
        if (isEquipped) { 
            options[USE] = "unequip";
        } // if
        else {
            options[USE] = "equip";
        } // else
    } // setEquipped
    
    public float getMinDamageBonus() {
        return minimumDamageBonus;
    } // getMinDamageBonus
    
    public float getMaxDamageBonus() {
        return maximumDamageBonus;
    } // getMaxDamageBonus

    // Weapons never stack
    @Override
    public int compareTo(Item item) {
        return -1;
    } // compareTo
} // Weapon