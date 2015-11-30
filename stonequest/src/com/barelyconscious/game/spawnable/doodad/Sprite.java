/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Sprite.java
 * Author:           Matt Schwartz
 * Date created:     07.19.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.spawnable.doodad;

import java.awt.image.BufferedImage;

public class Sprite {
    protected int xPos;
    protected int yPos;
    protected boolean removeOnWalk;
    protected boolean removeOnTick;
    protected boolean collision;
    protected boolean isVisible;
    protected BufferedImage displayIcon;
    protected String displayName;
    
    public Sprite(String name, BufferedImage img) {
        displayName = name;
        displayIcon = img;
        collision = false;
        isVisible = false;
    } // constructor
    
    public Sprite(String name, BufferedImage img, int x, int y, boolean collision, boolean removeable) {
        displayName = name;
        displayIcon = img;
        xPos = x;
        yPos = y;
        this.collision = collision;
        this.removeOnTick = removeable;
        isVisible = false;
    } // in-depth constructor
    
    public String getDisplayName() {
        return displayName;
    } // getDisplayname
    
    public void setCoordinates(int x, int y) {
        xPos = x;
        yPos = y;
    } // setCoordinates
    
    public void changeXBy(int delta) {
        xPos += delta;
    } // changeXBy
    
    public int getXPos() {
        return xPos;
    } // getXPos
    
    public void changeYBy(int delta) {
        yPos += delta;
    } // changeYBy
    
    public int getYPos() {
        return yPos;
    } // getYPos
    
    public void setVisible(boolean visible) {
        isVisible = visible;
    } // setVisible
    
    public boolean isVisible() {
        return isVisible;
    } // isVisible
    
    public void tick() {
        // do nothing
    } // tick
    
    /* Determines if the Object should be removed
        now */
    public boolean checkCanRemove() {
        return false;
    } // checkCanRemove
    
    public boolean isRemovable() {
        return removeOnTick;
    } // isRemovable
    
    public boolean hasCollision() {
        return collision;
    } // hasCollision
    
    public void action() {
        System.err.println(" > [ERR] No valid action().");
    } // action
    
    public void remove() {
        System.err.println(" > [ERR] No valid remove().");
    } // remove
    
    public BufferedImage getIcon() {
        return displayIcon;
    } // getIcon
} // Sprite