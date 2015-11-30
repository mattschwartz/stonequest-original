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

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.services.InputHandler;
import com.barelyconscious.game.services.SceneService;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class Cursors {

    public static final Cursor DEFAULT_CURSOR = createCursor("/gfx/cursors/defaultCursor.png");
    public static final Cursor MOVE_CURSOR = createCursor("/gfx/cursors/moveCursor.png");
    public static final Cursor SALVAGE_ITEM_CURSOR = createCursor("/gfx/cursors/salvageItemCursor.png");
    public static final Cursor ATTACK_ENTITY_CURSOR = createCursor("/gfx/cursors/attackEntityCursor.png");
    public static final Cursor LOOT_ITEM_CURSOR = createCursor("/gfx/cursors/lootItemCursor.png");
    public static final Cursor MOVE_HERE_CURSOR = createCursor("/gfx/cursors/moveHere.png");
    public static final Cursor CANNOT_MOVE_HERE_CURSOR = createCursor("/gfx/cursors/cannotMoveHere.png");

    public static Cursor createCursor(String pathToCursor) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage image;

        image = UIElement.loadImage(pathToCursor);
        if (image == null) {
            // error
            return null;
        } // if

        Cursor c = toolkit.createCustomCursor(image, new Point(0,0), "img");

        return c;
    } // createCursor

    public static void setCursor(Cursor newCursor) {
        if (SceneService.INSTANCE.getCursor() == newCursor) {
            return;
        } // if

        SceneService.INSTANCE.setCursor(newCursor);
    } // setCursor
} // Cursors
