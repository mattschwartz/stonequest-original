package com.barelyconscious.game.entity.tile;

import com.barelyconscious.game.entity.resources.ResourceSprite;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Tile {

    private final int tileId;
    private final String name;
    private final ResourceSprite spriteResource;
    private final boolean blocksMovement;
    private final boolean blocksSight;
}
