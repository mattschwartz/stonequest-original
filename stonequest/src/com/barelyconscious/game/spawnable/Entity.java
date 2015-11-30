/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Entity.java
 * Author:           Matt Schwartz
 * Date created:     07.19.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Game;

public class Entity extends Sprite {
    private int level;
    private float healthPoints;
    private int reach = 1;
    
    public Entity(String objectName, int tileId) {
        super(objectName, tileId);
        super.setCollision(true);
    } // constructor
    
    public Entity(String objectName, int tileId, int x, int y) {
        super(objectName, tileId);
        super.setCollision(true);
        super.setPosition(x, y);
    } // constructor
    
    public void changeHealthBy(float amount) {
        healthPoints += amount;
        if (healthPoints < 0) {
            healthPoints = 0;
            remove();
        } // if
    } // changeHealthBy
    
    public int getLevel() {
        return level;
    } // getLevel
    
    public void setLevel(int level) {
        this.level = level;
    } // setLevel
    
    /* Remove this entity from the 
        World Map */
    @Override
    public void tick() {
        if (healthPoints <= 0) {
            remove();
        } // if
    } // checkCanRemove
    
    /**
     * Used by subclasses when creating the entity.
     * @param health 
     */
    protected void setHealthPoints(float health) {
        healthPoints = health;
    } // setHealthPoints
    
    protected float getHealthPoints() {
        return healthPoints;
    } // getHealthPoints
    
    public void moveUp() {
        changePositionBy(0, -1);
    } // moveUp
    
    public void moveDown() {
        changePositionBy(0, 1);
    } // moveDown
    
    public void moveLeft() {
        changePositionBy(-1, 0);
    } // moveLeft
    
    public void moveRight() {
        changePositionBy(1, 0);
    } // moveRight
    
    /**
     * How far the entity can be and still reach the target.  Minimum value is 1
     * which is also the default value.
     */
    public void setReach(int r) {
        reach = r;
    } // setReach
    
    /**
     * How far an Entity can perform action() from another Entity
     * @return 
     */
    public int getReach() {
        return reach;
    } // getReach
    
    @Override
    public void remove() {
//        Sound.ENTITY_DEATH.play();
        Game.world.removeEntity(this);
    } // onDeath

    /**
     * This function should probably be decommissioned.
     * @return 
     */
    @Override
    public String toString() {
        return super.getName() + "@coord: [" + super.getXPos() + ", " + 
                super.getYPos() + "]";
    } // toString
} // Entity