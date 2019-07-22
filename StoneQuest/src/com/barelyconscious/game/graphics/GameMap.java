/* *****************************************************************************
   * File Name:         GameMap.java
   * Author:            Matt Schwartz
   * Date Created:      01.04.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  Contains the data for the current gameMap as a flattened 2D
                        integer array of tile ids.  The Screen calls renderBackground()
                        to draw the Tiles to the screen as well as perform lighting
                        for the gameMap.  Each gameMap is generated randomly based on some
                        seed so that the same random gameMap can be generated repeatedly.
                        
   ************************************************************************** */

package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.tiles.Tile;
import java.util.Random;

public class GameMap {
    public final static int IS_VISIBLE = 256;           // 0b10000000
    public final static int RECENTLY_SEEN = 512;        // 0b100000000
    public final static int UNLIGHT_ON_REFRESH = 1024;  // 0b1000000000
    
    private int tileHeight;
    private int tileWidth;
    private int pixelHeight;
    private int pixelWidth;
    private int xStart = 0;
    private int yStart = 0;
    private int[] map;
    
    private int zoneLevel;
    private int remainingElites;
    private String zoneName;
    
    public GameMap(int tileWidth, int tileHeight, int pixelWidth, int pixelHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    } // constructor
    
    /**
     * Randomly generates a GameMap for the world based on some seed so that the
     * same GameMap can be generated repeatedly and at any point and in any portion
     * of the GameMap.
     * @param seed 
     */
    public void generateAreaMap(int seed, int level, String name) {
        Random ran = new Random(seed);
        zoneLevel = level;
        zoneName = name;
        
        // Decides how big the gameMap is when it is generated.
        map = new int[tileWidth * tileHeight];
        
        for (int x = 0; x < tileWidth; x++) {
            for (int y = 0; y < tileHeight; y++) {
                if (ran.nextInt(100) < 2)
                    map[x + y * tileWidth] = Tile.STONEWALL_TILE_ID;
                else 
                    if (ran.nextInt(100) < 85) {
                        map[x + y * tileWidth] = Tile.GRASS_TILE_ID;
                    }
                    else {
                        if (ran.nextInt(100) < 50) {
                            map[x + y * tileWidth] = Tile.GRASS_TILE_3_ID;
                        }
                        else 
                        map[x + y * tileWidth] = Tile.GRASS_TILE_2_ID;
                    }
            } // for
        } // for
    } // generateAreaMap
    
    /**
     * 
     * @return the number of tiles wide of the current gameMap
     */
    public int getTileWidth() {
        return tileWidth;
    } // getTileWidth
    
    /**
     * 
     * @return the number of tiles high of the current gameMap
     */
    public int getTileHeight() {
        return tileHeight;
    } // getTileHeight
    
    /**
     * 
     * @return the gameMap offset in the x direction for drawing
     */
    public int getXStart() {
        return xStart;
    } // getXStart
    
    /**
     * 
     * @return the gameMap offset in the y direction for drawing
     */
    public int getYStart() {
        return yStart;
    } // getYStart
    
    /**
     * Renders each tile to the Screen, lighting ones around the player, accounting
     * for Tiles that block player line of sight
     * @param screen the Screen to draw to
     */
    public void renderBackground(Screen screen) {
        int xx;
        int yy;
        int tileId;
        
        /* Unlight tiles that are no longer in view, even if they are out
            of frame. */
        for (int i = 0; i < map.length; i++) {
            if ( (map[i] & UNLIGHT_ON_REFRESH) >> 10 == 1) {
                map[i] &= ~IS_VISIBLE;
            } // if
        } // for
        
        /*
         * Iterate over each tile in the gameMap; if the tile's UNLIGHT_ON_REFRESH
         * flag (0x400) is set, the tile was recently seen but needs to be unlit 
         * in case the player moved; if the tile's RECENTLY_SEEN flag (0x200) is 
         * set, the tile has recently been seen by the player but is currently 
         * not in view and will be drawn darker than normal as if it were in 
         * shadow; if neither of these flags are set, then no tile is drawn at 
         * that location
         */
        for (int x = 0; x < pixelWidth / Common.TILE_SIZE; x++) {
            for (int y = 0; y < pixelHeight / Common.TILE_SIZE; y++) {
                xx = x + xStart;
                yy = y + yStart;
                tileId = xx + yy * tileWidth;
                
                if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) {
//                    Tile.fogTile.render(screen, x * Common.TILE_SIZE, y * Common.TILE_SIZE);
                    continue;
                } // if
                
                if ( (map[tileId] & RECENTLY_SEEN) >> 9 == 1) {
                    getMapTile(xx, yy).renderShadedTile(screen, x * Common.TILE_SIZE, y * Common.TILE_SIZE);
                } // if
                
                else if ( ((map[tileId] & RECENTLY_SEEN) >> 9 == 0) && (map[tileId] & IS_VISIBLE) >> 8 == 0) {
//                    Tile.fogTile.render(screen, x * Common.TILE_SIZE, y * Common.TILE_SIZE);
                }
            } // for
        } // for
        
