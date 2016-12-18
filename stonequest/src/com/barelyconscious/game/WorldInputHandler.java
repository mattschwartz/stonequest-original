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

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.ShapeDrawer;
import com.barelyconscious.game.graphics.Viewport;
import com.barelyconscious.game.graphics.gui.Cursors;
import com.barelyconscious.game.graphics.gui.ingamemenu.TextLog;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyMap;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.services.InputHandler;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.Loot;
import com.barelyconscious.game.spawnable.Sprite;
import com.barelyconscious.util.ColorHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class WorldInputHandler extends Interactable {

    public WorldInputHandler() {
        super.addMouseListener(Interactable.Z_WORLD);

        KeyMap.setDefaultKeyBindings();
    }

    public void resize(int x, int y, int width, int height) {
        super.setRegion(x, y, width, height);
    }

    /**
     * When the user clicks the mouse, an action should be performed based on
     * where the mouse was clicked within the game World.INSTANCE.
     *
     * @param e The MouseEvent created when the user clicks the mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Sprite sprite;
        Tile tile;

        int x = e.getX() / SceneService.TILE_SIZE - World.INSTANCE.getTileOffsX();
        int y = e.getY() / SceneService.TILE_SIZE - World.INSTANCE.getTileOffsY();

        if (e.getButton() == Interactable.MOUSE_LEFT_CLICK) {
            if ((sprite = World.INSTANCE.getSpriteAt(x, y)) != null) {
                if (sprite instanceof Player) {
                    TextLog.INSTANCE.append("You twiddle your thumbs.");
                    World.INSTANCE.tick();
                }
                else {
                    World.INSTANCE.getPlayer().moveTo(x, y);
                }
            }
            else if (World.INSTANCE.canMove(x, y) && World.INSTANCE.isTileVisited(x, y)) {
                World.INSTANCE.getPlayer().moveTo(x, y);
            }
        }
        else if (e.getButton() == Interactable.MOUSE_RIGHT_CLICK) {
            if ((sprite = World.INSTANCE.getSpriteAt(x, y)) != null) {
                TextLog.INSTANCE.append(sprite.getDescription());
            }
            else if ((tile = World.INSTANCE.getTileAt(x, y)) != null) {
                TextLog.INSTANCE.append(tile.getDescription());
            }
        }
    }

    /**
     * Restores the mouse cursor when the user moves the cursor outside of the
     * World.INSTANCE region.
     */
    @Override
    public void mouseExited() {
        super.mouseExited(); //To change body of generated methods, choose Tools | Templates.
        Cursors.setCursor(Cursors.DEFAULT_CURSOR);
    }

    /**
     * Renders information gathered from user input to the screen.
     *
     */
    public void render() {
        renderMouseLocation();
    }

    /**
     * Renders information about where the mouse is hovering, such as what tile
     * the mouse is over; if the mouse is hovering over an Entity and whether
     * that Entity can be attacked; if the mouse is hovering over a piece of
     * Loot that can be picked up.
     *
     * @param screen the screen to render to
     */
    private void renderMouseLocation() {
        int x;
        int y;
        int textOffsX;
        int textOffsY = FontService.characterHeight;
        String mouseText = "";
        Tile tile;
        Sprite sprite;

        if (!isMouseInFocus()) {
            return;
        }

        x = InputHandler.INSTANCE.getMouseX();
        y = InputHandler.INSTANCE.getMouseY();

        x = x - (x % SceneService.TILE_SIZE);
        y = y - (y % SceneService.TILE_SIZE);

        sprite = World.INSTANCE.getSpriteAt(InputHandler.INSTANCE.getMouseX() / SceneService.TILE_SIZE - World.INSTANCE.getTileOffsX(), InputHandler.INSTANCE.getMouseY() / SceneService.TILE_SIZE - World.INSTANCE.getTileOffsY());

        if (sprite != null) {
            if (sprite instanceof Player) {
                ShapeDrawer.drawRectangle(ColorHelper.TILE_SELECT_PLAYER, x, y, SceneService.TILE_SIZE, SceneService.TILE_SIZE);

                mouseText = sprite.getName() + " (" + ((Player) sprite).getLevel() + ")";

                Cursors.setCursor(Cursors.DEFAULT_CURSOR);
            }
            else if (sprite instanceof Entity) {
                ShapeDrawer.drawRectangle(ColorHelper.TILE_SELECT_CAN_MOVE, x, y, SceneService.TILE_SIZE, SceneService.TILE_SIZE);

                mouseText = "Attack " + sprite.getName() + " (" + ((Entity) sprite).getLevel() + ")";

                Cursors.setCursor(Cursors.ATTACK_ENTITY_CURSOR);
            }
            else if (sprite instanceof Loot) {
                ShapeDrawer.drawRectangle(ColorHelper.TILE_SELECT_CAN_MOVE, x, y, SceneService.TILE_SIZE, SceneService.TILE_SIZE);

                mouseText = "Pick up " + sprite.getName();

                Cursors.setCursor(Cursors.LOOT_ITEM_CURSOR);
            }

            textOffsX = (World.INSTANCE.getWidth() - FontService.getStringWidth(mouseText)) / 2;
            ShapeDrawer.fillTransluscentRectangle(textOffsX - 5, 0, FontService.getStringWidth(mouseText) + 10, FontService.characterHeight + 5);
            FontService.drawFont(mouseText, Color.white, false, textOffsX, textOffsY);

            return;
        }

        tile = World.INSTANCE.getTileAt(InputHandler.INSTANCE.getMouseX() / SceneService.TILE_SIZE - World.INSTANCE.getTileOffsX(), InputHandler.INSTANCE.getMouseY() / SceneService.TILE_SIZE - World.INSTANCE.getTileOffsY());

        if (tile != null) {
            if (tile.hasCollision()) {
                ShapeDrawer.drawRectangle(ColorHelper.TILE_SELECT_CANNOT_MOVE, x, y, SceneService.TILE_SIZE, SceneService.TILE_SIZE);

                mouseText = tile.getName() + " - cannot move here";

                Cursors.setCursor(Cursors.CANNOT_MOVE_HERE_CURSOR);
            }
            else {
                ShapeDrawer.drawRectangle(ColorHelper.TILE_SELECT_CAN_MOVE, x, y, SceneService.TILE_SIZE, SceneService.TILE_SIZE);

                mouseText = tile.getName() + " - click to walk";

                Cursors.setCursor(Cursors.MOVE_HERE_CURSOR);
            }

            textOffsX = (World.INSTANCE.getWidth() - FontService.getStringWidth(mouseText)) / 2;
            ShapeDrawer.fillTransluscentRectangle(textOffsX - 5, 0, FontService.getStringWidth(mouseText) + 10, FontService.characterHeight + 5);
            FontService.drawFont(mouseText, Color.white, false, textOffsX, textOffsY);
        }
        else {
            Cursors.setCursor(Cursors.DEFAULT_CURSOR);
        }
    }
}
