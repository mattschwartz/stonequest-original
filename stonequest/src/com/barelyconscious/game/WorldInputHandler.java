/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        WorldInputHandler.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Captures and handles incoming keyboard and mouse input
 *                   from the user, performing actions as necessary as they
 *                   relate to the World.
 **************************************************************************** */
package com.barelyconscious.game;

import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.gui.Cursors;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyMap;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.Loot2;
import com.barelyconscious.game.spawnable.Sprite;
import java.awt.Color;

public class WorldInputHandler extends Interactable {

    private final World world;

    public WorldInputHandler(World world, int x, int y, int width, int height) {
        this.world = world;
        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_WORLD);

        KeyMap.setDefaultKeyBindings();
    } // constructor

    public void resize(int x, int y, int width, int height) {
        super.setRegion(x, y, width, height);
    } // resize

    /**
     * When the user clicks the mouse, an action should be performed based on
     * where the mouse was clicked within the game world.
     *
     * @param buttonClicked which button was clicked
     * @param x the x coordinate of the mouse on the screen
     * @param y the y coordinate of the mouse on the screen
     */
    @Override
    public void mouseClicked(int buttonClicked, int clickCount, int x, int y) {
        Sprite sprite;

        super.mouseClicked(buttonClicked, clickCount, x, y);

        x = mouseX / Common.TILE_SIZE - world.getTileOffsX();
        y = mouseY / Common.TILE_SIZE - world.getTileOffsY();

        if (buttonClicked == Interactable.MOUSE_LEFT_CLICK) {
            if ((sprite = world.getSpriteAt(x, y)) != null) {
                if (sprite instanceof Player) {
                    System.out.println("You twiddle your thumbs.");
                    world.tick();
                } // if
                else {
                    world.getPlayer().moveTo(x, y);
                } // else
            } // if
            else if (world.canMove(x, y) && world.isTileVisited(x, y)) {
                world.getPlayer().moveTo(x, y);
            } // else
        } // if
    } // mouseClicked

    /**
     * Restores the mouse cursor when the user moves the cursor outside of the
     * world region.
     */
    @Override
    public void mouseExited() {
        super.mouseExited(); //To change body of generated methods, choose Tools | Templates.
        Cursors.setCursor(Cursors.DEFAULT_CURSOR);
    } // mouseExited

    /**
     * Renders information gathered from user input to the screen.
     *
     * @param screen the screen to render to
     */
    public void render(Screen screen) {
        renderMouseLocation(screen);
    } // render

    /**
     * Renders information about where the mouse is hovering, such as what tile
     * the mouse is over; if the mouse is hovering over an Entity and whether
     * that Entity can be attacked; if the mouse is hovering over a piece of
     * Loot that can be picked up.
     *
     * @param screen the screen to render to
     */
    private void renderMouseLocation(Screen screen) {
        int x;
        int y;
        int textOffsX;
        int textOffsY = Font.NEW_CHAR_HEIGHT;
        String mouseText = "";
        Tile tile;
        Sprite sprite;

        if (!mouseInFocus) {
            return;
        } // if

        x = mouseX - (mouseX % Common.TILE_SIZE);
        y = mouseY - (mouseY % Common.TILE_SIZE);

        sprite = world.getSpriteAt(mouseX / Common.TILE_SIZE - world.getTileOffsX(), mouseY / Common.TILE_SIZE - world.getTileOffsY());

        if (sprite != null) {
            if (sprite instanceof Player) {
                screen.drawRectangle(Common.ENTITY_HEALTH_RGB, x, y, Common.TILE_SIZE, Common.TILE_SIZE);

                mouseText = sprite.getName() + " (" + ((Player) sprite).getLevel() + ")";

                Cursors.setCursor(Cursors.DEFAULT_CURSOR);
            } // if
            else if (sprite instanceof Entity) {
                screen.drawRectangle(Common.GOLD_AMOUNT_TEXT_RGB, x, y, Common.TILE_SIZE, Common.TILE_SIZE);

                mouseText = "Attack " + sprite.getName() + " (" + ((Entity) sprite).getLevel() + ")";

                Cursors.setCursor(Cursors.ATTACK_ENTITY_CURSOR);
            } // else if
            else if (sprite instanceof Loot2) {
                screen.drawRectangle(Common.GOLD_AMOUNT_TEXT_RGB, x, y, Common.TILE_SIZE, Common.TILE_SIZE);

                mouseText = "Pick up " + sprite.getName();

                Cursors.setCursor(Cursors.LOOT_ITEM_CURSOR);
            } // else if

            textOffsX = (world.getWidth() - Font.getStringWidth(screen, mouseText)) / 2;
            screen.fillTransluscentRectangle(textOffsX - 5, 0, Font.getStringWidth(screen, mouseText) + 10, Font.NEW_CHAR_HEIGHT + 5);
            Font.drawFont(screen, mouseText, Color.white, false, textOffsX, textOffsY);

            return;
        } // if

        tile = world.getTileAt(mouseX / Common.TILE_SIZE - world.getTileOffsX(), mouseY / Common.TILE_SIZE - world.getTileOffsY());

        if (tile != null) {
            if (tile.hasCollision()) {
                screen.drawRectangle(Common.FONT_DAMAGE_TEXT_RGB, x, y, Common.TILE_SIZE, Common.TILE_SIZE);

                mouseText = "Cannot move here";

                Cursors.setCursor(Cursors.CANNOT_MOVE_HERE_CURSOR);
            } // if 
            else {
                screen.drawRectangle(Common.GOLD_AMOUNT_TEXT_RGB, x, y, Common.TILE_SIZE, Common.TILE_SIZE);

                mouseText = tile + " - click to walk";

                Cursors.setCursor(Cursors.MOVE_HERE_CURSOR);
            } // else

            textOffsX = (world.getWidth() - Font.getStringWidth(screen, mouseText)) / 2;
            screen.fillTransluscentRectangle(textOffsX - 5, 0, Font.getStringWidth(screen, mouseText) + 10, Font.NEW_CHAR_HEIGHT + 5);
            Font.drawFont(screen, mouseText, Color.white, false, textOffsX, textOffsY);
        } // if
        else {
            Cursors.setCursor(Cursors.DEFAULT_CURSOR);
        }
    } // renderMouseLocation
} // WorldInputHandler
