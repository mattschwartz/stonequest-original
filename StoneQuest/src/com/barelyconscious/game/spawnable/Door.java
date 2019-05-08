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

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Key;

public class Door extends Doodad {
    private int lockId;
    
    public Door(String displayName, int x, int y, int lockId) {
        super(displayName, Tile.DOOR_IRON_CLOSED_TILE_ID, Tile.DOOR_IRON_OPEN_TILE_ID, x, y);
        this.lockId = lockId;
    } // constructor
    
    /**
     * Attempts to open the Door, allowing Entities to pass through it; some doors require keys that match the lock 
     * in order to open them.
     * @return true if the Player has the key that will unlock the door, false otherwise
     */
    public boolean openDoor() {
        Item item;
        
        for (int i = 0; i < Game.inventory.getNumItems(); i++) {
            if ( (item = Game.inventory.getItemAt(i)) instanceof Key) {
                if (((Key)item).getLockId() == lockId) {
                    interact();
                    Game.textLog.writeFormattedString("You open the door.", Common.FONT_NULL_RGB);
                    return true;
                } // if
            } // if
        } // for
        
        Game.textLog.writeFormattedString("The door is locked.", Common.FONT_NULL_RGB);
        return false;
    } // openDoor
} // Door
