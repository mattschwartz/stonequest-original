/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Cursors.java
 * Author:           Matt Schwartz
 * Date created:     08.28.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Game;
import static com.barelyconscious.game.Game.screen;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cursors {

    public static final Cursor DEFAULT_CURSOR = createCursor("/gfx/cursors/defaultCursor.png");
    public static final Cursor MOVE_CURSOR = createCursor("/gfx/cursors/moveCursor.png");
    public static final Cursor SALVAGE_ITEM_CURSOR = createCursor("/gfx/cursors/salvageItemCursor.png");
//    public static final Cursor HAND_CURSOR = createCursor("/gfx/cursors/handCursor.png");
    public static final Cursor ATTACK_ENTITY_CURSOR = createCursor("/gfx/cursors/attackEntityCursor.png");
    public static final Cursor LOOT_ITEM_CURSOR = createCursor("/gfx/cursors/lootItemCursor.png");
    public static final Cursor MOVE_HERE_CURSOR = createCursor("/gfx/cursors/moveHere.png");
    public static final Cursor CANNOT_MOVE_HERE_CURSOR = createCursor("/gfx/cursors/cannotMoveHere.png");

    public static Cursor createCursor(String pathToCursor) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage image;

        try {
            image = ImageIO.read(Game.class.getResourceAsStream(pathToCursor));
            Cursor c = toolkit.createCustomCursor(image, new Point(screen.getX(),
                    screen.getY()), "img");

            return c;
        } catch (IOException ex) {
            System.err.println("Error loading cursor: " + ex);
            return null;
        }
    } // createCursor

    public static void setCursor(Cursor newCursor) {
        if (screen.getCursor() == newCursor) {
            return;
        } // if
        
        screen.setCursor(newCursor);
    } // setCursor
} // Cursors
