/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Salvage.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item.salvage;

import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class Salvage extends Item {
    public Salvage() {
        super("", 1, 1, 1, "", null);
    } // constructor
    
    public Salvage(String name, int itemLevel, int sellValue, int stackSize, String fileLocation, Entity owner, AttributeMod... itemAffixes) {
        super(name, itemLevel, sellValue, stackSize, name, owner, itemAffixes);
    } // constructor
} // Salvage
