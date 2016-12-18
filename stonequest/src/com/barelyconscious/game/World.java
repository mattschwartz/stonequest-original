/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        World.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: The World is responsible for the following operations:
 *                 - Provides an interface between objects within the game 
 *                   world.
 *                 - Handles the loading and rendering of Maps.
 *                 - Is responsible for rendering objects to the Screen.
 **************************************************************************** */
package com.barelyconscious.game;

import com.barelyconscious.game.graphics.Map;
import com.barelyconscious.game.graphics.gui.Component;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.services.Service;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.Loot;
import com.barelyconscious.game.spawnable.Sprite;
import java.util.ArrayList;
import java.util.List;

public class World implements Component, Service {
    
    public static final World INSTANCE = new World();

    /**
     * A best-fit width, measured in Tiles, of the Screen available for
     * the Map.
     */
    private int tilesWide;
    /**
     * A best-fit height, measured in Tiles, of the Screen available for
     * the Map.
     */
    private int tilesHigh;
    /**
     * As the user moves his/her character around in the world, the Map must be
     * rendered at an offset with the Screen. This is the x-coordinate offset.
     */
    private int tileOffsX;
    /**
     * As the user moves his/her character around in the world, the Map must be
     * rendered at an offset with the Screen. This is the x-coordinate offset.
     */
    private int tileOffsY;
    private int screenWidth;
    private int screenHeight;
    private Player player = null;
    private List<Entity> entityList = new ArrayList<Entity>();
    private List<Loot> lootList = new ArrayList<Loot>();
    public Map currentMap = new Map();
    public WorldInputHandler inputHandler = new WorldInputHandler();
    
