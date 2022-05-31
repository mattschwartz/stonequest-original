package com.barelyconscious.game.entity.resources;

public enum ResourceSprite {

    PLAYER("sprites/player_sprite.png", 32, 32),
    SEWER_RAT("tiles/entities/sewer_rat.png", 32, 32),
    SCROLL("tiles/loot/scroll.png", 32, 32),
    POTION("tiles/loot/potion.png", 32, 32),

    GRASS_1("tiles/world/grass.png", 32, 32),
    GRASS_2("tiles/world/grass_2.png", 32, 32),
    GRASS_3("tiles/world/grass_3.png", 32, 32);

    public final String filepath;
    public final int width;
    public final int height;

    ResourceSprite(final String filepath, final int width, final int height) {
        this.filepath = filepath;
        this.width = width;
        this.height = height;
    }
}
