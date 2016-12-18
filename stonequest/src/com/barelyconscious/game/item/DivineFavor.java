/* *****************************************************************************
 *   Project:        StoneQuest
 *   File name:      DivineFavor.java
 *   Author:         Matt Schwartz
 *   Date:           12.16.2013
 *   License:        You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email mattschwartz@utexas.edu for issues or concerns.
 *   Description:    
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.spawnable.Entity;

public class DivineFavor extends Item {
    public DivineFavor(String name, String imageLocation, Entity owner) {
        super(name, 0, 0, 1, name, owner);
    }

    @Override
    public String getDescription() {
        return "The gods smile upon this trinket.";
    }

    @Override
    public int compareTo(Item item) {
        return -1;
    }
}
