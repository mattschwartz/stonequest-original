/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         MiniMap.java
 * Author:            Matt Schwartz
 * Date Created:      03.14.2013 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or 
 *                    concerns.
 * File Description:  Draws the current World in a smaller frame so that the
 *                    user can get a broad overview of the area.  Draws the GameMap
 *                    tiles as well as all Entities, Loot and Doodads uncovered
 *                    by the user.
 ************************************************************************** */
package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.GameMap;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.spawnable.Container;
import com.barelyconscious.game.spawnable.Doodad;
import com.barelyconscious.game.spawnable.EntityList;
import com.barelyconscious.game.spawnable.LootList;
import java.util.ArrayList;

public class MiniMap extends Interactable {

    private final int ZONE_LEVEL_OFFS_X = 291;
    private final int ZONE_LEVEL_OFFS_Y = 35;
    private final int ZONE_LEVEL_WIDTH = 21;
    private final int ZONE_LEVEL_HEIGHT = 18;
    private final int ZONE_LEVEL_IDENTIFIER_OFFS_X = 234;
    private final int ZONE_LEVEL_IDENTIFIER_OFFS_Y = 35;
    private final int REMAINING_ELITES_OFFS_X = ZONE_LEVEL_OFFS_X;
    private final int REMAINING_ELITES_OFFS_Y = 55;
    private final int REMAINING_ELITES_WIDTH = ZONE_LEVEL_WIDTH;
    private final int REMAINING_ELITES_HEIGHT = ZONE_LEVEL_HEIGHT;
    private final int MINIMAP_VIEW_OFFS_X = 3;
    private final int MINIMAP_VIEW_OFFS_Y = 9;
    private final int MINIMAP_VIEW_WIDTH = 315;
    private final int MINIMAP_VIEW_HEIGHT = 204;
    private final int MINIMAP_FRAME_WIDTH = 321;
    private final int MINIMAP_FRAME_HEIGHT = 217;
    private int xOffs;
    private int yOffs;
    private final int ZONE_NAMEBAR_WIDTH = 299;
    private final int ZONE_NAMEBAR_HEIGHT = 18;
    private final int ZONE_NAME_OFFS_X = 11;
    private final int ZONE_NAME_OFFS_Y = 0;
    private int tileScale = 3;
    private int mapStartX;
    private int mapStartY;
    
    private boolean showZoneLevelIdentifier = false;
    private boolean showRemainingElitesIdentifier = false;

    public void resizeMenu(int width, int height) {
        xOffs = width - MINIMAP_FRAME_WIDTH;
        yOffs = 1;
        
        Game.mouseHandler.registerHoverableListener(this);
        defineMouseZone(xOffs, yOffs, MINIMAP_FRAME_WIDTH, MINIMAP_FRAME_HEIGHT);
    } // resizeMenu

    public int getPixelWidth() {
        return MINIMAP_FRAME_WIDTH;
    } // getPixelWidth

    public int getPixelHeight() {
        return MINIMAP_FRAME_HEIGHT;
    } // getPixelHeight

    public int getOffsX() {
        return xOffs;
    } // getOffsX

    public int getOffsY() {
        return yOffs;
    } // getOffsY

    @Override
    public void mouseMoved(int x, int y) {
        x -= xOffs;
        y -= yOffs;
        showRemainingElitesIdentifier = false;
        showZoneLevelIdentifier = false;
        
        if (x >= ZONE_LEVEL_OFFS_X && x <= ZONE_LEVEL_OFFS_X + ZONE_LEVEL_WIDTH 
                && y >= ZONE_LEVEL_OFFS_Y && y <= ZONE_LEVEL_OFFS_Y + ZONE_LEVEL_HEIGHT) {
            showZoneLevelIdentifier = true;
        } // if
        
        if (x >= REMAINING_ELITES_OFFS_X && x <= REMAINING_ELITES_OFFS_X + REMAINING_ELITES_WIDTH 
                && y >= REMAINING_ELITES_OFFS_Y && y <= REMAINING_ELITES_OFFS_Y + REMAINING_ELITES_HEIGHT) {
            showRemainingElitesIdentifier = true;
        } // if
    } // mouseMoved

