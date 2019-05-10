/* *****************************************************************************
   * File Name:         EntitySewerRat.java
   * Author:            Matt Schwartz
   * Date Created:      10.20.2012
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.spawnable.entities;

import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Sound;
import com.barelyconscious.game.World;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.spawnable.Loot;
import java.util.Random;

public class SewerRatEntity extends Entity {
    private final World world;
    private float minimumDamage = 0.75f;
    private float maximumDamage = 1.99f;
    private float criticalStrikeChance = 0.05f;
    private float criticalDamageMultiplier = 25f;
    
//    private long hitFreq = 1000;
//    private long last = System.currentTimeMillis();
    
    public SewerRatEntity(World world, int level, int x, int y) {
        super("Sewer Rat", Tile.SEWER_RAT_TILE_ID);
        super.setLevel(level);
        super.setHealthPoints(10f * (level * 0.76f));
        minimumDamage *= 1 + (level * 1.05);
        maximumDamage *= 1 + (level * 1.55);
        this.world = world;
        super.setPosition(x, y);
        super.setLastKnownPosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
    } // constructor
    
    @Override
    public void tick() {
        
//        if (System.currentTimeMillis() < last + 1000) {
//            return;
//        }
//        
//        last = System.currentTimeMillis();
        
        int playerX = world.getPlayerX();
        int playerY = world.getPlayerY();
        int xDir = (playerX - getXPos()) >> 31;
        int yDir = (playerY - getYPos()) >> 31;
        int r = getReach() * Common.TILE_SIZE;
        
        /* Don't move if entity is in reach of player */
        if ( ((Math.abs(playerX - getXPos()) <= r) && (playerY - getYPos()) == 0) 
                || ((Math.abs(playerY - getYPos()) <= r) && (playerX - getXPos()) == 0) ) {
            interact();
            return;
        } // if
        
        // Pathing
        if (playerX > getXPos()) {
            xDir = 1;
        } // if
        if (playerY > getYPos()) {
            yDir = 1;
        } // if
        
        xDir *= Common.TILE_SIZE;
        yDir *= Common.TILE_SIZE;
        
        if (world.canMove(getXPos() + xDir, getYPos() + yDir) && !((getXPos() + xDir) == playerX && (getYPos() + yDir) == playerY)) {
            setPosition(getXPos() + xDir, getYPos() + yDir);
//            if (isVisible()) {
//                setLastKnownPosition(getXPos(), getYPos());
//            } // if
        } // if
        
        if (isVisible()) {
            setLastKnownPosition(getXPos(), getYPos());
        } // if
    } // tick
    
    @Override
    public void remove() {
        Loot drop;
        int goldTileId = Tile.GOLD_LOOT_STACK_TILE_ID;
        Random ran = new Random(System.currentTimeMillis());
        int amount = ran.nextInt(1400) + 1;
        
        amount *= super.getLevel();
        
        // Print death message and play sound
        Game.textLog.writeFormattedString(getDisplayName() + " dies.", Common.FONT_NULL_RGB, 
                new LineElement(getDisplayName(), true, Common.FONT_ENTITY_LABEL_RGB));
        
        // Drop any loot
        if (amount == 1) {
            goldTileId = Tile.GOLD_LOOT_SINGLE_TILE_ID; 
        } // if
        
        drop = new Loot(new Item("gold", 0, amount, goldTileId), getXPos(), getYPos());
        drop.setVisible(true);
        drop.setRemovableOnWalkover(true);
        
        world.addLoot(drop);
        
        super.remove();
    } // remove

    @Override
    public void interact() {
        float hit = calculateHit();
        Game.player.changeHealthBy(-hit);
        Game.textLog.writeFormattedString(getDisplayName() + " hits you for " + (int)hit + " physical.", 
                Common.FONT_DAMAGE_TEXT_RGB, new LineElement(getDisplayName(), true, 
                Common.FONT_ENTITY_LABEL_RGB));
        Sound.CHICKEN_CLUCK.play();
    } // interact
    
    private float calculateHit() {
        // Calculate a number between min hit and max hit
        Random ran = new Random();
        float hit =  minimumDamage;
        hit += (ran.nextFloat()) * (maximumDamage - minimumDamage);
        
        // Critical hit? multiply hit by critical multiplier
        if ((ran.nextDouble() * 100) <= criticalStrikeChance) {
            hit *= 1 + (criticalDamageMultiplier/100);
        } // if
        
        return hit;
    } // calculateHit
} // SewerRatEntity
