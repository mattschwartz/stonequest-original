/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         Key.java
   * Author:            Matt Schwartz
   * Date Created:      03.17.2013 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  Keys are used to open locked Doodads such as chests and
                        doors.  
   ************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.graphics.tiles.Tile;

public class Key extends Item {
    private final int LOCK_ID;
    
    public Key(String name, int sellValue, int lockId) {
        super(name, sellValue, Tile.KEY_TILE_ID);
        LOCK_ID = lockId;
    } // constructor
    
    /**
     * 
     * @return the lock id that the key fits into
     */
    public int getLockId() {
        return LOCK_ID;
    } // getLockId
} // Key
