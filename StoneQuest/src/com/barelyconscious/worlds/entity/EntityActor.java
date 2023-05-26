package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.entity.components.EntityLevelComponent;
import com.barelyconscious.worlds.entity.components.HealthComponent;
import com.barelyconscious.worlds.entity.components.PowerComponent;
import com.barelyconscious.worlds.entity.components.AttributeComponent;
import com.barelyconscious.worlds.entity.components.EquipmentComponent;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.Getter;

/**
 * A mobile unit that can be spawned into the game world. Creatures, players, npcs, etc.
 */
@Getter
public class EntityActor extends Actor {

    private final AttributeComponent entityAttributeComponent;

    private final HealthComponent healthComponent;
    private final PowerComponent powerComponent;
    private final EntityLevelComponent entityLevelComponent;
    private final EquipmentComponent equipment;

    public EntityActor(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentExperience,
        final float currentPower,
        final float maxPower,
        final Stats entityStats
    ) {
        this(name, transform, entityLevel, currentExperience,
            currentPower, maxPower, entityStats, 0);
    }

    public EntityActor(
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
        this.equipment = new EquipmentComponent(this);
        this.entityAttributeComponent = new AttributeComponent(this, entityStats);

        addComponent(entityAttributeComponent);
        addComponent(healthComponent = new HealthComponent(this, entityAttributeComponent, entityLevel, difficultyClass));
        addComponent(powerComponent = new PowerComponent(this, currentPower, maxPower));
        addComponent(entityLevelComponent = new EntityLevelComponent(this, entityLevel, currentExperience));
    }
}
