/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Ingredient.java
 * Author:           Matt Schwartz
 * Date created:     11.07.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class Ingredient extends Item {
    public Ingredient(String name, int itemLevel, int sellValue, int stackSize, String fileLocation, Entity owner, AttributeMod... itemAffixes) {
        super(name, itemLevel, sellValue, stackSize, name, owner, itemAffixes);
    }
}
