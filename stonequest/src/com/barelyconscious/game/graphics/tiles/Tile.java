/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Tile.java
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

import com.barelyconscious.game.graphics.Map;
import com.barelyconscious.game.graphics.UIElement;
import java.util.Random;

public class Tile {

    protected int tickCount = 0;
    protected int tileId;
    protected boolean blocksPathing;
    protected boolean blocksVision;
    public boolean isVisible = false;
    public boolean recentlySeen = false;
    public boolean unlightOnRefresh = false;
    protected Random random = new Random();
    protected String name;
    protected UIElement image;

    protected Tile() {
    }

    /**
     * Creates a Tile with the following parameters.
     *
     * @param name The name of the Tile
     * @param image The image for rendering to the screen
     * @param tileId The unique integer identifier for the Tile
     * @param blocksPathing If true, the Tile will block Entity pathing
     * @param blocksVision If true, the Tile will block sight for Entities
     */
    public Tile(String name, UIElement image, int tileId, boolean blocksPathing, boolean blocksVision) {
        this.tileId = tileId;
        this.name = name;
        this.image = image;
        this.blocksPathing = blocksPathing;
        this.blocksVision = blocksVision;
    }

    /**
     * Creates a Tile with the following parameters.
     *
     * @param name The name of the Tile
     * @param imagePath The file path for the location of the Tile
     * @param tileId The unique integer identifier for the Tile
     * @param blocksPathing If true, the Tile will block Entity pathing
     * @param blocksVision If true, the Tile will block sight for Entities
     */
    public Tile(String name, String imagePath, int tileId, boolean blocksPathing, boolean blocksVision) {
        this.tileId = tileId;
        this.name = name;
        this.image = UIElement.createUIElement(imagePath);
        this.blocksPathing = blocksPathing;
        this.blocksVision = blocksVision;
    }

    /**
     * Used for player inspection of Tiles.
     *
     * @return Returns the name of the Tile
     */
    public String getName() {
        return name;
    }

    /**
     * Descriptions are what the player sees when he/she inspects a Tile, so
     * they should be helpful to describe anything interesting about it.
     *
     * @return
     */
    public String getDescription() {
        return "A normal, uninteresting, inconspicuous bit of world.";
    }

    /**
     * If true, the Tile will block Entities from walking through it.
     *
     * @return Returns true if the Tile blocks Entity pathing.
     */
    public boolean hasCollision() {
        return blocksPathing;
    }

    /**
     * If true, the Tile will block Entities from seeing things beyond it.
     *
     * @return Returns true if the Tile blocks sight.
     */
    public boolean isSightBlocking() {
        return blocksVision;
    }

    /**
     * This method is called when an Entity walks over the Tile (if possible).
     */
    public void onWalkOver() {
    }

    /**
     * This method is called when this Tile is hit by a light source (e.g., the
     * player's vision).
     */
    public void onLight() {
    }

    /**
     * This method is called immediately after the Tile is removed from a light
     * source.
     */
    public void onUnlight() {
    }

    /**
     * This method is called every time a unit of time passes in the game.
     */
    public void tick() {
        tickCount++;
    }

    /**
     * Renders the Tile to the screen at the given coordinates. Some atypical
     * Tiles are rendered differently under special occasions.
     *
     * @param screen The screen to which the Tile will be rendered
     * @param map The map in which the Tile is rendered. Some tiles need to know
     * about surrounding Tiles
     * @param x The x starting location for the Tile to be rendered
     * @param y The y starting location for the Tile to be rendered
     */
    public void render(Map map, int x, int y) {
        if (isVisible) {
            image.render(x, y);
        }
        else if (recentlySeen) {
            image.renderShaded(x, y);
        }
    }

    /**
     * Renders the Tile slightly darker than normal to the screen at the given
     * coordinates. Some atypical Tiles are rendered differently under special
     * occasions.
     *
     * @param screen The screen to which the Tile will be rendered
     * @param map The map in which the Tile is rendered. Some tiles need to know
     * about surrounding Tiles
     * @param x The x starting location for the Tile to be rendered
     * @param y The y starting location for the Tile to be rendered
     */
    public void renderShaded(Map map, int x, int y) {
        image.renderShaded(x, y);
    }
}