    public void render(Screen screen) {
        int xPos = Game.world.getPlayerX() / Common.TILE_SIZE + Game.gameMap.getXStart();
        int yPos = Game.world.getPlayerY() / Common.TILE_SIZE + Game.gameMap.getYStart();

        mapStartX = Game.gameMap.getXStart() - MINIMAP_VIEW_WIDTH / 6 + Game.screen.getScreenWidth() / 40;//Game.gameMap.getXStart() - (pixelWidth / 2) / tileScale + (Game.screen.getScreenWidth() / Common.TILE_SIZE) / 2 - Game.player.getLightRadius() - tileScale;
        mapStartY = Game.gameMap.getYStart() - ((MINIMAP_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2) / tileScale + (Game.screen.getScreenHeight() / Common.TILE_SIZE) / 2 - Game.player.getLightRadius() / 2;

        /* Draw the world tiles */
        renderTiles(screen);

        /* Draw Loot objects to the gameMap */
        renderLoot(screen);

        /* Draw Doodad objects to the gameMap */
        renderDoodads(screen);

        /* Draw Entities to the gameMap */
        renderEntities(screen);

        xPos -= mapStartX;
        yPos -= mapStartY;

        xPos *= tileScale;
        yPos *= tileScale;

        // fix this later...
        xPos += 12;
        yPos -= 4;

        /* Draw the title of the Zone as the title for the mini gameMap */
        UIElement.MINIMAP_FRAME.render(screen, xOffs, yOffs);
        screen.fillRectangle(Common.MINIMAP_PLAYER_TILE_RGB, xOffs + xPos,
                yOffs + yPos + Font.CHAR_HEIGHT, tileScale, tileScale);
        
        renderIdentifierTabs(screen);
        renderText(screen);
    } // render

    private void renderText(Screen screen) {
        String zoneName = Game.gameMap.getZoneName();
        String zoneLevel = "" + Game.gameMap.getZoneLevel();
        String remainingElites = "2" + Game.gameMap.getRemainingElites();

        // Render the name of the zone to the minimap
        Font.drawOutlinedMessage(screen, zoneName, Common.FONT_WHITE_RGB, true, xOffs + ZONE_NAME_OFFS_X
            + (ZONE_NAMEBAR_WIDTH - zoneName.length() * Font.CHAR_WIDTH) / 2, yOffs + ZONE_NAME_OFFS_Y
            + (ZONE_NAMEBAR_HEIGHT - Font.CHAR_HEIGHT) / 2);

        // Render the level of the zone to the minimap
        Font.drawOutlinedMessage(screen, zoneLevel, Common.themeForegroundColor, false, xOffs + ZONE_LEVEL_OFFS_X
                + (ZONE_LEVEL_WIDTH - zoneLevel.length() * Font.CHAR_WIDTH) / 2, yOffs + ZONE_LEVEL_OFFS_Y
                + (ZONE_LEVEL_HEIGHT - Font.CHAR_HEIGHT) / 2);

        // Render the number of remaining elite monsters to the minimap
        Font.drawMessage(screen, remainingElites, Common.themeForegroundColor, false, xOffs + REMAINING_ELITES_OFFS_X
                + (REMAINING_ELITES_WIDTH - remainingElites.length() * Font.CHAR_WIDTH) / 2, yOffs
                + REMAINING_ELITES_OFFS_Y + (REMAINING_ELITES_HEIGHT - Font.CHAR_HEIGHT) / 2);
    } // renderText

    private void renderTiles(Screen screen) {
        int tileId;
        int tileRGB;
        int miniMapWidth = MINIMAP_VIEW_WIDTH / tileScale;
        int miniMapHeight = MINIMAP_VIEW_HEIGHT / tileScale;

        int[][] pixels = new int[miniMapWidth][miniMapHeight];

        for (int x = 0, mapX = mapStartX; x < miniMapWidth; x++, mapX++) {
            for (int y = 0, mapY = mapStartY; y < miniMapHeight; y++, mapY++) {
                tileId = Game.gameMap.getTileIdAt(mapX, mapY);

                if ((tileId & GameMap.IS_VISIBLE) >> 8 == 1) {
                    tileRGB = Tile.getTile((byte) tileId).getMiniMapColor();
                } // if
                else if ((tileId & GameMap.RECENTLY_SEEN) >> 9 == 1) {
                    tileRGB = Tile.getTile((byte) tileId).getMiniMapColorShaded();
                } // else if
                else {
                    continue;
                } // else

                pixels[x][y] = tileRGB;
            } // for
        } // for

        screen.scale(xOffs + MINIMAP_VIEW_OFFS_X, yOffs + MINIMAP_VIEW_OFFS_Y, pixels, tileScale);
    } // renderTiles
    
    private void renderIdentifierTabs(Screen screen) {
        if (showZoneLevelIdentifier) {
            UIElement.MINIMAP_ZONE_LEVEL_IDENTIFIER_TAB.render(screen, xOffs, yOffs);
        } // if
        
        if (showRemainingElitesIdentifier) {
            UIElement.MINIMAP_REMAINING_ELITES_IDENTIFIER_TAB.render(screen, xOffs, yOffs);
        }
    } // renderIdentifierTabs

    /**
     * Draw all the Entities to the MiniMap if they are visible.
     *
     * @param screen
     */
    private void renderEntities(Screen screen) {
        int xPos;
        int yPos;
        EntityList entityList = Game.world.getEntities();

        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).isVisible()) {
                xPos = entityList.get(i).getXPos();
                yPos = entityList.get(i).getYPos();
            } // if
            else if (entityList.get(i).wasRecentlySeen()) {
                xPos = entityList.get(i).getLastKnownXPos();
                yPos = entityList.get(i).getLastKnownYPos();
            } // else if
            else {
                continue;
            } // else

