package com.barelyconscious.game.entity.resources;

public enum ResourceSprites {

    PLAYER("sprites/player_sprite.png"),
    SEWER_RAT("tiles/entities/sewer_rat.png"),
    SCROLL("tiles/loot/scroll.png"),
    POTION("tiles/loot/potion.png"),

    GRASS_1("tiles/world/grass.png"),
    GRASS_2("tiles/world/grass_2.png"),
    GRASS_3("tiles/world/grass_3.png");

    public final String filepath;

    ResourceSprites(final String filepath) {
        this.filepath = filepath;
    }
}