    private World() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Only one world can be created at runtime."); 
        }
        
        // debugger
        currentMap.generateAreaMap(256, 256, -1, 25, "Kud arajhi steppes");
    }

    @Override
    public void start() {
        resize(SceneService.INSTANCE.getWidth(), SceneService.INSTANCE.getHeight());
    }

    @Override
    public void stop() {
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    /**
     * Resize the world with the given width and height: redetermine how many
     * tiles wide and high the new World is; redetermine the tile offset based
     * on where the player is located.
     *
     * @param width
     * @param height
     */
    public final void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;

        determineNumTiles();
        determineTileOffs();

        inputHandler.resize(0, 0, width, height);
    }

    /**
     * Determine, based on the screen width and height, the maximum number of
     * tiles wide and high that can fit on the Screen.
     */
    private void determineNumTiles() {
        tilesWide = (int) Math.floor(screenWidth * 1.0 / SceneService.TILE_SIZE) + 1;
        tilesHigh = (int) Math.floor(screenHeight * 1.0 / SceneService.TILE_SIZE) + 1;
    }

    /**
     * The player should be centered in the world, thus an offset of tiles must
     * be computed.
     */
    private void determineTileOffs() {
        // Center the World around the Player's position within it
        tileOffsX = (tilesWide / 2) - player.getX();
        tileOffsY = (tilesHigh / 2) - player.getY();
    }

    public int getTileOffsX() {
        return tileOffsX;
    }

    public int getTileOffsY() {
        return tileOffsY;
    }

    /**
     *
     * @return the number of tiles in a row
     */
    public int getTilesWide() {
        return tilesWide;
    }

    /**
     *
     * @return the number of tiles in a column
     */
    public int getTilesHigh() {
        return tilesHigh;
    }

    /**
     *
     * @param sprite
     */
    public void spawnSprite(Sprite sprite) {
        if (sprite instanceof Entity) {
            entityList.add((Entity) sprite);
        }
        else if (sprite instanceof Loot) {
            lootList.add((Loot) sprite);
        }
    }

    public Sprite getSpriteAt(int x, int y) {
        if (player.getX() == x && player.getY() == y) {
            return player;
        }

        for (Entity entity : entityList) {
            if (entity.getX() == x && entity.getY() == y && entity.isVisible() && !entity.shouldRemove()) {
                return entity;
            }
        }

        for (Loot loot : lootList) {
            if (loot.getX() == x && loot.getY() == y && loot.isVisible() && !loot.shouldRemove()) {
                return loot;
            }
        }

        return null;
    }

    /**
     * Returns the id of a tile at the provided coordinates, expected as the
     * actual world coordinates and not the screen coordinates.
     *
     * @param x the x coordinate of the tile to be retrieved
     * @param y the y coordinate of the tile to be retrieved
     * @return a nonzero value is the Tile at [x,y]; a value of -1 means
     * the tile cannot be seen by the player or is nonexistent
     */
    public Tile getTileAt(int x, int y) {
        // If tile is neither visible or recently seen, tile shouldn't be known by the player
        if (!currentMap.isTileVisibleAt(x, y)) {
            if (!currentMap.isTileRecentlySeenAt(x, y)) {
                return null;
            }
        }

        return currentMap.getTileAt(x, y);
    }

    public boolean isTileVisited(int x, int y) {
        return currentMap.isTileVisibleAt(x, y) || currentMap.isTileRecentlySeenAt(x, y);
    }

    /**
     *
     * @param x
     * @param y
     * @return returns true if the tile at x,y is visible to the player
     */
    public boolean isTileLit(int x, int y) {
        return currentMap.isTileVisibleAt(x, y);
    }

    /**
     * Spawns a new Player at x,y within the world. There is only one player per
     * instance of StoneQuest, thus this function should only be called once.
     *
     * @param player the player to be spawned
     * @param x the x location of the player
     * @param y the y location of the player
     */
    public void spawnPlayer(Player player, int x, int y) {
        if (this.player != null) {
            return;
        }

        this.player = player;
        this.player.setPosition(x, y);
        tick();
    }

    /**
     *
     * @return the current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return the player's x position in the world
     */
    public int getPlayerX() {
        return player.getX();
    }

    /**
     *
     * @return the player's x position in the world
     */
    public int getPlayerY() {
        return player.getY();
    }

    /**
     * Determines whether or not it is possible to move to the tile designated
     * by the x and y coordinates
     *
     * @param x the x coordinate of the tile in question
     * @param y the y coordinate of the tile in question
     * @return true if it is possible to move to the given tile and false if it
     * is not possible
     */
    public boolean canMove(int x, int y) {
        // Check map 
        if (!currentMap.canMove(x, y)) {
            return false;
        }

        // Check entity list
        for (Entity entity : entityList) {
            if (entity.getX() == x && entity.getY() == y) {
                return false;
            }
        }

        return true;
    }

    /**
     * Game logic - this method is called when the user consumes one turn and
     * calls tick() on every Entity and Loot object in the world.
     */
    public void tick() {
        Sprite sprite;

        for (int i = 0; i < entityList.size(); i++) {
            sprite = entityList.get(i);
            sprite.tick();

            if (sprite.shouldRemove()) {
                entityList.remove(i);
            }
        }

        for (int i = 0; i < lootList.size(); i++) {
            sprite = lootList.get(i);
            sprite.tick();

            if (sprite.shouldRemove()) {
                lootList.remove(i);
            }
        }

        player.tick();
        determineTileOffs();
    }

    @Override
    public void render() {
        // Makes sure everything is on the same page when it comes to where the player is located
        int pX = player.getX();
        int pY = player.getY();
        int offsX = tileOffsX;
        int offsY = tileOffsY;

        currentMap.renderBackground(this, pX, pY, offsX, offsY);

        renderLoot();
        renderEntities();

        player.render((pX + offsX) * SceneService.TILE_SIZE, (pY + offsY) * SceneService.TILE_SIZE);

        inputHandler.render();
    }

    private void renderLoot() {
        int lootX = 15;
        int lootY = 15;

        lootX += tileOffsX;
        lootY += tileOffsY;

        if (lootX > 0 && lootY > 0) {
//            Tile.lootTile.render(screen, lootX * Common.TILE_SIZE, lootY * Common.TILE_SIZE);
        }
    }

    private void renderEntities() {
        int x;
        int y;

        for (Entity entity : entityList) {
            x = entity.getX() + tileOffsX;
            y = entity.getY() + tileOffsY;

            if (x < 0 || y < 0) {
                continue;
            }

            x *= SceneService.TILE_SIZE;
            y *= SceneService.TILE_SIZE;

            entity.render(x, y);
        }
    }

    // Functions from interface ################################################
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return screenWidth;
    }

    @Override
    public int getHeight() {
        return screenHeight;
    }

    @Override
    public void setX(int newX) {
        // does nothing
    }

    @Override
    public void setY(int newY) {
        // does nothing
    }

    @Override
    public void dispose() {
        // Never disposes
    }

    @Override
    public boolean shouldRemove() {
        // Never gets removed
        return false;
    }
}
