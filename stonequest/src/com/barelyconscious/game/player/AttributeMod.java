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

import com.barelyconscious.game.spawnable.Entity;

public class AttributeMod implements Comparable<AttributeMod> {

    private int attributeId;
    private double modifierAmount;

    /**
     * Create a AttributeMod with the following parameters
     *
     * @param id the id of the attribute of the Player which will be affected by
     * the modification
     * @param mod the amount to adjust the attribute by
     */
    public AttributeMod(int id, double mod) {
        attributeId = id;
        modifierAmount = mod;
    } // constructor

    /**
     * @return the attribute id
     */
    public int getAttributeId() {
        return attributeId;
    } // getAttributeId

    /**
     *
     * @return the amount by which the attribute is affected
     */
    public double getAttributeModifier() {
        return modifierAmount;
    } // getAttributeModifier

    /**
     * Translates an attribute id into a String
     *
     * @return
     */
    @Override
    public String toString() {
        return Entity.attributeIdToString(attributeId);
    } // toString

    /**
     * Two AttributeMods are considered equal if both the attribute they modify
     * and the amount by which they modify that attribute are equal.
     * @param mod
     * @return 
     */
    @Override
    public int compareTo(AttributeMod mod) {
        if (attributeId != mod.getAttributeId()) {
            return -1;
        } // if
        
        if (modifierAmount != mod.getAttributeModifier()) {
            return -1;
        } // if
        
        return 0;
    } // compareTo
}  // AttributeMod
