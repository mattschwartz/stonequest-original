package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * A mobile unit that can be spawned into the game world. Creatures, players, npcs, etc.
 */
@Getter
public class EntityActor extends Actor {

    private final EntityLevelComponent entityLevelComponent;
    private final EquipmentComponent equipment;

    private final Map<TraitName, DynamicValueComponent> traits = new HashMap<>();
    private final Map<StatName, DynamicValueComponent> stats = new HashMap<>();

    public DynamicValueComponent getPowerComponent() {
        return stats.get(StatName.POWER);
    }

    public DynamicValueComponent getHealthComponent() {
        return stats.get(StatName.HEALTH);
    }

    public EntityActor(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentExperience,
        final float currentPower,
        final float maxPower
    ) {
        this(name, transform, entityLevel, currentExperience,
            currentPower, maxPower, 0);
    }

    public EntityActor(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentExperience,
        final float currentPower,
        final float maxPower,
        final int difficultyClass
    ) {
        super(name, transform);
        this.equipment = new EquipmentComponent(this);

        var healthComponent = new HealthComponent(this, entityLevel, difficultyClass);
        addComponent(healthComponent);
        stats.put(StatName.HEALTH, healthComponent);

        var powerComponent = new PowerComponent(this, currentPower, maxPower);
        addComponent(powerComponent);
        stats.put(StatName.POWER, powerComponent);

        addComponent(entityLevelComponent = new EntityLevelComponent(this, entityLevel, currentExperience));
    }

    public EntityActor addTrait(TraitName traitName, float value) {
        var adjustableValue = new DynamicValueComponent(this, value, value);

        if (!traits.containsKey(traitName)) {
            addComponent(adjustableValue);
        }

        traits.put(traitName, adjustableValue);
        return this;
    }

    public EntityActor addStat(StatName statName, float value) {
        var adjustableValue = new DynamicValueComponent(this, value, value);

        if (!stats.containsKey(statName)) {
            addComponent(adjustableValue);
        }

        stats.put(statName, adjustableValue);
        return this;
    }

    public DynamicValueComponent getTrait(TraitName traitName) {
        return traits.get(traitName);
    }

    public DynamicValueComponent getStat(StatName statName) {
        return stats.get(statName);
    }

    public void adjustTraitMaxBy(TraitName traitName, float delta) {
        DynamicValueComponent adjValue = traits.get(traitName);
        if (adjValue != null) {
            adjValue.adjustMaxValueBy(delta);
        }
    }

    public void adjustTraitCurrentBy(TraitName traitName, float delta) {
        DynamicValueComponent adjValue = traits.get(traitName);
        if (adjValue != null) {
            adjValue.adjust(delta);
        }
    }

    public void adjustStatMaxBy(StatName statName, float delta) {
        DynamicValueComponent adjValue = stats.get(statName);
        if (adjValue != null) {
            adjValue.adjustMaxValueBy(delta);
        }
    }

    public void adjustStatCurrentBy(StatName statName, float delta) {
        DynamicValueComponent adjValue = stats.get(statName);
        if (adjValue != null) {
            adjValue.adjust(delta);
        }
    }
}
