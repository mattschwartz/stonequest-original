/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Armor.java
 * Author:           Matt Schwartz
 * Date created:     07.09.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.StatBonus;

public class Armor extends Item {
    private int slotId;
    private int bonusArmor;
    private boolean isEquipped;
    
    public Armor(String name, int sellV, int armor, int slotId, int tileId, StatBonus... affixes) {
        super(name, sellV, 1, tileId, affixes);
        super.setItemDescription("Place " + armordIdToString(slotId) + " for best results.");
        super.options[USE] = "equip";
        
        this.slotId = slotId;
        bonusArmor = armor;
        isEquipped = false;
    } // constructor
    
    public int getArmorType() {
        return slotId;
    } // getArmorType
    
    public int getBonusArmor() {
        return bonusArmor;
    } // getBonusArmor
    
    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
        
        if (isEquipped) { 
            options[USE] = "unequip";
        } // if
        
        else {
            options[USE] = "equip";
        } // else
    } // setEquipped
    
    public boolean isEquipped() {
        return isEquipped;
        
    } // isEquipped/* Returns the name associated with a given equipment slot ID as a String */
    private static String armordIdToString(int armorId) {
        switch (armorId) {
            case Player.NECK_SLOT:      return "around neck";
            case Player.HELM_SLOT:      return "on head";
            case Player.EARRING_SLOT:   return "in ear";
            case Player.CHEST_SLOT:     return "on chest";
            case Player.OFF_HAND_SLOT:  return "in off hand";
            case Player.BELT_SLOT:      return "around waist";
            case Player.GREAVES_SLOT:   return "on legs";
            case Player.RING_SLOT:      return "on finger";
            case Player.BOOTS_SLOT:     return "on feet";
            default:                    return "??";
        } // switch
    } // armorIdToString

    @Override
    public String toString() {
        switch (slotId) {
            case Player.NECK_SLOT:
            case Player.HELM_SLOT:
            case Player.EARRING_SLOT:
            case Player.CHEST_SLOT:
            case Player.OFF_HAND_SLOT:
            case Player.BELT_SLOT:
            case Player.RING_SLOT:      return "a " + super.getDisplayName();
            case Player.GREAVES_SLOT:   
            case Player.BOOTS_SLOT:     return "some " + super.getDisplayName();
            default:                    return "Papaya fruitsauce";
        } // switch
    } // toString

    // Armor never stacks, even if it is exactly the same item
    @Override
    public int compareTo(Item item) {
        return -1;
    } // compareTo
} // Armor