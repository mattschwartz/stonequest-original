package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.EntityStatsCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * A mobile unit that can be spawned into the game world. Creatures, players, npcs, etc.
 */
@Getter
public class EntityActor extends Actor {

    protected final Map<TraitName, DynamicValueComponent> traits = new HashMap<>();
    protected final Map<StatName, DynamicValueComponent> stats = new HashMap<>();

    public EntityActor(
        final String name,
        final Vector transform
    ) {
        super(name, transform);
        addComponent(new EquipmentComponent(this));
    }

    public EntityLevelComponent getEntityLevelComponent() {
        return getComponent(EntityLevelComponent.class);
    }

    public EquipmentComponent getEquipment() {
        return getComponent(EquipmentComponent.class);
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
        addComponent(new EquipmentComponent(this));

        var healthComponent = new DynamicValueComponent(this, entityLevel, difficultyClass);
        addComponent(healthComponent);
        stats.put(StatName.HEALTH, healthComponent);

        var powerComponent = new DynamicValueComponent(this, currentPower, maxPower);
        addComponent(powerComponent);
        stats.put(StatName.POWER, powerComponent);

        addComponent(new EntityLevelComponent(this, entityLevel, currentExperience));
    }

    @AllArgsConstructor
    public final class TraitAccessor {
        private final TraitName name;

        public DynamicValueComponent get() {
            return traits.get(name);
        }

        public TraitAccessor set(float currentValue, float maxValue) {
            traits.get(name).setValue(currentValue, maxValue);
            return this;
        }

        public TraitAccessor adjustMaxValueBy(float maxValueDelta) {
            traits.get(name).adjustMaxValueBy(maxValueDelta);
            return this;
        }
    }

    @AllArgsConstructor
    public final class StatAccessor {
        private final StatName name;

        public DynamicValueComponent get() {
            return stats.get(name);
        }

        public StatAccessor set(float currentValue, float maxValue) {
            stats.get(name).setValue(currentValue, maxValue);
            return this;
        }

        public StatAccessor adjustMaxValueBy(float maxValueDelta) {
            stats.get(name).adjustMaxValueBy(maxValueDelta);
            return this;
        }
    }

    public TraitAccessor trait(TraitName name) {
        if (!traits.containsKey(name)) {
            var dvc = new DynamicValueComponent(this, 0, 0);
            addComponent(dvc);
            traits.put(name, dvc);
        }

        return new TraitAccessor(name);
    }

    public StatAccessor stat(StatName name) {
        if (!stats.containsKey(name)) {
            var dvc = new DynamicValueComponent(this, 0, 0);
            addComponent(dvc);
            stats.put(name, dvc);
        }
        return new StatAccessor(name);
    }

    public DynamicValueComponent getPowerComponent() {
        return stats.get(StatName.POWER);
    }

    public DynamicValueComponent getHealthComponent() {
        return stats.get(StatName.HEALTH);
    }
}