            xPos = xPos / Common.TILE_SIZE + Game.gameMap.getXStart();
            yPos = yPos / Common.TILE_SIZE + Game.gameMap.getYStart();

            xPos -= mapStartX;
            yPos -= mapStartY;

            xPos *= tileScale;
            yPos *= tileScale;

            // fix this later...
            xPos += 12;
            yPos -= 4;

            if (xPos < 0 || yPos < 0 || xPos >= screen.getScreenWidth()
                    || yPos >= screen.getScreenHeight()) {
                continue;
            } // if

            screen.fillRectangle(Common.MINIMAP_ENTITY_TILE_RGB, xOffs + xPos,
                    yOffs + Font.CHAR_HEIGHT + yPos, tileScale, tileScale);
        } // for
    } // renderEntities

    /**
     * Draw all the Loot to the MiniMap if they are visible.
     *
     * @param screen
     */
    private void renderLoot(Screen screen) {
        int xPos;
        int yPos;
        LootList lootList = Game.world.getLootItems();

        for (int i = 0; i < lootList.size(); i++) {
            if (lootList.get(i).isVisible()) {
                xPos = lootList.get(i).getXPos();
                yPos = lootList.get(i).getYPos();
            } // if
            else {
                continue;
            } // else

            xPos = xPos / Common.TILE_SIZE + Game.gameMap.getXStart();
            yPos = yPos / Common.TILE_SIZE + Game.gameMap.getYStart();

            xPos -= mapStartX;
            yPos -= mapStartY;

            xPos *= tileScale;
            yPos *= tileScale;

            // fix this later...
            xPos += 12;
            yPos -= 4;

            /* If the object is out of range, don't draw it to the gameMap*/
            if (xPos < 0 || yPos < 0 || xPos >= screen.getScreenWidth()
                    || yPos >= screen.getScreenHeight()) {
                continue;
            } // if

            screen.fillRectangle(Common.MINIMAP_LOOT_TILE_RGB, xOffs + xPos,
                    yOffs + Font.CHAR_HEIGHT + yPos, tileScale, tileScale);
        } // for
    } // renderLoot

    /**
     * Draw all the Doodads to the MiniMap if they are visible.
     *
     * @param screen
     */
    private void renderDoodads(Screen screen) {
        int xPos;
        int yPos;
        ArrayList<Doodad> doodadList = Game.world.getDoodads();

        for (Doodad doodad : doodadList) {
            if (doodad.isVisible()) {
                xPos = doodad.getXPos();
                yPos = doodad.getYPos();
            } // if
            else {
                continue;
            } // else

            xPos = xPos / Common.TILE_SIZE + Game.gameMap.getXStart();
            yPos = yPos / Common.TILE_SIZE + Game.gameMap.getYStart();

            xPos -= mapStartX;
            yPos -= mapStartY;

            xPos *= tileScale;
            yPos *= tileScale;

            // fix this later...
            xPos += 12;
            yPos -= 4;

            /* If the object is out of range, don't draw it to the gameMap*/
            if (xPos < 0 || yPos < 0 || xPos >= screen.getScreenWidth()
                    || yPos >= screen.getScreenHeight()) {
                continue;
            } // if

            if (doodad instanceof Container) {
                screen.fillRectangle(Common.MINIMAP_CONTAINER_TILE_RGB, xOffs + xPos,
                        yOffs + Font.CHAR_HEIGHT + yPos, tileScale, tileScale);
            } // if
            else {
                screen.fillRectangle(Common.MINIMAP_NEXUS_TILE_RGB, xOffs + xPos,
                        yOffs + Font.CHAR_HEIGHT + yPos, tileScale, tileScale);
            } // else
        } // for
    } // renderDoodads
} // MiniMap
