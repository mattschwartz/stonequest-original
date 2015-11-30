/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        WorldFrame.java
 * Author:           Matt Schwartz
 * Date created:     07.19.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game;

import com.barelyconscious.game.graphics.Map;
import com.barelyconscious.game.graphics.tiles.PlayerTile;
import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.Loot;
import com.barelyconscious.game.spawnable.EntityList;
import com.barelyconscious.game.spawnable.LootList;
import com.barelyconscious.game.spawnable.entities.SewerRatEntity;
import java.util.ArrayList;

public class WorldFrame {
    private int playerX;
    private int playerY;
    
    private PlayerTile playerTile;
    private EntityList entityList;
    private LootList lootList;
    private final Player PLAYER;
    private final TextLog log;
    private final Map map;
    
    public WorldFrame(TextLog log, Map map) {
        playerTile = new PlayerTile();
        entityList = new EntityList();
        lootList = new LootList();
        PLAYER = Game.player;
        
        this.map = map;
        this.log = log;
        
        addEntity(new SewerRatEntity(this, 1, 40, 20));
        generateMap(250);
    } // constructor
    
    /**
     * When the player enters a new level of the game, a new map is needed.
     * @param depth 
     */
    public void generateMap(int depth) {
        map.generateAreaMap(depth);
    } // generateMap
    
    /**
     * Performs all necessary operations when the game is resized.
     */
    public void onResize() {
        playerX = Game.invenMenu.getOffsX() / 2;
        playerY = Game.attributesMenu.getOffsY() / 2;
        
        // Make sure the player is positioned correctly
        if ( (playerY % Common.TILE_SIZE) > (Common.TILE_SIZE / 2) ) {
            playerY += Common.TILE_SIZE - (playerY % Common.TILE_SIZE);
        } else {
            playerY -= playerY % 20;
        } // if-else
        
        if ( (playerX % Common.TILE_SIZE) > (Common.TILE_SIZE / 2) ) {
            playerX += Common.TILE_SIZE - (playerX % Common.TILE_SIZE);
        } else {
            playerX -= playerX % 20;
        } // if-else
    } // onResize
    
    /**
     * Introduce an Entity e into the world.
     * @param e 
     */
    public final void addEntity(Entity e) {
        entityList.add(e);
    } // addLoot
    
    /**
     * Remove an Entity e from the list of Entities to be drawn.
     * @param e 
     */
    public void removeEntity(Entity e) {
        entityList.remove(e);
    } // removeEntity
    
    public final void addLoot(Loot l) {
        lootList.add(l);
    } // addLoot
    
    public void removeLoot(Loot l) {
        lootList.remove(l);
    } // removeLoot
    
    public int getPlayerX() {
        return playerX;
    } // getPlayerX
    
    public int getPlayerY() {
        return playerY;
    } // getPlayerY
    
    /**
     * Used in Screen.java to draw the Entities to the screen
     * @return 
     */
    public EntityList getEntities() {
        return entityList;
    } // getEntities
    
    // Attempt to pick up an item at player's location
    public void pickupItem() {
        ArrayList<Loot> lootpile = lootList.getList(playerX, playerY);
        
        if (lootpile != null && lootpile.size() > 0) {
            if (lootpile.size() > 1) {
                Game.lootWindow.setActive();
                Game.lootWindow.setList(lootpile);
            } // if
            else {
                Game.lootWindow.clearFocus();
                PLAYER.interactWith(lootpile.get(0));
            } // else
        } // if
        
        tick();
    } // pickupItem
    
    /**
     * Causes the world to tick without the player having to perform some action.
     */
    public void waitTurn() {
        log.writeFormattedString("You wait a turn.", null);
        tick();
    } // waitTurn
    
