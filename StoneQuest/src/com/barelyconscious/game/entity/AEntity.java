package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.EntityLevelComponent;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.PowerComponent;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

/**
 * A mobile unit that can be spawned into the game world. Creatures, players, npcs, etc.
 */
@Getter
public class AEntity extends Actor {

    private int entityLevel;
    private final Stats entityStats;

    public AEntity(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentExperience,
        final float currentHealth,
        final float maxHealth,
        final float currentPower,
        final float maxPower,
        final Stats entityStats
    ) {
        super(name, transform);

        this.entityLevel = entityLevel;
        this.entityStats = entityStats;

        addComponent(new HealthComponent(this, currentHealth, maxHealth));
        addComponent(new PowerComponent(this, currentPower, maxPower));
        addComponent(new EntityLevelComponent(this, entityLevel, currentExperience));
    }

    protected void adjustEntityLevel(final int delta) {
        entityLevel += delta;
    }
}
