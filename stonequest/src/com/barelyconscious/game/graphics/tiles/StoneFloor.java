/* *****************************************************************************
   * File Name:         StoneFloor.java
   * Author:            Matt Schwartz
   * Date Created:      01.07.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.graphics.tiles;

import java.awt.Color;

public class StoneFloor extends Tile {
    public StoneFloor() {
        super(STONE_TILE_ID, "/tiles/world/stone_floor.png", false, false, false);
        miniMapColor = new Color(204, 204, 204).getRGB();
    } // constructor
} // StoneFloor