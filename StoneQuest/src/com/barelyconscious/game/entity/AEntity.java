package com.barelyconscious.game.entity;

import java.util.*;

/**
 * A mobile unit that can be spawned into the game world. Creatures, players, npcs, etc.
 */
public abstract class AEntity extends Actor {

    private int entityLevel;
    private final Map<Stats, Float> entityStats;

    public AEntity() {
        this(0, new HashMap<>());
    }

    public AEntity(
        final int entityLevel,
        final Map<Stats, Float> entityStats
    ) {
        this.entityLevel = entityLevel;
        this.entityStats = entityStats;
    }
}
