/* *****************************************************************************
 * File Name:         Map.java
 * Author:            Matt Schwartz
 * Date Created:      01.04.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  Contains the data for the current map as a flattened 2D
 integer array of tile ids.  The Screen calls renderBackground()
 to draw the Tiles to the screen as well as perform lighting
 for the map.  Each map is generated randomly based on some
 seed so that the same random map can be generated repeatedly.
                        
 ************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.World;
import com.barelyconscious.game.graphics.tiles.GrassTile;
import com.barelyconscious.game.graphics.tiles.StoneWallTile;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.services.SceneService;
import java.util.Random;

public class Map {

    private int numVerticalTiles;
    private int numHorizontalTiles;
    private int xStart = 0;
    private int yStart = 0;
    private Tile[] map;
    private int zoneLevel;
    private int remainingElites;
    private String zoneName;

    public Map() {
    }

    /**
     * Randomly generates a Map for the world based on some seed so that the
     * same Map can be generated repeatedly and at any point and in any portion
     * of the Map.
     *
     * @param seed
     */
    public void generateAreaMap(int width, int height, int seed, int level, String name) {
        Random ran = new Random(seed);
        
        numHorizontalTiles = width;
        numVerticalTiles = height;
        
        zoneLevel = level;
        zoneName = name;

        // Decides how big the map is when it is generated.
        map = new Tile[numHorizontalTiles * numVerticalTiles];

        for (int x = 0; x < numHorizontalTiles; x++) {
            for (int y = 0; y < numVerticalTiles; y++) {
                if (ran.nextInt(100) < 5) {
                    map[x + y * numHorizontalTiles] = new StoneWallTile();
                }
                else {
                    map[x + y * numHorizontalTiles] = new GrassTile();
                }
            }
        }
    }

    /**
     * Renders each tile to the Screen, lighting ones around the player,
     * accounting for Tiles that block player line of sight
     *
     * @param world
     * @param pX
     * @param xOffs
     * @param yOffs
     * @param pY
     */
    public void renderBackground(World world, int pX, int pY, int xOffs, int yOffs) {
        int tileId;

        /*
         * Iterate over each tile in the map; if the tile's UNLIGHT_ON_REFRESH
         * flag (0x400) is set, the tile was recently seen but needs to be unlit 
         * in case the player moved; if the tile's RECENTLY_SEEN flag (0x200) is 
         * set, the tile has recently been seen by the player but is currently 
         * not in view and will be drawn darker than normal as if it were in 
         * shadow; if neither of these flags are set, then no tile is drawn at 
         * that location
         */
        for (int x = -xOffs; x < world.getTilesWide() - xOffs; x++) {
            if (x < 0 || x >= numHorizontalTiles) {
                continue;
            }
            for (int y = -yOffs; y < world.getTilesHigh() - yOffs; y++) {
                tileId = x + y * numHorizontalTiles;

                if (y < 0 || y >= numVerticalTiles) {
                    continue;
                }

                if (map[tileId].recentlySeen) {
                    getTileAt(x, y).renderShaded(this, (x + xOffs) * SceneService.TILE_SIZE, (y + yOffs) * SceneService.TILE_SIZE);
                }
            }
        }

        /* Unlight tiles that are no longer in view, even if they are out
         of frame. */
        for (int i = 0; i < map.length; i++) {
            if (map[i].unlightOnRefresh) {
                map[i].isVisible = false;
            }
        }

        // Cast shadows around the player
        shadowCasting(pX, pY, world.getPlayer().getLightRadius());

        /* Iterate over each tile in the map; if the Tile's IS_VISIBLE flag (0x100)
         is set, the Tile has been marked visible and should be drawn to the
         screen; the Tile should then be marked RECENTLY_SEEN and UNLIGHT_ON_REFRESH
         for later passes */
        for (int x = -xOffs; x < world.getTilesWide() - xOffs; x++) {
            if (x < 0 || x >= numHorizontalTiles) {
                continue;
            }
            for (int y = -yOffs; y < world.getTilesHigh() - yOffs; y++) {
                tileId = x + y * numHorizontalTiles;

                if (y < 0 || y >= numVerticalTiles) {
                    continue;
                }

                if (map[tileId].isVisible) {
                    getTileAt(x, y).render(this, (x + xOffs) * SceneService.TILE_SIZE, (y + yOffs) * SceneService.TILE_SIZE);
                    map[tileId].unlightOnRefresh = true;
                    map[tileId].recentlySeen = true;
                }
            }
        }
    }

    private void shadowCasting(int playerX, int playerY, int lightRadius) {
        /* Call shadowcasting for each octant around the player in accordance to
         the recursive shadowcasting algorithm described on rogebasin */
        shadowCasting(playerX, playerY, 0, -1, 0, lightRadius, 1);
        shadowCasting(playerX, playerY, 0, 1, 0, lightRadius, 2);
        shadowCasting(playerX, playerY, 0, 1, 0, lightRadius, 3);
        shadowCasting(playerX, playerY, 0, -1, 0, lightRadius, 4);
        shadowCasting(playerX, playerY, 0, -1, 0, lightRadius, 5);
        shadowCasting(playerX, playerY, 0, 1, 0, lightRadius, 6);
        shadowCasting(playerX, playerY, 0, 1, 0, lightRadius, 7);
        shadowCasting(playerX, playerY, 0, -1, 0, lightRadius, 8);

        /* Unlight some tiles around the player to make the light radius look
         more like a circle of light */
        circularizeLight(playerX, playerY, lightRadius);
    }

    /**
     * Recursive shadow casting algorithm described on Roguebasin:
     * http://roguebasin.roguelikedevelopment.org/index.php?title=FOV_using_recursive_shadowcasting
     *
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
                    seenBlockedTile = false;

                    // go left->right across the row
                    for (int x = (int) Math.round(startSlope * y); x <= (int) Math.round(originalEndSlope * y); x++) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int) Math.round(startSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = ((x * 1.0) - 1 + 0.1) / (y * 1.0);

                                shadowCasting(centerX, centerY, y + 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            case 6:
                // Go row-by-row
                for (int y = depth; y >= -radius; y--) {
                    seenBlockedTile = false;

                    // go left->right across the row
                    for (int x = (int) Math.round(startSlope * y); x <= (int) Math.round(originalEndSlope * y); x++) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int) Math.round(startSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(x) + 1) - 0.1) / (y * 1.0);
                                endSlope = (x >> 31 == 1) ? endSlope : -endSlope;
                                shadowCasting(centerX, centerY, y - 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            case 2:
                // Go row-by-row
                for (int y = depth; y <= radius; y++) {
                    seenBlockedTile = false;

                    // right->left
                    for (int x = (int) Math.round(startSlope * y); x >= (int) Math.round(originalEndSlope * y); x--) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int) Math.round(startSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = ((x * 1.0) + 1 + 0.01) / (y * 1.0);

                                shadowCasting(centerX, centerY, y + 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) + 0.1) / (y * 1.0);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            case 5:
                // Go row-by-row
                for (int y = depth; y >= -radius; y--) {
                    seenBlockedTile = false;

                    // go left->right across the row
                    for (int x = (int) Math.round(startSlope * y); x >= (int) Math.round(originalEndSlope * y); x--) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (x == (int) Math.round(originalEndSlope * y)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = (x + 1 - 0.1) / (y * 1.0);

                                shadowCasting(centerX, centerY, y - 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = ((x * 1.0) - 0.1) / (y * 1.0);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            case 3:
                // Go col-by-col
                for (int x = depth; x <= radius; x++) {
                    seenBlockedTile = false;

                    // go top->bottom
                    for (int y = (int) Math.round(startSlope * x); y >= (int) Math.round(originalEndSlope * x); y--) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int) Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            case 8: // top->bottom
                // Go col-by-col
                for (int x = depth; x >= -radius; x--) {
                    seenBlockedTile = false;

                    // go top->bottom
                    for (int y = (int) Math.round(startSlope * x); y >= (int) Math.round(originalEndSlope * x); y--) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int) Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);

                                shadowCasting(centerX, centerY, x + 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a row is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            case 4:
                // Go col-by-col
                for (int x = depth; x <= radius; x++) {
                    seenBlockedTile = false;

                    // go bottom->top
                    for (int y = (int) Math.round(startSlope * x); y <= (int) Math.round(originalEndSlope * x); y++) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            map[xx + yy * numHorizontalTiles].isVisible = true; // display the blocking tile

                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int) Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);
                                endSlope = -endSlope;
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a col is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            case 7: // bottom->top
                // Go col-by-col
                for (int x = depth; x >= -radius; x--) {
                    seenBlockedTile = false;

                    // go bottom->top
                    for (int y = (int) Math.round(startSlope * x); y <= (int) Math.round(originalEndSlope * x); y++) {
                        yy = centerY - y;
                        xx = centerX + x;

                        if (xx < 0 || yy < 0 || xx >= numHorizontalTiles || yy >= numVerticalTiles) {
                            continue;
                        }

                        map[xx + yy * numHorizontalTiles].isVisible = true;

                        // Blocking tile found
                        if (map[xx + yy * numHorizontalTiles].isSightBlocking()) {
                            // If blocked tile has already been seen, keep going
                            if (seenBlockedTile) {
                                continue;
                            }
                            // if blocking tile is the very first tile found on a new row
                            else if (y == (int) Math.round(startSlope * x)) {
                                seenBlockedTile = true;
                                continue;
                            }
                            else {
                                seenBlockedTile = true;
                                endSlope = ((Math.abs(y) + 1) - 0.1) / (x * 1.0);
                                endSlope = -endSlope;
                                shadowCasting(centerX, centerY, x + 1, originalStartSlope,
                                        endSlope, radius, octant);
                            }
                        }
                        // Non-blocking tile
                        else {
                            // End of a blocking tile block
                            if (seenBlockedTile) {
                                seenBlockedTile = false;
                                // calculate new startSlope
                                startSlope = (y * 1.0) / ((x * 1.0) - 0.1);
                            }
                            else {
                                seenBlockedTile = false;
                            }
                        }
                    }

                    // If last tile in a col is a blocking tile, no need to keep going
                    if (seenBlockedTile) {
                        break;
                    }
                }
                break;

            default:
                return;
        }
    }

    /**
     * Unlight outter tiles to give the appearance of a circular light source
     * emanating from around the player.
     *
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

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 1
            y = centerY - radius;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 2
            y = centerY + radius;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 3
            y = centerY + radius;
            x = centerX + radius - i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }
        }

        // 1 tile closer to the player
        for (int i = 0; i < 2; i++) {
            // Quadrant 0
            y = centerY - radius + 1;
            x = centerX + radius - i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 1
            y = centerY - radius + 1;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 2
            y = centerY + radius - 1;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 3
            y = centerY + radius - 1;
            x = centerX + radius - i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }
        }

        // 2 tiles closer to the player
        for (int i = 0; i < 1; i++) {
            // Quadrant 0
            y = centerY - radius + 2;
            x = centerX + radius - i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 1
            y = centerY - radius + 2;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 2
            y = centerY + radius - 2;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 3
            y = centerY + radius - 2;
            x = centerX + radius - i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }
        }

        // 3 tiles close to the player
        for (int i = 0; i < 1; i++) {
            // Quadrant 0
            y = centerY - radius + 3;
            x = centerX + radius - i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 1
            y = centerY - radius + 3;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 2
            y = centerY + radius - 3;
            x = centerX - radius + i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }

            // Quadrant 3
            y = centerY + radius - 3;
            x = centerX + radius - i;

            if (!(x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles)) {
                map[x + y * numHorizontalTiles].isVisible = false;
            }
        }
    }

    /**
     * Moves the map by xShift, yShift when the player moves. This only changes
     * where the map starts being drawn to the screen.
     *
     * @param xShift
     * @param yShift
     */
    public void shiftWorldBy(int xShift, int yShift) {
        xStart -= xShift / SceneService.TILE_SIZE;
        yStart -= yShift / SceneService.TILE_SIZE;
    }

    /**
     * The level of the current level determines the level of the monsters in
     * the map as well as items that drop for the player; higher levels mean
     * higher difficulty; this should not be changed after the zone is loaded
     * in.
     *
     * @return the level of the current zone
     */
    public int getZoneLevel() {
        return zoneLevel;
    }

    /**
     * The value of elites decreases as each are killed by the player.
     *
     * @return the number of elite/super monsters remaining in the area
     */
    public int getRemainingElites() {
        return remainingElites;
    }

    /**
     * This function is used to identify zones for the player.
     *
     * @return the name of the currently loaded zone
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     *
     * @param x the x coordinate of the Tile in question
     * @param y the y coordinate of the Tile in question
     * @return true if the player can move past the Tile at x,y
     */
    public boolean canMove(int x, int y) {
        x += xStart;
        y += yStart;

        if (x < 0 || y < 0 || x >= numHorizontalTiles || y >= numVerticalTiles) {
            return false;
        }

        return !map[x + y * numHorizontalTiles].hasCollision();
    }

    /**
     * Returns the Tile at a position xPos,yPos.
     *
     * @param xPos
     * @param yPos
     * @return
     */
    public Tile getTileAt(int xPos, int yPos) {
//        if (xPos < 0 || yPos < 0 || xPos >= numHorizontalTiles || yPos >= numVerticalTiles) {
//            return new WaterTile();
//        }
        return map[xPos + yPos * numHorizontalTiles];
    }

    /**
     * Removes a tile at xPos,yPos.
     *
     * @param xPos
     * @param yPos
     */
    public void removeTile(int xPos, int yPos) {
    }

    public boolean isTileVisibleAt(int xPos, int yPos) {
        if (xPos < 0 || yPos < 0 || xPos >= numHorizontalTiles || yPos >= numVerticalTiles) {
            return false;
        }
        return map[xPos + yPos * numHorizontalTiles].isVisible;
    }

    public boolean isTileRecentlySeenAt(int xPos, int yPos) {
        if (xPos < 0 || yPos < 0 || xPos >= numHorizontalTiles || yPos >= numVerticalTiles) {
            return false;
        }
        return map[xPos + yPos * numHorizontalTiles].recentlySeen;
    }
}
