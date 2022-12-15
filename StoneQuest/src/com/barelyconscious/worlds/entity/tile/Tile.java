package com.barelyconscious.worlds.entity.tile;

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
    private final boolean blocksMovement;
    private final boolean blocksSight;
}