    /**
     * Supply a change in x and y for the player to move.  If isRanged is true
     * the player will not move and instead will fire a projectile in the direction
     * supplied.
     * @param isRanged
     * @param deltaX
     * @param deltaY 
     */
    public void move(boolean isRanged, int deltaX, int deltaY) {
        // Hopefully this will be taken care of in a better fashion
        Entity entity;
        ArrayList<Loot> loot;
        
        deltaX *= Common.TILE_SIZE;
        deltaY *= Common.TILE_SIZE;
        
        // Move the player if possible
        entity = entityList.get(playerX + deltaX, playerY + deltaY);

        // interact with any entities in the next tile space
        if (entity != null) {
            PLAYER.interactWith(entity);
        } // if 
        
        else if (canMove(playerX + deltaX, playerY + deltaY)) {
            map.shiftWorldBy(-deltaX, -deltaY);
            shiftSpritesBy(-deltaX, -deltaY);
            loot = lootList.getList(playerX, playerY);

            if (loot != null) {
                    // Print all the loot on this tile to the log for the player to see
                for (int i = 0; i < loot.size(); i++) {
                    loot.get(i).onWalkOver();
                } // for
            } // if
        } // else if
        
        Game.lootWindow.clearFocus();
        tick();
    } // move
    
    /**
     * Move all sprites [entities and loot objects] by deltaX, deltaY to give the
     * appearance of a moved worldview.
     * @param deltaX direction in x to shift
     * @param deltaY direction in y to shift
     */
    private void shiftSpritesBy(int deltaX, int deltaY) {
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).changePositionBy(deltaX, deltaY);
        } // for
        
        for (int i = 0; i < lootList.size(); i++) {
            lootList.get(i).changePositionBy(deltaX, deltaY);
        } // for
    } // shiftSpritesBy
    
    /**
     * Returns whether or not the calling entity is able to move to the requested
     * position x,y.  Performs a check with the environment as well as other
     * entities.
     * @param x
     * @param y
     * @return 
     */
    public boolean canMove(int x, int y) {
        // Entity blocking path?
        if (entityList.get(x, y) != null) {
            return false;
        } // if
        
        // Environment blocking path?
        return map.canMove(x/Common.TILE_SIZE, y/Common.TILE_SIZE);
    } // canMove
    
    /* Game mechanics */
    public void tick() {
        PLAYER.tick();
        
        for (int i = 0; i < entityList.size(); i++) {
            int x = entityList.get(i).getXPos(); 
            int y = entityList.get(i).getYPos(); 
            int px = playerX; 
            int py = playerY;
            int r = PLAYER.getLightRadius() * Common.TILE_SIZE;
            
            if ( Math.abs(px-x) <= r && Math.abs(py-y) <= r) {
                entityList.get(i).setVisible(true);
            } // if
            
            entityList.get(i).tick();
        } // for
        
        for (int i = 0; i < lootList.size(); i++) {
            if (lootList.get(i).getRemoveOnTick()) {
                removeLoot(lootList.get(i));
                continue;
            } // if
            
            int x = lootList.get(i).getXPos(); 
            int y = lootList.get(i).getYPos(); 
            int px = playerX; 
            int py = playerY;
            int r = PLAYER.getLightRadius() * Common.TILE_SIZE;
            
            if ( Math.abs(px-x) <= r && Math.abs(py-y) <= r) {
                lootList.get(i).setVisible(true);
            } // if
            
            lootList.get(i).tick();
        } // for
    } // tick
    
    /**
     * Draw all entities (including the player) and the loot to the screen.
     * @param screen 
     */
    public void renderSprites(Screen screen) {
        int x;
        int y;
        
        for (int i = 0; i < entityList.size(); i++) {
            // Don't renderSprites enemies that aren't visible...
            if (entityList.get(i).isVisible()) {
                x = entityList.get(i).getXPos();
                y = entityList.get(i).getYPos();
                
                entityList.get(i).getTile().render(screen, x, y);
            } // if
        } // for
        
        for (int i = 0; i < lootList.size(); i++) {
            if (lootList.get(i).isVisible()) {
                x = lootList.get(i).getXPos();
                y = lootList.get(i).getYPos();
                
                if (x < 0 || y < 0 || (x + Common.TILE_SIZE) > Game.getWidth() || (y + Common.TILE_SIZE) > Game.getHeight()) {
                    continue;
                } // if
                
                lootList.get(i).getTile().render(screen, x, y);
            } // if
        } // for
        
        playerTile.render(screen, playerX, playerY);
        
        Game.lootWindow.render(screen);
    } // renderSprites
} // WorldFrame