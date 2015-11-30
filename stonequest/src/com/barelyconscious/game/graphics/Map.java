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
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.tiles.Tile;
import java.util.Random;

public class Map {
    private final int IS_VISIBLE = 256;
    
    /**
     * Flattened 2D array of tile IDs which are mapped from left to right, top
     * to bottom.  Access by tileIds[x + y * width].
     */
    private int[] map;
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
        map = new int[width * height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (ran.nextInt(100) < 2)
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
        int xx;
        int yy;
        int px = Game.world.getPlayerX() / Common.TILE_SIZE;
        int py = Game.world.getPlayerY() / Common.TILE_SIZE;
        int r = Game.player.getLightRadius();
        int tileId;
        
        // Parralellize this?
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, -1, 0, Game.player.getLightRadius(), 1);
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, 1, 0, Game.player.getLightRadius(), 2);
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, 1, 0, Game.player.getLightRadius(), 3);
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, -1, 0, Game.player.getLightRadius(), 4);
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, -1, 0, Game.player.getLightRadius(), 5);
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, 1, 0, Game.player.getLightRadius(), 6);
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, 1, 0, Game.player.getLightRadius(), 7);
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, Game.world.getPlayerY() / Common.TILE_SIZE + yStart,
                        0, -1, 0, Game.player.getLightRadius(), 8);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                xx = x + xStart;
                yy = y + yStart;
                tileId = xx + yy * width;
                
                if (xx < 0 || yy < 0 || xx >= width || yy >= height) {
                    continue;
                } // if
                
                if ( (map[tileId] & IS_VISIBLE) >> 8 == 1) {
                    getTile(xx, yy).render(screen, x * Common.TILE_SIZE, y * Common.TILE_SIZE);
//                                map[xx + yy * width] -= IS_VISIBLE;
                } // if
            } // for
        } // for
    } // renderMap
    
    private void shadowCasting(int centerX, int centerY, int depth, double startSlope, double endSlope, int radius, int octant) {
        int yy = -1;
        int xx = -1;
        final double originalStartSlope = startSlope; 
        final double originalEndSlope = endSlope; 
        boolean seenBlockedTile = false;
        
        /*        --Octants--
         
                    Shared
                    edge by
         Shared     1 & 2      Shared
         edge by\      |      /edge by
         1 & 8   \     |     / 2 & 3
                  \1111|2222/
                  8\111|222/3
                  88\11|22/33
                  888\1|2/333
         Shared   8888\|/3333  Shared
         edge by-------@-------edge by
         7 & 8    7777/|\4444  3 & 4
                  777/6|5\444
                  77/66|55\44
                  7/666|555\4
                  /6666|5555\
         Shared  /     |     \ Shared
         edge by/      |      \edge by
         6 & 7      Shared     4 & 5
                    edge by 
                    5 & 6
                                        */ 
        
        switch (octant) {
            case 1:
                // Go row-by-row
                for (int y = depth; y <= radius; y++) {
                    seenBlockedTile=false;
                    
                    // go left->right across the row
                    for (int x = (int)Math.round(startSlope * y); x <= (int)Math.round(originalEndSlope * y); x++) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int)Math.round(startSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
//                                endSlope = ((Math.abs(x) + 1) - 0.1)  / (y * 1.0);
//                                endSlope = (x >> 31 == 1) ? endSlope : -endSlope;
                                
                                endSlope = ((x * 1.0) - 1 + 0.1) / (y * 1.0);
                                
                                shadowCasting(centerX, centerY, y + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
            
            case 6: 
                // Go row-by-row
                for (int y = depth; y >= -radius; y--) {
                    seenBlockedTile=false;
                    // go left->right across the row
                    for (int x = (int)Math.round(startSlope * y); x <= (int)Math.round(originalEndSlope * y); x++) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int)Math.round(startSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(x) + 1) - 0.1)  / (y * 1.0);
                                endSlope = (x >> 31 == 1) ? endSlope : -endSlope;
                                shadowCasting(centerX, centerY, y - 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
                
            case 2:
                // Go row-by-row
                for (int y = depth; y <= radius; y++) {
                    seenBlockedTile=false;
                    
                    // right->left
                    for (int x = (int)Math.round(startSlope * y); x >= (int)Math.round(originalEndSlope * y); x--) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int)Math.round(startSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
//                                endSlope = (y * 1.0 - 0.1) / ((x * 1.0));
                                endSlope = ((x * 1.0) + 1 + 0.01) / (y * 1.0);
                                
                                shadowCasting(centerX, centerY, y + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) + 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
                
            case 5:
                // Go row-by-row
                for (int y = depth; y >= -radius; y--) {
                    seenBlockedTile=false;
                    
                    // go left->right across the row
                    for (int x = (int)Math.round(startSlope * y); x >= (int)Math.round(originalEndSlope * y); x--) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int)Math.round(originalEndSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
                                endSlope = (x + 1 - 0.1)  / (y * 1.0);
//                                endSlope = ((Math.abs(x) + 1) - 0.1)  / (y * 1.0);
//                                endSlope = (x >> 31 == 1) ? endSlope : -endSlope;
                                
                                shadowCasting(centerX, centerY, y - 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
                
            case 3: 
                // Go col-by-col
                for (int x = depth; x <= radius; x++) {
                    seenBlockedTile=false;
                    
                    // go top->bottom
                    for (int y = (int)Math.round(startSlope * x); y >= (int)Math.round(originalEndSlope * x); y--) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int)Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);
//                                endSlope = -endSlope;
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
                
            case 8: // top->bottom
                // Go col-by-col
                for (int x = depth; x >= -radius; x--) {
                    seenBlockedTile=false;
                    
                    // go top->bottom
                    for (int y = (int)Math.round(startSlope * x); y >= (int)Math.round(originalEndSlope * x); y--) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int)Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);
//                                endSlope = -endSlope;
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
                
            case 4:
                // Go col-by-col
                for (int x = depth; x <= radius; x++) {
                    seenBlockedTile=false;
                    
                    // go bottom->top
                    for (int y = (int)Math.round(startSlope * x); y <= (int)Math.round(originalEndSlope * x); y++) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int)Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);
                                endSlope = -endSlope;
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
//                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a col is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
                
            case 7: // bottom->top
                // Go col-by-col
                for (int x = depth; x >= -radius; x--) {
                    seenBlockedTile=false;
                    
                    // go bottom->top
                    for (int y = (int)Math.round(startSlope * x); y <= (int)Math.round(originalEndSlope * x); y++) {
                        yy = centerY - y;
                        xx = centerX + x;
                        
                        if (xx < 0 || yy < 0 || xx >= width || yy >= height) continue;
                        
                        // Blocking tile found
                        if (Tile.blocksLineOfSight( map[xx + yy * width] & 0xFF)) {
                            map[xx + yy * width] |= IS_VISIBLE; // display the blocking tile
                            
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            } // if
                            
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int)Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            } // else if
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);
                                endSlope = -endSlope;
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
//                                seenBlockedTile = false;
                                map[xx + yy * width] |= IS_VISIBLE;
                            } // else
                        } // else
                    } // for
                    
                    // If last tile in a col is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    } // if
                } // for
                break;
                
            default:
                return;
        } // switch
        
        
        
        
        
        
        
//        for (int x = px - r; x < px + r; x++) {
//            for (int y = py - r; y < py + r; y++) {
//                if (x < 0 || y < 0 || x >= width || y >= height) {
//                    continue;
//                }
//                if (getTile(x, y).blocksLineOfSight()) {
//                    System.err.println("blocking sight");
//                map[x + y * width] |= IS_VISIBLE; 
//                    break;
//                }
//                map[x + y * width] |= IS_VISIBLE; 
//            }
//        }
    } // checkVisibility
    
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
        return ! (Tile.getTile((byte)map[x + y * width]).hasCollision());
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
        return Tile.getTile(tileId & 0xFF);
    } // getTile
    
    /**
     * Removes a tile at xPos,yPos.
     * @param xPos
     * @param yPos 
     */
    public void removeTile(int xPos, int yPos) {
        
    } // removeTile
} // Map