/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Sprite.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to destroy 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;

public class Sprite {

    protected int x;
    protected int y;
    protected int lastKnownX;
    protected int lastKnownY;
    protected boolean isVisible;
    protected boolean hasBeenSeen;
    protected boolean hasCollision;
    protected boolean destroy;
    protected String name;
    protected Faction faction;
    protected UIElement spriteIcon;

    /**
     * Sprites are mobile objects within the World, such as Entities and Loot.
     * This constructor creates a new Sprite with the following values:
     * @param name the name of the Sprite (visible to the player)
     * @param x the x coordinate of the Sprite
     * @param y the y coordinate of the Sprite
     * @param hasCollision if true, other Sprites will not be able to walk over
     * this Sprite; if true, this Sprite will not be able to walk over other
     * Sprites and will collide with other World objects
     * @param icon this is what is rendered to the Screen
     */
    public Sprite(String name, int x, int y, boolean hasCollision, UIElement icon) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hasCollision = hasCollision;
        faction = new Faction();
        spriteIcon = icon;
        destroy = false;

        isVisible = false;
        hasBeenSeen = false;
    } // constructor

    /**
     *
     * @return the x coordinate of the Sprite within the world
     */
    public int getX() {
        return x;
    } // getX

    /**
     *
     * @return the y coordinate of the Sprite within the world
     */
    public int getY() {
        return y;
    } // getY

    /**
     * Changes the x position of the Sprite to xPos.
     *
     * @param xPos the new x position of the Sprite
     */
    public void setX(int xPos) {
        x = xPos;
    } // setX

    /**
     * Changes the y position of the Sprite to xPos.
     *
     * @param yPos the new y position of the Sprite
     */
    public void setY(int yPos) {
        y = yPos;
    } // setY

    /**
     * Sets both the x and y coordinates of the Sprite simultaneously, for ease
     * when it is necessary to change both values.
     *
     * @param x the new x coordinate of the Sprite
     * @param y the new y coordinate of the Sprite
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    } // setPosition
    
    /**
     * Sets both the last known x and y coordinates of the Sprite simultaneously, for ease
     * when it is necessary to change both values.
     *
     * @param x the new last known x coordinate of the Sprite
     * @param y the new last known y coordinate of the Sprite
     */
    public void setLastKnownPosition(int x, int y) {
        lastKnownX = x;
        lastKnownY = y;
    } // setLastKnownPosition

    /**
     * Adjusts the Sprite's position by xShift, yShift. Useful when the world
     * must move around the player.
     *
     * @param xShift the value by which to shift the x coordinate of the Sprite
     * @param yShift the value by which to shift the y coordinate of the Sprite
     */
    public void shiftBy(int xShift, int yShift) {
        this.x += xShift;
        this.y += yShift;
    } // shiftBy

    /**
     *
     * @return the last known x position of the Sprite, as seen by the player
     */
    public int getLastKnownX() {
        return lastKnownX;
    } // getlastKnownX

    /**
     *
     * @return the last known y position of the Sprite, as seen by the player
     */
    public int getLastKnownY() {
        return lastKnownY;
    } // getLastKnownY

    /**
     *
     * @return true if the Sprite is currently visible to the player, false if
     * it is not
     */
    public boolean isVisible() {
        return isVisible;
    } // isVisible

    /**
     * Changes the visibility of the Sprite to visible.
     *
     * @param visible the new visibility of the Sprite
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    } // setVisible

    /**
     *
     * @return true if the Sprite has been seen by the player, false if it has
     * not
     */
    public boolean hasBeenSeen() {
        return hasBeenSeen;
    } // hasBeenSeen

    /**
     * Sets whether or not the Sprite has been seen by the player to beenSeen.
     *
     * @param beenSeen the new value for whether or not the Sprite has been seen
     * by the player
     */
    public void setHasBeenSeen(boolean beenSeen) {
        hasBeenSeen = beenSeen;
    } // setHasBeenSeen

    /**
     *
     * @return true if the Sprite has collision with other objects in the world
     */
    public boolean hasCollision() {
        return hasCollision;
    } // hasCollision
    
    /**
     * 
     * @return returns this Sprite's faction
     */
    public Faction getFaction() {
        return faction;
    } // getFaction

    /**
     *
     * @return the name of the Sprite (this is the name the player will see)
     */
    public String getName() {
        return name;
    } // getName

    /**
     * Method to be overriden by subclasses. All activity which must occur
     * during a game tick should be done here.
     */
    public void tick() {
    } // tick

    /**
     * Calling this method will cause the Sprite to be removed on the next game
     * tick.
     */
    public void remove() {
        destroy = true;
    } // destroy

    /**
     *
     * @return if true, the Sprite should be removed from the World
     */
    public boolean shouldRemove() {
        return destroy;
    } // shouldRemove

    /**
     * When one Sprite walks over another Sprite, it calls this function and
     * passes itself to the other Sprite.
     * <br>
     * Example: SpriteA walks over SpriteB, calling SpriteB.onWalkOver(SpriteA);
     *
     * @param interactee the calling Sprite
     */
    public void onWalkOver(Sprite interactee) {
    } // onWalkOver

    /**
     * When this Sprite attempts to interact with another Sprite, this method 
     * is called and the other Sprite is passed along with it.
     *
     * @param interactee the calling Sprite that is attempting to interact with
     * this Sprite
     */
    public void interact(Sprite interactee) {
    } // interact

    /**
     * Renders the Sprite to the screen, given
     *
     * @param screen
     */
    public void render(Screen screen, int xOffs, int yOffs) {
        if (isVisible) {
            spriteIcon.render(screen, xOffs, yOffs);
        } // if
        else if (hasBeenSeen) {
            spriteIcon.renderShaded(screen, xOffs, yOffs);
        } // else if

        // Otherwise, do not render
    } // render
} // Sprite
