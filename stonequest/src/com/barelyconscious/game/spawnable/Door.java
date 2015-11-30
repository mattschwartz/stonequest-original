/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Door.java
 * Author:           Matt Schwartz
 * Date created:     06.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Key;
import com.barelyconscious.game.player.Inventory;

public class Door extends Doodad {
    private int lockId;
    
    public Door(String displayName, int x, int y, int lockId, UIElement initialTile, UIElement spentTile) {
        super(displayName, x, y, true, initialTile, spentTile);
        this.lockId = lockId;
    } // constructor
} // Door
