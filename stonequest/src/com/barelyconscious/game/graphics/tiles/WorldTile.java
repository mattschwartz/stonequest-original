/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        WorldTile.java
 * Author:           Matt Schwartz
 * Date created:     09.05.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.tiles;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WorldTile {

    private static final String ENVIRONMENT_GROUND_PATH = "/gfx/tiles/world/environment/ground/";
    public static final WorldTile GRASS_TILE = new WorldTile(ENVIRONMENT_GROUND_PATH + "grass.png");
    public static final WorldTile GRASS_TILE_2 = new WorldTile(ENVIRONMENT_GROUND_PATH + "grass2.png");
    public static final WorldTile GRASS_TILE_3 = new WorldTile(ENVIRONMENT_GROUND_PATH + "grass3.png");
    private BufferedImage tileImage;
    private Graphics2D g;

    public WorldTile(String pathToImage) {
        try {
            tileImage = ImageIO.read(Game.class.getResourceAsStream(pathToImage));
        } catch (IOException ex) {
            System.err.println(" [ERROR] Failed to load resource \"" + pathToImage + "\": " + ex);
        }
    } // constructor

    public void render(Screen screen, int x, int y) {
        g = screen.getGraphics();
        g.drawImage(tileImage, x, y, null);
        g.dispose();
    } // render
} // WorldTile
