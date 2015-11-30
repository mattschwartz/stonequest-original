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
package com.barelyconscious.game.player.condition;

import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class PotionEffect extends Condition {

    public PotionEffect(int duration, String name, Entity affectedEntity, AttributeMod... affectedAttributes) {
        super(duration, Condition.BENEFIT_POTION_TYPE, name, affectedEntity, null, affectedAttributes);
    } // constructor

    @Override
    public void apply() {
        super.apply();
        // Temporary, should eventually write out to the text log
        String applyMessage = name + " affects your attributes: ";
        
        for (AttributeMod attributeMod : affectedAttributes) {
            applyMessage += Entity.attributeIdToString(attributeMod.getAttributeId()) + " (" + attributeMod.getAttributeModifier() + "), ";
        } // for
        
        applyMessage = applyMessage.substring(0, applyMessage.length() - 2) + ".";
        
        System.out.println(applyMessage);
    } // apply

    @Override
    public String getDescription() {
        return "A thick, glowing liquid that grants you additional power.";
    } // getDescription
} // PotionEffect
