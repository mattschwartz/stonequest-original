package com.barelyconscious.game.entity.resources;

public enum ResourceSprites {

    PLAYER("sprites/player_sprite.png"),
    SEWER_RAT("tiles/entities/sewer_rat.png"),
    SCROLL("tiles/loot/scroll.png"),
    POTION("tiles/loot/potion.png");

    public final String filepath;

    ResourceSprites(final String filepath) {
        this.filepath = filepath;
    }
}
