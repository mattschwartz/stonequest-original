/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Entity.java
 * Author:           Matt Schwartz
 * Date created:     07.19.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: An Entity is some character that the player can interact with
                     in some way.  All enemies, vendors and the Player are considered
                     Entities.  Each Enity has some number of health points, a level
                     and a reach, which is how far they can interact with the Player.
 **************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Game;

public class Entity extends Sprite {
    private int level;
    private int reach = 1;
    private int lightRadius = 10;
    private int faction;
    private double curHp;
    private double healthPoints;
    
    /**
     * Creates a new Entity with the following parameters
     * @param entityName the name of the Entity 
     * @param tileId the id of the artwork for the Entity
     */
    public Entity(String entityName, int tileId) {
        super(entityName, tileId);
        
        /* All Entities cause collision with the Player and each other */
        super.setCollision(true);
    } // constructor
    
    /**
     * Creates a new Entity with the following parameters
     * @param entityName the name of the Entity
     * @param tileId the id of the artwork for the Entity
     * @param x the x spawn location for the Entity
     * @param y the y spawn location for the Entity
     */
    public Entity(String entityName, int tileId, int x, int y) {
        super(entityName, tileId);
        super.setCollision(true);
        super.setPosition(x, y);
    } // constructor
    
    /**
     * Change the health of the Entity by amount
     * @param amount the amount to change the health of the Entity by
     */
    public void changeHealthBy(double amount) {
        curHp += amount;
        if (curHp < 0) {
            curHp = 0;
            remove();
        } // if
    } // changeHealthBy
    
    /**
     * 
     * @return the level of the Entity
     */
    public int getLevel() {
        return level;
    } // getLevel
    
    /**
     * Should only be set once per Entity
     * @param level the level of the Entity
     */
    public void setLevel(int level) {
        this.level = level;
    } // setLevel
    
    /**
     * Whatever the Entity needs to do on a game tick, including
     * marking the Entity for removal if its health points fall below 0.
     */
    @Override
    public void tick() {
        if (curHp <= 0) {
            remove();
        } // if
    } // checkCanRemove
    
    /**
     * Used by subclasses when creating the entity.
     * @param health the amount of health for the Entity
     */
    protected void setHealthPoints(float health) {
        curHp = health;
        healthPoints = health;
    } // setHealthPoints
    
    /**
     * 
     * @return the maximum amount of hitpoints for the Entity
     */
    public double getMaxHealth() {
        return healthPoints;
    } // getMaxHealth
    
    /**
     * 
     * @return the current health points of the Entity
     */
    public double getHealthPoints() {
        return curHp;
    } // getHealthPoints
    
    /**
     * Moves the Entity up on the GameMap one Tile.
     */
    public void moveUp() {
        changePositionBy(0, -1);
    } // moveUp
    
    /**
     * Moves the Entity down on the GameMap one Tile.
     */
    public void moveDown() {
        changePositionBy(0, 1);
    } // moveDown
    
    /**
     * Moves the Entity left on the GameMap one Tile.
     */
    public void moveLeft() {
        changePositionBy(-1, 0);
    } // moveLeft
    
    /**
     * Moves the Entity right on the GameMap one Tile.
     */
    public void moveRight() {
        changePositionBy(1, 0);
    } // moveRight
    
    /**
     * Set the reach of the Entity to r; the default value for the reach of Entities
     * is 1
     * @param r the new value for the reach of the Entity
     */
    public void setReach(int r) {
        reach = r;
    } // setReach
    
    /**
     * How far an Entity can perform action() from another Entity
     * @return the reach for the Entity
     */
    public int getReach() {
        return reach;
    } // getReach
    
    /**
     * 
     * @param newLightRadius changes the light radius of the Entity
     */
    public void setLightRadius(int newLightRadius) {
        lightRadius = newLightRadius;
    } // setLightRadius
    
    /**
     * 
     * @return the light radius for the Entity
     */
    public int getLightRadius() {
        return lightRadius;
    } // getLightRadius
    
    /**
     * Removes the Entity from the world.
     */
    @Override
    public void remove() {
//        Sound.ENTITY_DEATH.play();
        Game.world.removeEntity(this);
    } // onDeath
} // Entity
