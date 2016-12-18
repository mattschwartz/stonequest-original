/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        CleansingPotionEffect.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.player.condition;

import com.barelyconscious.game.spawnable.Entity;

public class CleansingPotionEffect extends PotionEffect {

    public CleansingPotionEffect(Entity affectedEntity) {
        super(0, "Cleansing Potion", affectedEntity);
    }

    /**
     * Removes all curse Conditions from the Entity.
     */
    @Override
    public void apply() {
        for (Condition condition : affectedEntity.getConditions()) {
            if (condition.getConditionType() == Condition.DETRIMENT_CURSE_TYPE) {
                condition.remove();
            }
        }
        
        System.out.println(name + " cleanses your afflictions.");
    }

    @Override
    public String getDescription() {
        return "A tasteless, smooth liquid believed to have the ability to rid the body of spiritual afflictions.";
    }
}
