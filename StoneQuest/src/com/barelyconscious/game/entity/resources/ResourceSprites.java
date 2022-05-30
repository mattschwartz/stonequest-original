package com.barelyconscious.game.entity.resources;

public enum ResourceSprites {

    PLAYER("res/tiles/entities/player.png"),
    SEWER_RAT("res/tiles/entities/sewer_rat.png"),
    SCROLL("res/tiles/loot/scroll.png"),
    POTION("res/tiles/loot/potion.png");

    public final String filepath;

    ResourceSprites(final String filepath) {
        this.filepath = filepath;
    }
}
