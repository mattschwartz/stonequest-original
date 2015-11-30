/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        GrassTile.java
 * Author:           Matt Schwartz
 * Date created:     12.18.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.tiles;

import com.barelyconscious.game.graphics.UIElement;

public class GrassTile extends Tile {

    private final int ALT_1_PROBABILITY = 85;
    private final int ALT_2_PROBABILITY = 15;
    private final UIElement GRASS_TILE_ALT_1 = UIElement.createUIElement("/gfx/tiles/world/environment/ground/grass.png");
    private final UIElement GRASS_TILE_ALT_2 = UIElement.createUIElement("/gfx/tiles/world/environment/ground/grass2.png");
    private final UIElement GRASS_TILE_ALT_3 = UIElement.createUIElement("/gfx/tiles/world/environment/ground/grass3.png");

    /**
     * Creates a Tile with the following parameters.
     */
    public GrassTile() {
        this.name = "grass";
        this.tileId = 0;
        this.blocksPathing = false;
        this.blocksVision = false;

        if (random.nextInt(100) < ALT_1_PROBABILITY) {
            this.image = GRASS_TILE_ALT_1;
        } // if
        else if (random.nextInt(100) < ALT_2_PROBABILITY) {
            this.image = GRASS_TILE_ALT_2;
        } // else if
        else {
            this.image = GRASS_TILE_ALT_3;
        } // else if
    } // constructor

    @Override
    public String getDescription() {
        return "Hairy dirt.";
    } // getDescription
} // GrassTile
