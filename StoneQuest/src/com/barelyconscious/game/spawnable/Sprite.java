/* *****************************************************************************
* File Name:         Sprite.java
* Author:            Matt Schwartz
* Date Created:      02.18.2013
* Redistribution:    You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
* File Description:  Contains all functions and variables shared between the
                    spawnable objects, Entities and Doodads (such as Loot)
************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.graphics.tiles.Tile;

abstract public class Sprite {
    private boolean isVisible; // never seen; not displayed
    private boolean notUpdated; // seen but not recently; shaded
    private boolean removeOnTick;
    private boolean hasCollision;
    
    private int lastKnownX;
    private int lastKnownY;
    
    private int xPos;
    private int yPos;
    private String displayName;
    private int tileId;
    
    /**
     * Create a Sprite with the following parameters
     * @param displayname the name visible to the player of the Sprite
     * @param tileId the tile id of the artwork for the Sprite
     */
    public Sprite(String displayname, int tileId) {
        this.displayName = displayname;
        this.tileId = tileId;
        
        removeOnTick = false;
        isVisible = false;
        notUpdated = false;
    } // constructor
    
    /**
     * Returns whether the Sprite is visible to the player and thus whether it
     * should be drawn to the screen, if it is within sight.
     * @return 
     */
    public boolean isVisible() {
        return isVisible;
    } // isVisible
    
    /**
     * Sets the object visible or invisible.
     * @param visible 
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    } // setVisible
    
    public boolean wasRecentlySeen() {
        return notUpdated;
    } // wasRecentlySeen
    
    public void setRecentlySeen(boolean updated) {
        notUpdated = updated;
    } // setRecentlySeen
    
    /**
     * removeOnTick: if true, the Sprite will be removed on game tick()
     * @return 
     */
    public boolean getRemoveOnTick() {
        return removeOnTick;
    } // getRemoveOnTick
    
    public void setRemoveOnTick(boolean removable) {
        removeOnTick = removable;
    } // setRemoveOnTick
    
    /**
     * Return hasCollision: whether or not the Sprite has collision with other 
     * sprites.
     * @return 
     */
    public boolean hasCollision() {
        return hasCollision;
    } // hasCollision
    
    public void setCollision(boolean coll) {
        hasCollision = coll;
    } // setCollision
    
    public int getXPos() {
        return xPos;
    } // getXPos
    
    public int getYPos() {
        return yPos;
    } // getYPos
    
    public int getLastKnownXPos() {
        return lastKnownX;
    }
    
    public int getLastKnownYPos() {
        return lastKnownY;
    }
    
    /**
     * Changes the position of the Sprite.
     * @param x
     * @param y 
     */
    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    } // setPosition
    
    protected void setLastKnownPosition(int x, int y) {
        lastKnownX = x;
        lastKnownY = y;
    } // setLastKnownPosition
    
    /**
     * Changes the position of the Sprite by some deltaX, deltaY amount.
     * @param deltaX
     * @param deltaY 
     */
    public void changePositionBy(int deltaX, int deltaY) {
        xPos += deltaX;
        yPos += deltaY;
        lastKnownX += deltaX;
        lastKnownY += deltaY;
    } // changePositionBy
    
    /**
     * Returns the name of the Sprite.
     * @return 
     */
    public String getDisplayName() {
        return displayName;
    } // getName
    
    /**
     * Change the current tile id to tileId
     * @param tileId the new tile ID
     */
    public void setTileId(int tileId) {
        this.tileId = tileId;
    } // setTileId
    
    /**
     * Returns the tile for the Sprite to be drawn to the screen.
     * @return 
     */
    public Tile getTile() {
        return Tile.getTile(tileId);
    } // getIcon
    
    /**
     * Whatever needs to happen for a subclass on game onUpdate.  This method is
     * implemented in subclasses only.  Implementing subclasses should test if
     * the Sprite ought to be removed and then remove it.
     */
    public void tick() {
    } // tick
    
    /**
     * Dispose of the Sprite and clear any resources the sprite was using.  Not
     * sure if this is possible.
     */
    public void remove() {
        removeOnTick = true;
    } // remove
    
    /**
     * When the player walks over a Sprite that has no collision.  Generally only
     * called from Loot.
     */
    public void onWalkOver() {
    } // onWalkOver
    
    /**
     * Implemented in subclasses.  This method is called when the player acts
     * upon the Sprite in some way.
     */
    public void interact() {
    } // interact
    
    /**
     * Perform some operation on or with another Sprite.  
     * @param s 
     */
    public void interactWith(Sprite s) {
    } // interactWith

    @Override
    public String toString() {
        return getClass() + " " + displayName;
    }
    
} // Sprite
