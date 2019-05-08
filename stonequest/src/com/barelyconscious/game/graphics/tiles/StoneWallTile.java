/* *****************************************************************************
   * File Name:         StoneWallTile.java
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

public class StoneWallTile extends Tile {
    public StoneWallTile() {
        super(STONEWALL_TILE_ID, "/tiles/world/stonewall.png", true, false, true);
        miniMapColor = new Color(65, 65, 65).getRGB();//new Color(120, 120, 120).getRGB();
    } // constructor
} // StoneWallTile