        /* Call shadowcasting for each octant around the player in accordance to
            the recursive shadowcasting algorithm described on rogebasin */
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, -1, 0, 
                Game.player.getLightRadius(), 1);
        
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, 1, 0, 
                Game.player.getLightRadius(), 2);
        
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, 1, 0, 
                Game.player.getLightRadius(), 3);
        
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, -1, 0, 
                Game.player.getLightRadius(), 4);
        
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, -1, 0, 
                Game.player.getLightRadius(), 5);
        
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, 1, 0, 
                Game.player.getLightRadius(), 6);
        
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, 1, 0, 
                Game.player.getLightRadius(), 7);
        
        shadowCasting(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, 0, -1, 0, 
                Game.player.getLightRadius(), 8);
        
        /* Unlight some tiles around the player to make the light radius look
            more like a circle of light */
        circularizeLight(Game.world.getPlayerX() / Common.TILE_SIZE + xStart, 
                Game.world.getPlayerY() / Common.TILE_SIZE + yStart, Game.player.getLightRadius());
        
        /* Iterate over each tile in the gameMap; if the Tile's IS_VISIBLE flag (0x100)
            is set, the Tile has been marked visible and should be drawn to the
            screen; the Tile should then be marked RECENTLY_SEEN and UNLIGHT_ON_REFRESH
            for later passes */
        for (int x = 0; x < pixelWidth / Common.TILE_SIZE; x++) {
            for (int y = 0; y < pixelHeight / Common.TILE_SIZE; y++) {
                xx = x + xStart;
                yy = y + yStart;
                tileId = xx + yy * tileWidth;
                
                if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) {
                    continue;
                } // if
                
                if ( (map[tileId] & IS_VISIBLE) >> 8 == 1) {
                    getMapTile(xx, yy).render(screen, x * Common.TILE_SIZE, y * Common.TILE_SIZE);
                    map[xx + yy * tileWidth] |= UNLIGHT_ON_REFRESH;
                    map[xx + yy * tileWidth] |= RECENTLY_SEEN;
                } // if
            } // for
        } // for
        
    } // renderMap
    
    /**
     * Recursive shadow casting algorithm described on Roguebasin: 
     * http://roguebasin.roguelikedevelopment.org/index.php?title=FOV_using_recursive_shadowcasting
     * @param centerX the player's x coordinate
     * @param centerY the player's y coordinate
     * @param depth the row or column in the algorithm's search
     * @param startSlope the starting slope for the algorithm's search
     * @param endSlope the end slope for the algorithm's search
     * @param radius how far the player's light source carries
     * @param octant which octant to perform the algorithm in
     */
    private void shadowCasting(int centerX, int centerY, int depth, double startSlope, double endSlope, int radius, int octant) {
        int yy;
        int xx;
        final double originalStartSlope = startSlope; 
        final double originalEndSlope = endSlope; 
        boolean seenBlockedTile;
        
        /* Diagram from roguebasin depicting each octant around the player, @
         
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
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
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
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
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
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
                                // calculate new startSlope
                                startSlope = ((x * 1.0) + 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
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
                                
                                shadowCasting(centerX, centerY, y - 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            } // if
                            else {
                                seenBlockedTile = false;
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
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
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
                                seenBlockedTile = false;
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
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
                                
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope, 
                                        endSlope, radius, octant);
                            } // else
                        } // if
                        
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
                                seenBlockedTile = false;
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
                            map[xx + yy * tileWidth] |= IS_VISIBLE; // display the blocking tile
                            
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
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
                                seenBlockedTile = false;
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
                        
                        if (xx < 0 || yy < 0 || xx >= tileWidth || yy >= tileHeight) continue;
                        
                        map[xx + yy * tileWidth] |= IS_VISIBLE;
                        
                        
                        // Blocking tile found
                        if (Tile.doesBlockSight( map[xx + yy * tileWidth] & 0xFF)) {
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
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            } // if
                            else {
                                seenBlockedTile = false;
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
    } // checkVisibility
    
    /**
     * Unlight outter tiles to give the appearance of a circular light source
     * emanating from around the player.
     * @param centerX player's x coordinate
     * @param centerY player's y coordinate
     * @param radius the distance from the player that the light source extends
     */
    private void circularizeLight(int centerX, int centerY, int radius) {
        int y;
        int x;
        
        /* A diagram of how the light source should look around the player 

         . . . . x x x x x x x . . . .    . = unlight tiles
         . . x x x x x x x x x x x . .    x = light tiles
         . x x x x x x x x x x x x x .    @ = player
         . x x x x x x x x x x x x x .
         x x x x x x x x x x x x x x x
         x x x x x x x x x x x x x x x
         x x x x x x x x x x x x x x x
         x x x x x x x @ x x x x x x x
         x x x x x x x x x x x x x x x
         x x x x x x x x x x x x x x x
         x x x x x x x x x x x x x x x
         . x x x x x x x x x x x x x .
         . x x x x x x x x x x x x x .
         . . x x x x x x x x x x x . .
         . . . . x x x x x x x . . . .
         
         Quadrant 0 = y-radius, x+radius
         Quadrant 1 = y-radius, x-radius
         Quadrant 2 = y+radius, x-radius
         Quadrant 3 = y+radius, x+radius
         */
        
        // Outtermost corners of the light radius
        for (int i = 0; i < 4; i++) {
            // Quadrant 0
            y = centerY - radius;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 1
            y = centerY - radius;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 2
            y = centerY + radius;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 3
            y = centerY + radius;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if
        } // for

        // 1 tile closer to the player
        for (int i = 0; i < 2; i++) {
            // Quadrant 0
            y = centerY - radius + 1;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if
            
            // Quadrant 1
            y = centerY - radius + 1;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 2
            y = centerY + radius - 1;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 3
            y = centerY + radius - 1;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if
        } // for

        // 2 tiles closer to the player
        for (int i = 0; i < 1; i++) {
            // Quadrant 0
            y = centerY - radius + 2;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 1
            y = centerY - radius + 2;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 2
            y = centerY + radius - 2;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 3
            y = centerY + radius - 2;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if
        } // for

        // 3 tiles close to the player
        for (int i = 0; i < 1; i++) {
            // Quadrant 0
            y = centerY - radius + 3;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 1
            y = centerY - radius + 3;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 2
            y = centerY + radius - 3;
            x = centerX - radius + i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if

            // Quadrant 3
            y = centerY + radius - 3;
            x = centerX + radius - i;
            
            if ( !(x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) ) {
                map[x + y * tileWidth] &= 0xEFF;
            } // if
        } // for
    } // circularizeLight
    
    /**
     * Moves the gameMap by xShift, yShift when the player moves.  This only changes
     * where the gameMap starts being drawn to the screen.
     * @param xShift
     * @param yShift 
     */
    public void shiftWorldBy(int xShift, int yShift) {
        xStart -= xShift / Common.TILE_SIZE;
        yStart -= yShift / Common.TILE_SIZE;
    } // shiftWorldBy
    
    /**
     * The level of the current level determines the level of the monsters in
     * the gameMap as well as items that drop for the player; higher levels mean
     * higher difficulty; this should not be changed after the zone is loaded
     * in.
     * @return the level of the current zone
     */
    public int getZoneLevel() {
        return zoneLevel;
    } // getZoneLevel
    
    /**
     * The value of elites decreases as each are killed by the player.
     * @return the number of elite/super monsters remaining in the area
     */
    public int getRemainingElites() {
        return remainingElites;
    } // getRemainingElites
    
    /**
     * This function is used to identify zones for the player.
     * @return the name of the currently loaded zone
     */
    public String getZoneName() {
        return zoneName;
    } // getZoneName
    
    /**
     * 
     * @param x the x coordinate of the Tile in question
     * @param y the y coordinate of the Tile in question
     * @return true if the player can move past the Tile at x,y
     */
    public boolean canMove(int x, int y) {
        x += xStart;
        y += yStart;
        
        if (x < 0 || y < 0 || x >= tileWidth || y >= tileHeight) {
            return false;
        } // if
        
        return ! (Tile.getTile((byte)map[x + y * tileWidth]).hasCollision());
    } // canMove
    
    /**
     * Returns the Tile at a position xPos,yPos.  
     * @param xPos
     * @param yPos
     * @return 
     */
    private Tile getMapTile(int xPos, int yPos) {
        if (xPos < 0 || yPos < 0 || xPos >= tileWidth || yPos >= tileHeight) {
            return Tile.getTile(Tile.WATER_TILE_ID);
        }
        int tileId = map[xPos + yPos * tileWidth];
        return Tile.getTile(tileId & 0xFF);
    } // getMapTile
    
    /**
     * 
     * @param xPos the x coordinate of the requested Tile
     * @param yPos the y coordinate of the requested Tile
     * @return the tile ID of the gameMap at xPos, yPos
     */
    public int getTileIdAt(int xPos, int yPos) {
        if (xPos < 0 || yPos < 0 || xPos >= tileWidth || yPos >= tileHeight) {
            return Tile.WATER_TILE_ID;
        } // if
        
        return map[xPos + yPos * tileWidth];
    } // getMapTile
    
    /**
     * Return the status of the light on a tile at xPos, yPos.  Returns the upper
     * 2 bits of a tile, which represent a visible tile and a recently seen tile.
     * @param xPos
     * @param yPos
     * @return 
     */
    public int tileLightStatus(int xPos, int yPos) {
        xPos /= Common.TILE_SIZE;// + xStart;
        yPos /= Common.TILE_SIZE;// + yStart;
        
        xPos += xStart;
        yPos += yStart;
        
        if (xPos < 0 || yPos < 0 || xPos >= tileWidth || yPos >= tileHeight)
            return 0;
        
        return map[xPos + yPos * tileWidth] & ~0xFF;
    } // tileLightStatus
    
    /**
     * Removes a tile at xPos,yPos.
     * @param xPos
     * @param yPos 
     */
    public void removeTile(int xPos, int yPos) {
        
    } // removeTile

    public boolean isTileVisibleAt(int xPos, int yPos) {
        xPos /= Common.TILE_SIZE;// + xStart;
        yPos /= Common.TILE_SIZE;// + yStart;
        
        xPos += xStart;
        yPos += yStart;
        
        if (xPos < 0 || yPos < 0 || xPos >= tileWidth || yPos >= tileHeight)
            return false;
        return ((map[xPos + yPos * tileWidth] & ~0xFF) & GameMap.IS_VISIBLE) >> 8 == 1;
    }

    public boolean isTileRecentlySeenAt(int xPos, int yPos) {
        xPos /= Common.TILE_SIZE;// + xStart;
        yPos /= Common.TILE_SIZE;// + yStart;
        
        xPos += xStart;
        yPos += yStart;
        
        if (xPos < 0 || yPos < 0 || xPos >= tileWidth || yPos >= tileHeight)
            return false;
        return ((map[xPos + yPos * tileWidth] & ~0xFF) & GameMap.RECENTLY_SEEN) >> 9 == 1;
    }
} // GameMap
