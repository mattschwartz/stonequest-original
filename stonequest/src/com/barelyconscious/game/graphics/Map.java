/* *****************************************************************************
   * File Name:         Map.java
   * Author:            Matt Schwartz
   * Date Created:      01.04.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.tiles.Tile;
import java.util.Random;

public class Map {
    
    /**
     * Flattened 2D array of tile IDs which are mapped from left to right, top
     * to bottom.  Access by tileIds[x + y * width].
     */
    private byte[] map;
    private int height;
    private int width;
    private int xStart = 0;
    private int yStart = 0;
    
    public Map(int tileWidth, int tileHeight) {
        width = tileWidth;
        height = tileHeight;
    } // constructor
    
    /**
     * Randomly generates a Map for the world based on some seed so that the
     * same Map can be generated repeatedly and at any point and in any portion
     * of the Map.
     */
    public void generateAreaMap(int seed) {
        Random ran = new Random(seed);
        
        // Decides how big the map is when it is generated.
        map = new byte[width * height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (ran.nextInt(10) < 2)
                    map[x + y * width] = Tile.STONE_WALL_TILE_ID;
                else 
                map[x + y * width] = Tile.GRASS_TILE_ID;
            } // for
        } // for
    } // generateAreaMap
    
    /**
     * Render each tile in the view to the Screen.
     */
    public void renderBackground(Screen screen) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                getTile(x + xStart, y + yStart).render(screen, x*Common.TILE_SIZE, y*Common.TILE_SIZE);
            } // for
        } // for
    } // renderMap
    
    /**
     * Moves the map by xShift, yShift when the player moves.  This only changes
     * where the map starts being drawn to the screen.
     * @param xShift
     * @param yShift 
     */
    public void shiftWorldBy(int xShift, int yShift) {
        xStart -= xShift / Common.TILE_SIZE;
        yStart -= yShift / Common.TILE_SIZE;
    } // shiftWorldBy
    
    public boolean canMove(int x, int y) {
        x += xStart;
        y += yStart;
        
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        return ! (Tile.getTile(map[x + y * width]).hasCollision());
    } // canMove
    
    /**
     * Returns the Tile at a position xPos,yPos.  
     * @param xPos
     * @param yPos
     * @return 
     */
    public Tile getTile(int xPos, int yPos) {
        if (xPos < 0 || yPos < 0 || xPos >= width || yPos >= height) {
            return Tile.getTile(Tile.WATER_TILE_ID);
        }
        int tileId = map[xPos + yPos * width];
        return Tile.getTile(tileId);
    } // getTile
    
    /**
     * Removes a tile at xPos,yPos.
     * @param xPos
     * @param yPos 
     */
    public void removeTile(int xPos, int yPos) {
        
    } // removeTile
} // Map