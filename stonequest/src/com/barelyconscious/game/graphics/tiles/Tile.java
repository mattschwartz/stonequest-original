/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         Tile.java
 * Author:            Matt Schwartz
 * Date Created:      01.04.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  Contains static variables containing tile data for each
 *                    tile that will be drawn to the screen.  Such as: rendering,
 *                    a flattened 2D array of the tile's pixels, and game-
 *                    mechanic variables like whether the tile blocks line of
 *                    sight.  Each tile has:
 *      -       byte  id: small datum that corresponds a tile to its
 *                     class so that it can be minimally stored until it needs
 *                     to be drawn to the screen using render().
 *      -        int  WIDTH: the width of the image
 *      -        int  HEIGHT: the height of the image
 *      -      int[]  pixels: data which stores the pixels of the tile
 *      -    boolean  hasCollision: if this value is true, entities cannot, in
 *                     most cases pass through the tile within the world.
 *      -    boolean  isVisible: always initially false.  only becomes true as
 *                     the tile comes into view of the player
 *      -    boolean  blocksSight: if this value is true, player line of sight
 *                     is occluded by this tile
 ************************************************************************** */
package com.barelyconscious.game.graphics.tiles;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {

    private final int TRANSPARENT_COLOR = -65281; // precomputed rgb of 255,0,255
    private final int TRANSLUSCENT_COLOR = new Color(125, 0, 125).getRGB();
    // World tile ids - part of the world map
    public static final int GRASS_TILE_ID = 0;
    public static final int STONE_TILE_ID = 1;
    public static final int STONEWALL_TILE_ID = 2;
    public static final int MOSSY_STONEWALL_TILE_ID = 3;
    public static final int WATER_TILE_ID = 4;
    public static final int DOOR_IRON_CLOSED_TILE_ID = 5;
    public static final int DOOR_IRON_OPEN_TILE_ID = 6;
    public static final int DOOR_WOODEN_CLOSED_TILE_ID = 7;
    public static final int DOOR_WOODEN_OPEN_TILE_ID = 8;
    public static final int CONTAINER_CHEST_CLOSED_TILE_ID = 9;
    public static final int CONTAINER_CHEST_OPEN_TILE_ID = 10;
    // Entity ids - tiles for all enemies and the player
    public static final int PLAYER_TILE_ID = 11;
    public static final int SEWER_RAT_TILE_ID = 12;
    // Item ids
    public static final int GOLD_LOOT_SINGLE_TILE_ID = 13;
    public static final int GOLD_LOOT_STACK_TILE_ID = 14;
    public static final int CHEST_IRON_TILE_ID = 15;
    public static final int CHEST_LEATHER_TILE_ID = 16;
    public static final int GREAVES_IRON_TILE_ID = 17;
    public static final int GREAVES_LEATHER_TILE_ID = 18;
    public static final int BOOTS_IRON_TILE_ID = 19;
    public static final int BOOTS_LEATHER_TILE_ID = 20;
    public static final int HELMET_IRON_TILE_ID = 21;
    public static final int HELMET_LEATHER_TILE_ID = 22;
    public static final int BELT_IRON_TILE_ID = 23;
    public static final int BELT_LEATHER_TILE_ID = 24;
    public static final int EARRING_TILE_ID = 25;
    public static final int NECKLACE_TILE_ID = 26;
    public static final int RING_TILE_ID = 27;
    public static final int OFFHAND_SHIELD_IRON_TILE_ID = 28;
    public static final int OFFHAND_SHIELD_WOOD_TILE_ID = 29;
    public static final int MAINHAND_SWORD_TILE_ID = 30;
    public static final int FOOD_TILE_ID = 31;
    public static final int POTION_TILE_ID = 32;
    public static final int ARROW_TILE_ID = 33;
    public static final int SCROLL_TILE_ID = 34;
    public static final int GLASS_BOTTLE_TILE_ID = 35;
    public static final int KEY_TILE_ID = 36;
    public static final int JUNK_TILE_ID = 37;
    public static final int GRASS_TILE_2_ID = 38;
    public static final int FOG_TILE_ID = 39;
    public static final int GRASS_TILE_3_ID = 40;
    public static final int ANTIMAGIC_POTION_TILE_ID = 41;

    public static final int HELMET_CLOTH_TILE_ID = 42;
    public static final int CHEST_CLOTH_TILE_ID = 43;

    // World tiles
    private static Tile[] tiles = new Tile[256]; // holds the classes for all tiles
    public static Tile grassTile = new GrassTile();
    public static Tile grassTile2 = new Tile("grass", GRASS_TILE_2_ID, "/tiles/world/grass_2.png", false, false, false);
    public static Tile grassTile3 = new Tile("grass", GRASS_TILE_3_ID, "/tiles/world/grass_3.png", false, false, false);
    public static Tile fogTile = new Tile("the unseeeeeeeeeN", FOG_TILE_ID, "/tiles/world/fog.png", false, false, false);
    public static Tile stoneTile = new StoneFloor();
    public static Tile stoneWallTile = new StoneWallTile();
    public static Tile stoneWallMossyTile = new StoneWallMossyTile();
    public static Tile waterTile = new WaterTile();
    public static Tile doorIronClosedTile = new DoorIronClosedTile();
    public static Tile doorIronOpenTile = new DoorIronOpenTile();
    public static Tile doorWoodenClosedTile = new DoorWoodenClosedTile();
    public static Tile doorWoodenOpenTile = new DoorWoodenOpenTile();
    public static Tile containerClosedTile = new ContainerClosedTile();
    public static Tile containerOpenTile = new ContainerOpenTile();
    // Entity tiles
    public static Tile playerTile = new PlayerTile();
    public static Tile sewerRatTile = new SewerRatTile();
    // Loot tiles
    public static Tile goldCoinSingleTile = new Tile(GOLD_LOOT_SINGLE_TILE_ID, "/tiles/loot/gold_coin_single.png");
    public static Tile goldCoinStackTile = new Tile(GOLD_LOOT_STACK_TILE_ID, "/tiles/loot/gold_coin_stack.png");
    public static Tile armorChestIronTile = new Tile(CHEST_IRON_TILE_ID, "/tiles/loot/armor/chest/iron.png");
    public static Tile armorChestLeatherTile = new Tile(CHEST_LEATHER_TILE_ID, "/tiles/loot/armor/chest/leather.png");
    public static Tile armorChestClothTile = new Tile(CHEST_CLOTH_TILE_ID, "/tiles/loot/armor/chest/clothRobe.png");
    public static Tile armorGreavesIronTile = new Tile(GREAVES_IRON_TILE_ID, "/tiles/loot/armor/greaves/iron.png");
    public static Tile armorGreavesLeatherTile = new Tile(GREAVES_LEATHER_TILE_ID, "/tiles/loot/armor/greaves/leather.png");
    public static Tile armorBootsIronTile = new Tile(BOOTS_IRON_TILE_ID, "/tiles/loot/armor/boots/iron.png");
    public static Tile armorBootsLeatherTile = new Tile(BOOTS_LEATHER_TILE_ID, "/tiles/loot/armor/boots/leather.png");
    public static Tile armorHelmetIronTile = new Tile(HELMET_IRON_TILE_ID, "/tiles/loot/armor/helmet/iron.png");
    public static Tile armorHelmetLeatherTile = new Tile(HELMET_LEATHER_TILE_ID, "/tiles/loot/armor/helmet/leather.png");
    public static Tile armorHelmetClothTile = new Tile(HELMET_CLOTH_TILE_ID, "/tiles/loot/armor/helmet/clothHat.png");
    public static Tile armorBeltIronTile = new Tile(BELT_IRON_TILE_ID, "/tiles/loot/armor/belt/iron.png");
    public static Tile armorBeltLeatherTile = new Tile(BELT_LEATHER_TILE_ID, "/tiles/loot/armor/belt/leather.png");
    public static Tile armorEarringTile = new Tile(EARRING_TILE_ID, "/tiles/loot/armor/jewelry/earring.png");
    public static Tile armorNecklaceTile = new Tile(NECKLACE_TILE_ID, "/tiles/loot/armor/jewelry/necklace.png");
    public static Tile armorRingTile = new Tile(RING_TILE_ID, "/tiles/loot/armor/jewelry/ring.png");
    public static Tile armorShieldIronTile = new Tile(OFFHAND_SHIELD_IRON_TILE_ID, "/tiles/loot/armor/shield/iron.png");
    public static Tile armorShieldWoodTile = new Tile(OFFHAND_SHIELD_WOOD_TILE_ID, "/tiles/loot/armor/shield/wood.png");
    public static Tile swordTileId = new Tile(MAINHAND_SWORD_TILE_ID, "/tiles/loot/weapons/sword.png");
    public static Tile foodTile = new Tile(FOOD_TILE_ID, "/tiles/loot/food.png");
    public static Tile potionTile = new Tile(POTION_TILE_ID, "/tiles/loot/potion.png");
    public static Tile antimagicPotionTile = new Tile(ANTIMAGIC_POTION_TILE_ID, "/tiles/loot/antimagic_potion.png");
    public static Tile scrollTile = new Tile(SCROLL_TILE_ID, "/tiles/loot/scroll.png");
    public static Tile arrowTile = new Tile(ARROW_TILE_ID, "/tiles/loot/projectile.png");
    public static Tile glassBottleTile = new Tile(GLASS_BOTTLE_TILE_ID, "/tiles/loot/glass_bottle.png");
    public static Tile keyTile = new Tile(KEY_TILE_ID, "/tiles/loot/key.png");
    protected final byte id;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected int miniMapColor;
    protected int[] pixels;
    protected String tileName;
    private boolean hasCollision;
    private boolean blocksSight;

    /**
     * Loads the Tile data into the Tile array, tiles[] and keeps track of the class variables.
     *
     * @param id the corresponding id for the tile as defined in Tile.java
     * @param fileName where the resource can be found, /res/tiles by default
     * @param hasCollision true if entities cannot pass through this tile
     * @param isVisible true if the player can see the tile on the screen
     * @param blocksSight true if the tile blocks line of sight for the player
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Tile(int id, String fileName, boolean hasCollision, boolean isVisible, boolean blocksSight) {
        BufferedImage img = null;
        this.hasCollision = hasCollision;
        this.blocksSight = blocksSight;

        if (hasCollision) {
            tileName = "wall";
        } else {
            tileName = "floor";
        }

        try {
            img = ImageIO.read(Game.class.getResourceAsStream(fileName));
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println(" [ERR] Failed to load image (" + fileName + "): " + ex);
        } // catch

        WIDTH = img.getWidth();
        HEIGHT = img.getHeight();
        pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        this.id = (byte) id;

        tiles[this.id] = this;
    } // constructor

    @SuppressWarnings("LeakingThisInConstructor")
    public Tile(String tileName, int id, String fileName, boolean hasCollision, boolean isVisible, boolean blocksSight) {
        BufferedImage img = null;
        this.hasCollision = hasCollision;
        this.blocksSight = blocksSight;
        this.tileName = tileName;

        try {
            img = ImageIO.read(Game.class.getResourceAsStream(fileName));
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println(" [ERR] Failed to load image (" + fileName + "): " + ex);
        } // catch

        WIDTH = img.getWidth();
        HEIGHT = img.getHeight();
        pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        this.id = (byte) id;

        tiles[this.id] = this;
        miniMapColor = new Color(120, 120, 120).getRGB();
    } // constructor

    /**
     * Loot tile constructor. All loot tiles have the same boolean values
     *
     * @param id
     * @param fileName
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Tile(int id, String fileName) {
        this("Loot", id, fileName, false, false, false);
    } // loot constructor

    /**
     * Draw the tile starting at xPos, yPos, skipping mask Color(255,0,255)
     *
     * @param xStart
     * @param yStart
     */
    public void render(Screen scr, int xStart, int yStart) {
        int pix;
        int mask = 0xff;
        int r, g, b;

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                pix = pixels[x + y * WIDTH];

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } // if

                if (pix == TRANSLUSCENT_COLOR) {
                    pix = scr.getPixel(x + xStart, y + yStart);
                    r = (pix >> 16) & mask;
                    g = (pix >> 8) & mask;
                    b = pix & mask;

                    r = (int) (r * 1.05);
                    g = (int) (g * 1.05);
                    b = (int) (b * 1.15);

                    pix = (r << 16) + (g << 8) + b;

                    scr.setPixel(pix, x + xStart, y + yStart);
                    continue;
                } // if

                scr.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // render

    /**
     * Draw the tile starting at xPos, yPos, skipping mask Color(255,0,255)
     *
     * @param xStart
     * @param yStart
     */
    public void renderScaled(Screen scr, int xStart, int yStart, int scale) {
        int pix;
        int mask = 0xff;
        int r, g, b;
        int raster[][] = new int[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                pix = pixels[x + y * WIDTH];

//                if (pix == TRANSPARENT_COLOR) {
//                    continue;
//                } // if

                if (pix == TRANSLUSCENT_COLOR) {
                    pix = scr.getPixel(x + xStart, y + yStart);
                    r = (pix >> 16) & mask;
                    g = (pix >> 8) & mask;
                    b = pix & mask;

                    r = (int) (r * 1.05);
                    g = (int) (g * 1.05);
                    b = (int) (b * 1.15);

                    pix = (r << 16) + (g << 8) + b;
                } // if

                raster[x][y] = pix;
            } // for
        } // for

        scr.scale(xStart, yStart, raster, scale);
    } // render

    /**
     * Draws the tile darker than normal so that it appears in shadow to the other tiles
     *
     * @param scr the screen to draw the tile to
     * @param xStart defines the left-most side of the tile
     * @param yStart defines the upper-most side of the tile
     */
    public void renderShadedTile(Screen scr, int xStart, int yStart) {
        int pix;
        int mask = 0xff;
        int r, g, b;

        // Iterate over the tile row by column
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                pix = pixels[x + y * WIDTH];

                // Skips the transparent color (255,0,255)
                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } // if

                if (pix == TRANSLUSCENT_COLOR) {
                    pix = scr.getPixel(x + xStart, y + yStart);
                    r = (pix >> 16) & mask;
                    g = (pix >> 8) & mask;
                    b = pix & mask;

                    r = (int) (r * 1.05);
                    g = (int) (g * 1.05);
                    b = (int) (b * 1.15);

                    pix = (r << 16) + (g << 8) + b;

                    scr.setPixel(pix, x + xStart, y + yStart);
                    continue;
                } // if

                // Darkens the tile by 25%
                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                r = (int) (r * 0.75);
                g = (int) (g * 0.75);
                b = (int) (b * 0.75);

                pix = (r << 16) + (g << 8) + b;

                scr.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // renderShadedTile

    /**
     * Used to draw the mini map by getting a tile's average RGB color value
     *
     * @param tileid the Tile id for which the average RGB color value is desired
     * @return the average RGB color value
     */
    public int getMiniMapColor() {
        return miniMapColor;
    } // getMiniMapColor

    /**
     * Used to draw the mini map by getting a shaded tile's average RGB color value
     *
     * @param tileid the Tile id for which the average RGB color value is desired
     * @return the average RGB color value
     */
    public int getMiniMapColorShaded() {
        int r, g, b;
        int pix;
        int mask = 0xff;

        pix = miniMapColor;

        r = (pix >> 16) & mask;
        g = (pix >> 8) & mask;
        b = pix & mask;

        r = (int) (r * 0.75);
        g = (int) (g * 0.75);
        b = (int) (b * 0.75);

        pix = (r << 16) + (g << 8) + b;

        return pix;
    } // getMiniMapColor

    /**
     *
     * @return true if the tile blocks entity movement
     */
    public boolean hasCollision() {
        return hasCollision;
    } // hasCollision

    /**
     * Sets the collision variable for the tile to hasCollision.
     *
     * @param hasCollision the new value for the hasCollision variable
     */
    public void setCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    } // setCollision

    /**
     * Takes a tileId and returns true if that tile blocks entity line of sight.
     *
     * @param tileId the tile to get the value for
     * @return true if the tile blocks entity line of sight
     */
    public static boolean doesBlockSight(int tileId) {
        return tiles[tileId].blocksSight;
    } // doesBlockSight

    /**
     * Takes a tileId and returns the tile's class so that its functions may be called.
     *
     * @param tileId
     * @return the tile associated with the tileId
     */
    public static Tile getTile(int tileId) {
        if (tileId > 256) {
            System.err.println(" [ERR] Requested tile id (" + tileId + ") greater than 256!");
            return null;
        } // if

        return tiles[tileId];
    } // getTile

    @Override
    public String toString() {
        return tileName;
    } // toString
} // Tile