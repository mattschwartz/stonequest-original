/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Player.java
 * Author:           Matt Schwartz
 * Date created:     09.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.player;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.item.Scroll;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.Faction;
import com.barelyconscious.game.spawnable.Sprite;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    public static final UIElement DEFAULT_PLAYER_ICON = new UIElement("/gfx/tiles/sprites/player/playerIcon.png");
    private int currentExperience = 0;
    /**
     * Scrolls are unknown to a player until he/she reads them. Those scrolls
     * are added to this list so that similar scrolls obtained in the future are
     * known
     */
    private List<Integer> identifiedScrolls = new ArrayList<Integer>();
    private Cauldron cauldron;

    /**
     * Creates a new player with the name playerName at level 1 with fresh
     * starting attributes.
     *
     * @param playerName the name of the new player
     */
    public Player(String playerName) {
        super(playerName, DEFAULT_PLAYER_ICON);
        super.faction = new Faction(Faction.PLAYER_FACTION);

        // Set the player's starting attributes
        setStartingAttributeValues();
    } // constructor

    /**
     * Sets the player's starting attributes to the level one for every
     * attribute except for health, which begins at 10. This method is only
     * called once per player.
     */
    private void setStartingAttributeValues() {
        attributes[HEALTH_ATTRIBUTE] = 10;

        for (int i = 1; i < NUM_ATTRIBUTES; i++) {
            attributes[i] = 1;
        } // for
    } // setStartingAttributeValues

    /**
     *
     * @param scroll the scroll to check against a list of identified scrolls
     * @return true if the scroll has been identified previously by the player
     */
    public boolean isScrollIdentified(int scrollId) {
        return identifiedScrolls.contains(scrollId);
    } // isScrollIdentified

    /**
     * If a Scroll has not been previously identified by the player, add it to
     * the list of known Scrolls.
     *
     * @param scroll the scroll being read
     */
    public void read(Scroll scroll) {
        if (!identifiedScrolls.contains(scroll.getScrollId())) {
            identifiedScrolls.add(scroll.getScrollId());
        } // if
    } // read

    /**
     * Adds amount worth of experience to the player's current pool of
     * experience, leveling the player to the next level if sufficient amount of
     * experience has been acquired
     *
     * @param amount
     */
    public void adjustExperienceBy(int amount) {
        if (currentExperience + amount >= getRequiredExperience()) {
            amount = getRequiredExperience() - currentExperience;
            currentExperience = amount;
            levelUp();
        } // if
        else {
            currentExperience += amount;
        } // else
    } // adjustExperienceBy

    /**
     *
     * @return the amount of experience the player has received since the most
     * recent level up
     */
    public int getCurrentExperience() {
        return currentExperience;
    } // getCurrentExperience

    public int getRequiredExperience() {
        return (int) (Math.ceil((level + 1) * Math.sqrt(Math.pow((level + 1), 3))));
    } // getRequiredExperience

    /**
     * Levels up the player and performs any necessary operations when that
     * happens. Level Ups occur when the player reaches the required amount of
     * experience for the next level.
     */
    private void levelUp() {
        currentExperience = 0;
    } // levelUp

    /**
     * Temporary? method that allows the user to click a location on the screen
     * to where the player will then move.
     *
     * @param deltaX the new x location
     * @param deltaY the new y location
     */
    public void moveTo(int deltaX, int deltaY) {
        new Thread(new PathFinder(this, deltaX - x, deltaY - y)).start();
    }

    @Override
    public void interact(Sprite interactee) {
        // if hostile sprite
        if (interactee.getFaction().isHostile(this)) {
        } // if

        if (interactee instanceof Entity) {
            ((Entity) interactee).changeHealthBy(-15);
        }

    } // interact

    @Override
    public void render(Screen screen, int xOffs, int yOffs) {
        DEFAULT_PLAYER_ICON.render(screen, xOffs, yOffs); //To change body of generated methods, choose Tools | Templates.
    } // render
} // Player

class PathFinder implements Runnable {

    private boolean running = false;
    private int deltaX;
    private int deltaY;
    private Entity entity;

    public void stop() {
        running = false;
    }

    public PathFinder(Entity entity, int x, int y) {
        this.entity = entity;
        running = true;

        deltaX = x;
        deltaY = y;
    }

    @Override
    public void run() {
        for (int x = 0; x < Math.abs(deltaX); x++) {
            try {
                if (deltaX > 0) {
                    if (!Game.getWorld().canMove(entity.getX() + 1, entity.getY())) {
                        entity.moveRight();
                        Game.getWorld().tick();
                        return;
                    }
                    entity.moveRight();
                } // if
                else {
                    if (!Game.getWorld().canMove(entity.getX() - 1, entity.getY())) {
                        entity.moveLeft();
                        Game.getWorld().tick();
                        return;
                    }
                    entity.moveLeft();
                } // else

                Game.getWorld().tick();
                Thread.sleep(30);
            } // for
            catch (InterruptedException ex) {
            }
        } // for


        for (int y = 0; y < Math.abs(deltaY); y++) {
            try {
                if (deltaY > 0) {
                    if (!Game.getWorld().canMove(entity.getX(), entity.getY() + 1)) {
                        entity.moveDown();
                        Game.getWorld().tick();
                        return;
                    }
                    entity.moveDown();
                } else {
                    if (!Game.getWorld().canMove(entity.getX(), entity.getY() - 1)) {
                        entity.moveUp();
                        Game.getWorld().tick();
                        return;
                    }
                    entity.moveUp();
                }

                Game.getWorld().tick();

                Thread.sleep(30);
            } // for
            catch (InterruptedException ex) {
            }
        } // for
    }
}
