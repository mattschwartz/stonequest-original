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
import com.barelyconscious.game.Game;
import com.barelyconscious.game.World;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.ingamemenu.TextLog;
import com.barelyconscious.game.spawnable.Sprite;
import com.barelyconscious.util.EntityHelper;
import java.util.Random;

public class SewerRatEntity extends Entity {

    public static final UIElement SEWER_RAT_ENTITY = UIElement.createUIElement("/gfx/tiles/sprites/entities/sewerRatIcon.png");
    private float minimumDamage = 0.75f;
    private float maximumDamage = 1.99f;
    private float criticalStrikeChance = 0.05f;
    private float criticalDamageMultiplier = 25f;
    private World world;

    public SewerRatEntity(int level, int x, int y) {
        super("Sewer Rat", x, y, SEWER_RAT_ENTITY);
        super.setLevel(level);
        super.setHealth(10f * (level * 0.76f));
        minimumDamage *= 1 + (level * 1.05);
        maximumDamage *= 1 + (level * 1.55);
        world = World.INSTANCE;
    } // constructor

    @Override
    public void tick() {
        int playerX = world.getPlayerX();
        int playerY = world.getPlayerY();
        int xDir = (playerX - x) >> 31;
        int yDir = (playerY - y) >> 31;

        /* Don't move if entity is in reach of player */
        if (((Math.abs(playerX - x) <= reach) && (playerY - y) == 0)
                || ((Math.abs(playerY - y) <= reach) && (playerX - x) == 0)) {
            interact(world.getPlayer());
            return;
        } // if

        // Pathing
        if (playerX > x) {
            xDir = 1;
        } // if
        if (playerY > y) {
            yDir = 1;
        } // if

        if (world.canMove(x + xDir, y + yDir) && !((x + xDir) == playerX && (y + yDir) == playerY)) {
            setPosition(x + xDir, y + yDir);
        } // if
    } // tick

    @Override
    public void interact(Sprite interactee) {
        /* If the sprite with which this SewerRat is interacting is hostile, 
         attack it. */
        if (interactee.getFaction().isHostile(this)) {
        } // if

        if (!(interactee instanceof Entity)) {
            return;
        } // if

        double hit = calculateHit();
        TextLog.INSTANCE.appendDamageMessage(this, (Entity)interactee, hit, EntityHelper.PHYSICAL_DAMAGE_TYPE);
        ((Entity) interactee).changeHealthBy(-hit);
    } // interact

    private float calculateHit() {
        // Calculate a number between min hit and max hit
        Random ran = new Random();
        float hit = minimumDamage;
        hit += (ran.nextFloat()) * (maximumDamage - minimumDamage);

        // Critical hit? multiply hit by critical multiplier
        if ((ran.nextDouble() * 100) <= criticalStrikeChance) {
            hit *= 1 + (criticalDamageMultiplier / 100);
        } // if

        return hit;
    } // calculateHit
} // SewerRatEntity
