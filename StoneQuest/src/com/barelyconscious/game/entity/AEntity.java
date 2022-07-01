package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.EntityLevelComponent;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.PowerComponent;
import com.barelyconscious.game.entity.components.StatsComponent;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

/**
 * A mobile unit that can be spawned into the game world. Creatures, players, npcs, etc.
 */
@Getter
public class AEntity extends Actor {

    private int entityLevel;
    private final StatsComponent entityStatsComponent;
    public AEntity(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentExperience,
        final float currentPower,
        final float maxPower,
        final Stats entityStats
    ) {
        this(name, transform, entityLevel, currentExperience, currentPower, maxPower, entityStats, 0);
    }

    public AEntity(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentExperience,
        final float currentPower,
        final float maxPower,
        final Stats entityStats,
        final int difficultyClass
    ) {
        super(name, transform);

        this.entityLevel = entityLevel;
        entityStatsComponent = new StatsComponent(this, entityStats);

        addComponent(entityStatsComponent);
        addComponent(new HealthComponent(this, entityStatsComponent, entityLevel, difficultyClass));
        addComponent(new PowerComponent(this, currentPower, maxPower));
        addComponent(new EntityLevelComponent(this, entityLevel, currentExperience));
    }

    protected void adjustEntityLevel(final int delta) {
        entityLevel += delta;
    }
}
