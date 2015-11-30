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
   *                    spawnable objects, Entities and Doodads (such as Loot)
   ************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.graphics.tiles.Tile;

abstract public class Sprite {
    private boolean isVisible;
    private boolean removeOnTick;
    private boolean hasCollision;
    
    private int xPos;
    private int yPos;
    private String displayName;
    private int tileId;
    
    public Sprite(String displayname, int tileId) {
        this.displayName = displayname;
        this.tileId = tileId;
        // Default values
        removeOnTick = false;
        isVisible = false;
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
     * Return hasCollision: whether or not the Sprite has collision with the 
     * player.
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
    
    /**
     * Changes the position of the Sprite.
     * @param x
     * @param y 
     */
    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    } // setPosition
    
    /**
     * Changes the position of the Sprite by some deltaX, deltaY amount.
     * @param deltaX
     * @param deltaY 
     */
    public void changePositionBy(int deltaX, int deltaY) {
        xPos += deltaX;
        yPos += deltaY;
    } // changePositionBy
    
    /**
     * Returns the name of the Sprite.
     * @return 
     */
    public String getName() {
        return displayName;
    } // getName
    
    /**
     * Returns the tile for the Sprite to be drawn to the screen.
     * @return 
     */
    public Tile getTile() {
        return Tile.getTile(tileId);
    } // getIcon
    
    /**
     * Whatever needs to happen for a subclass on game update.  This method is
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
    
} // Sprite