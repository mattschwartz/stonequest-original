/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Augment.java
 * Author:           Matt Schwartz
 * Date created:     12.18.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.spawnable.Entity;

public class Augment extends Item {
    public Augment(String name, int itemLevel, Entity owner) {
        super(name, itemLevel, 0, 1, "", owner);
    }
}
