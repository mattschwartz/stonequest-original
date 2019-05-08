/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        AttributeMod.java
 * Author:           Matt Schwartz
 * Date created:     07.08.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: A AttributeMod modifies a player attribute by some amount 
                     temporarily.  Many types of Items have AttributeMod's and when
                     they are used or consumed, alter the Player's attributes in
                     some way.
 **************************************************************************** */

package com.barelyconscious.game.player;

public class AttributeMod {    
    private int attributeId;
    private double modifierAmount;

    /**
     * Create a AttributeMod with the following parameters
     * @param id the id of the attribute of the Player which will be affected
     * by the modification
     * @param mod the amount to adjust the attribute by
     */
    public AttributeMod(int id, double mod) {
        attributeId = id;
        modifierAmount = mod;
    } // constructor
    
    /**
     * Returns the affected attribute plus 10 because it is current attributes which
     * are affected and not the Player's skill level
     * @return 
     */
    public int getStatId() {
        return attributeId + 10;
    } // getStatId
    
    /**
     * 
     * @return the amount by which the attribute is affected 
     */
    public double getAttributeModifier() {
        return modifierAmount;
    } // getAttributeModifier

    /**
     * Translates an attribute id into a String
     * @return 
     */
    @Override
    public String toString() {
        return Player.idToString(attributeId);
    } // toString
}  // AttributeMod
