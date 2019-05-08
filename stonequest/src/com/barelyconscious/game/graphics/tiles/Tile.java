/* *****************************************************************************
   * File Name:         Tile.java
   * Author:            Matt Schwartz
   * Date Created:      01.04.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.graphics.tiles;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {
    private final int TRANSPARENT_COLOR = -65281; // rgb of 255,0,255
    
    // World tile ids
    public static final int GRASS_TILE_ID = 0;
    public static final int STONE_TILE_ID = 1;
    public static final int STONE_WALL_TILE_ID = 2;
    public static final int WATER_TILE_ID = 3;
    public static final int DOOR_IRON_CLOSED_TILE_ID = 4;
    public static final int DOOR_IRON_OPEN_TILE_ID = 5;
    public static final int DOOR_WOODEN_CLOSED_TILE_ID = 6;
    public static final int DOOR_WOODEN_OPEN_TILE_ID = 7;
    public static final int MOSSY_STONEWALL_TILE_ID = 8;
    
    // Entity ids
    public static final int PLAYER_TILE_ID = 9;
    public static final int SEWER_RAT_TILE_ID = 10;
    
    // Item ids
    public static final int GOLD_LOOT_SINGLE_TILE_ID = 11;
    public static final int GOLD_LOOT_STACK_TILE_ID = 12;
    
    public static final int ARMOR_CHEST_IRON_TILE_ID = 13;
    public static final int ARMOR_CHEST_LEATHER_TILE_ID = 14;
    public static final int ARMOR_GREAVES_IRON_TILE_ID = 15;
    public static final int ARMOR_GREAVES_LEATHER_TILE_ID = 16;
    public static final int ARMOR_BOOTS_IRON_TILE_ID = 17;
    public static final int ARMOR_BOOTS_LEATHER_TILE_ID = 18;
    public static final int ARMOR_HELMET_IRON_TILE_ID = 19;
    public static final int ARMOR_HELMET_LEATHER_TILE_ID = 20;
    public static final int ARMOR_BELT_IRON_TILE_ID = 21;
    public static final int ARMOR_BELT_LEATHER_TILE_ID = 22;
    public static final int ARMOR_EARRING_TILE_ID = 23;
    public static final int ARMOR_NECKLACE_TILE_ID = 24;
    public static final int ARMOR_RING_TILE_ID = 25;
    public static final int ARMOR_SHIELD_IRON_TILE_ID = 26;
    public static final int ARMOR_SHIELD_WOOD_TILE_ID = 27;
    
    public static final int SWORD_TILE_ID = 28;
    
    public static final int FOOD_TILE_ID = 29;
    public static final int POTION_TILE_ID = 30;
    public static final int ARROW_TILE_ID = 31;
    public static final int SCROLL_TILE_ID = 32;
    public static final int GLASS_BOTTLE_TILE_ID = 33;
    public static final int JUNK_TILE_ID = 34;
    
    // World tiles
    private static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile();
    public static Tile stoneTile = new StoneFloor();
    public static Tile stoneWallTile = new StoneWallTile();
    public static Tile waterTile = new WaterTile();
    public static Tile doorIronClosedTile = new DoorIronClosedTile();
    public static Tile doorIronOpenTile = new DoorIronOpenTile();
    public static Tile doorWoodenClosedTile = new DoorWoodenClosedTile();
    public static Tile doorWoodenOpenTile = new DoorWoodenOpenTile();
    public static Tile stoneWallMossyTile = new StoneWallMossyTile();
    
    // Entity tiles
    public static Tile playerTile = new PlayerTile();
    public static Tile sewerRatTile = new SewerRatTile();
    
    // Loot tiles
    public static Tile goldCoinSingleTile = new Tile(GOLD_LOOT_SINGLE_TILE_ID, "/tiles/loot/gold_coin_single.png");
    public static Tile goldCoinStackTile = new Tile(GOLD_LOOT_STACK_TILE_ID, "/tiles/loot/gold_coin_stack.png");
    
    public static Tile armorChestIronTile = new Tile(ARMOR_CHEST_IRON_TILE_ID, "/tiles/loot/armor/chest/iron.png");
    public static Tile armorChestLeatherTile = new Tile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/armor/chest/leather.png");
    public static Tile armorGreavesIronTile = new Tile(ARMOR_GREAVES_IRON_TILE_ID, "/tiles/loot/armor/greaves/iron.png");
    public static Tile armorGreavesLeatherTile = new Tile(ARMOR_GREAVES_LEATHER_TILE_ID, "/tiles/loot/armor/greaves/leather.png");
    public static Tile armorBootsIronTile = new Tile(ARMOR_BOOTS_IRON_TILE_ID, "/tiles/loot/armor/boots/iron.png");
    public static Tile armorBootsLeatherTile = new Tile(ARMOR_BOOTS_LEATHER_TILE_ID, "/tiles/loot/armor/boots/leather.png");
    public static Tile armorHelmetIronTile = new Tile(ARMOR_HELMET_IRON_TILE_ID, "/tiles/loot/armor/helmet/iron.png");
    public static Tile armorHelmetLeatherTile = new Tile(ARMOR_HELMET_LEATHER_TILE_ID, "/tiles/loot/armor/helmet/leather.png");
    public static Tile armorBeltIronTile = new Tile(ARMOR_BELT_IRON_TILE_ID, "/tiles/loot/armor/belt/iron.png");
    public static Tile armorBeltLeatherTile = new Tile(ARMOR_BELT_LEATHER_TILE_ID, "/tiles/loot/armor/belt/leather.png");
    public static Tile armorEarringTile = new Tile(ARMOR_EARRING_TILE_ID, "/tiles/loot/armor/jewelry/earring.png");
    public static Tile armorNecklaceTile = new Tile(ARMOR_NECKLACE_TILE_ID, "/tiles/loot/armor/jewelry/necklace.png");
    public static Tile armorRingTile = new Tile(ARMOR_RING_TILE_ID, "/tiles/loot/armor/jewelry/ring.png");
    public static Tile armorShieldIronTile = new Tile(ARMOR_SHIELD_IRON_TILE_ID, "/tiles/loot/armor/shield/iron.png");
    public static Tile armorShieldWoodTile = new Tile(ARMOR_SHIELD_WOOD_TILE_ID, "/tiles/loot/armor/shield/wood.png");
    
    public static Tile swordTileId = new Tile(SWORD_TILE_ID, "/tiles/loot/weapons/sword.png");
    
    public static Tile foodTileId = new Tile(FOOD_TILE_ID, "/tiles/loot/food.png");
    public static Tile potionTileId = new Tile(POTION_TILE_ID, "/tiles/loot/potion.png");
    public static Tile scrollTileId = new Tile(SCROLL_TILE_ID, "/tiles/loot/scroll.png");
    public static Tile arrowTileId = new Tile(ARROW_TILE_ID, "/tiles/loot/projectile.png");
    public static Tile glassBottleTile = new Tile(GLASS_BOTTLE_TILE_ID, "/tiles/loot/glass_bottle.png");
    
    private boolean hasCollision;
    private boolean isVisible;
    private boolean blocksSight;
    
    protected final byte id;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected int[] pixels;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Tile(int id, String fileName, boolean hasCollision, boolean isVisible, boolean blocksSight) {
        BufferedImage img = null;
        this.hasCollision = hasCollision;
        this.isVisible = isVisible;
        this.blocksSight = blocksSight;

        this.id = (byte) id;

        try {
            img = ImageIO.read(Game.class.getResourceAsStream(fileName));
        } catch (IOException ex) {
            System.err.println(" [ERR] Failed to load image (" + fileName + "): " + ex);
            WIDTH = 0;
            HEIGHT = 0;

            tiles[id] = null;

            return;
        } // catch
        
        WIDTH = img.getWidth();
        HEIGHT = img.getHeight();
        pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());

        tiles[id] = this;
    } // constructor
    
    // Loot tile
    @SuppressWarnings("LeakingThisInConstructor")
    public Tile(int id, String fileName) {
        this(id, fileName, false, false, false);
    } // loot constructor

    /** 
     * Draw the tile starting at xPos, yPos, skipping mask Color(255,0,255)
     * @param xStart
     * @param yStart 
     */
    public void render(Screen scr, int xStart, int yStart) {
        int pix;
        
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                pix = pixels[x + y * WIDTH];
                
                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } // if
                
                scr.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // render

    public boolean hasCollision() {
        return hasCollision;
    } // hasCollision

    public void setCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    } // setCollision

    public boolean isVisible() {
        return isVisible;
    } // isVisible

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    } // setVisible

    public boolean blocksLineOfSight() {
        return blocksSight;
    } // blocksLineOfSight

    public void setBlocksLineOfSight(boolean blocksLineOfSight) {
        this.blocksSight = blocksLineOfSight;
    } // setBlocksLineOfSight
    
    public static Tile getTile(int tileId) {
        if (tileId > 256) {
            System.err.println(" [ERR] Requested tile id (" + tileId + ") greater than 256!");
            return null;
        } // if
        
        return tiles[tileId];
    } // getTile
} // Tile