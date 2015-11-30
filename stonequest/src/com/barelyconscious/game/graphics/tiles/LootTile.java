package com.barelyconscious.game.graphics.tiles;

public class LootTile extends Tile {
    // Tile IDs
//    public static final int GOLD_LOOT_SINGLE_TILE_ID = 11;
//    public static final int GOLD_LOOT_STACK_TILE_ID = 12;
//    
//    public static final int ARMOR_CHEST_IRON_TILE_ID = 13;
//    public static final int ARMOR_CHEST_LEATHER_TILE_ID = 14;
//    public static final int ARMOR_GREAVES_IRON_TILE_ID = 15;
//    public static final int ARMOR_GREAVES_LEATHER_TILE_ID = 16;
//    public static final int ARMOR_BOOTS_IRON_TILE_ID = 17;
//    public static final int ARMOR_BOOTS_LEATHER_TILE_ID = 18;
//    public static final int ARMOR_HELMET_IRON_TILE_ID = 19;
//    public static final int ARMOR_HELMET_LEATHER_TILE_ID = 20;
//    public static final int ARMOR_BELT_IRON_TILE_ID = 21;
//    public static final int ARMOR_BELT_LEATHER_TILE_ID = 22;
//    public static final int ARMOR_EARRING_TILE_ID = 23;
//    public static final int ARMOR_NECKLACE_TILE_ID = 24;
//    public static final int ARMOR_RING_TILE_ID = 25;
//    public static final int ARMOR_SHIELD_IRON_TILE_ID = 26;
//    public static final int ARMOR_SHIELD_WOOD_TILE_ID = 27;
//    
//    public static final int SWORD_TILE_ID = 28;
//    
//    public static final int FOOD_TILE_ID = 29;
//    public static final int POTION_TILE_ID = 30;
//    public static final int ARROW_TILE_ID = 31;
//    public static final int SCROLL_TILE_ID = 32;
//    public static final int GLASS_BOTTLE_TILE_ID = 33;
//    public static final int JUNK_TILE_ID = 34;
    
    // Tiles
//    public static Tile goldCoinSingleTile = new LootTile(GOLD_LOOT_SINGLE_TILE_ID, "/tiles/loot/gold_coin_single.png");
//    public static Tile goldCoinStackTile = new LootTile(GOLD_LOOT_STACK_TILE_ID, "/tiles/loot/gold_coin_stack.png");
//    
//    public static Tile armorChestIronTile = new LootTile(ARMOR_CHEST_IRON_TILE_ID, "/tiles/loot/chest/iron.png");
//    public static Tile armorChestLeatherTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/chest/leather.png");
//    public static Tile armorGreavesIronTile = new LootTile(ARMOR_GREAVES_IRON_TILE_ID, "/tiles/loot/greaves/iron.png");
//    public static Tile armorGreavesLeatherTile = new LootTile(ARMOR_GREAVES_LEATHER_TILE_ID, "/tiles/loot/greaves/leather.png");
//    public static Tile armorBootsIronTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/boots/iron.png");
//    public static Tile armorBootsLeatherTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/boots/leather.png");
//    public static Tile armorHelmetIronTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/helmet/iron.png");
//    public static Tile armorHelmetLeatherTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/helmet/leather.png");
//    public static Tile armorBeltIronTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/belt/iron.png");
//    public static Tile armorBeltLeatherTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/belt/leather.png");
//    public static Tile armorEarringTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/jewelry/earring.png");
//    public static Tile armorNecklaceTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/jewelry/necklace.png");
//    public static Tile armorRingTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/jewelry/ring.png");
//    public static Tile armorShieldIronTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/shield/iron.png");
//    public static Tile armorShieldWoodTile = new LootTile(ARMOR_CHEST_LEATHER_TILE_ID, "/tiles/loot/shield/wood.png");
    
    
    public static Tile swordTileId = new LootTile(SWORD_TILE_ID, "/tiles/loot/weapons/sword.png");
    
    public static Tile foodTileId = new LootTile(FOOD_TILE_ID, "/tiles/loot/food.png");
    public static Tile potionTileId = new LootTile(POTION_TILE_ID, "/tiles/loot/potion.png");
    public static Tile scrollTileId = new LootTile(SCROLL_TILE_ID, "/tiles/loot/scroll.png");
    
    public static Tile glassBottleTile = new LootTile(GLASS_BOTTLE_TILE_ID, "/tiles/loot/glass_bottle.png");
    
    public LootTile(int id, String fileName) {
        super(id, fileName, false, false, false);
    } // constructor
    
    
} // LootTile
