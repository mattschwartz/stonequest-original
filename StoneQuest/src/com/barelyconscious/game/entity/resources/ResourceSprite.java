package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.entity.graphics.RenderLayer;

public enum ResourceSprite {

    HERO_1("sprites/player_sprite.png", 32, 32),
    HERO_2("sprites/player_sprite_ranger.png", 32, 32),
    HERO_3("sprites/player_male.png", 32, 32),

    SEWER_RAT("sprites/sewerRatIcon.png", 32, 32),
    SKELETON("sprites/monster_skeleton.png", 32, 32),

    SCROLL("sprites/scroll.png", 32, 32),
    POTION("sprites/potion_health_small.png", 32, 32),

    GRAVESTONE("sprites/gravestone.png", 32, 32),

    GRASS_1("tiles/world/grass.png", 32, 32),
    GRASS_2("tiles/world/grass_2.png", 32, 32),
    GRASS_3("tiles/world/grass_3.png", 32, 32),

    HERO_SELECTION_SPRITE("sprites/heroSelectionSprite.png", 48, 48);

    public final String filepath;
    public final int width;
    public final int height;
    // todo .. maybe?
    //    public final RenderLayer defaultRenderLayer;

    ResourceSprite(final String filepath, final int width, final int height) {
        this.filepath = filepath;
        this.width = width;
        this.height = height;
    }
}
