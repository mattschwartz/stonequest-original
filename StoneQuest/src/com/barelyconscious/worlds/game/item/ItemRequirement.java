package com.barelyconscious.worlds.game.item;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.Stats;
import com.barelyconscious.worlds.entity.components.AdjustableValueComponent;
import com.barelyconscious.worlds.entity.components.EntityLevelComponent;
import com.barelyconscious.worlds.entity.components.AttributeComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A check against an Entity to ensure some requirement is met before an item can be used/equipped.
 */
public abstract class ItemRequirement {

    public abstract boolean meetsRequirement(final EntityActor entity);

    @AllArgsConstructor
    public static class LevelItemRequirement extends ItemRequirement {

        private final int requiredLevel;

        @Override
        public boolean meetsRequirement(EntityActor entity) {
            EntityLevelComponent entityLevelComponent = entity.getEntityLevelComponent();
            return entityLevelComponent.getEntityLevel() >= requiredLevel;
        }
    }

    @Getter
    public static class StatItemRequirement extends ItemRequirement {
        private final Stats.Attribute attribute;
        private final float requiredStatValue;
        /**
         * If true, requirement will only use the max stat value instead of the current
         * <p>
         * defaults to true
         */
        private final boolean useMaxStatValue;

        public StatItemRequirement(Stats.Attribute attribute, float requiredStatValue) {
            this(attribute, requiredStatValue, true);
        }

        public StatItemRequirement(Stats.Attribute attribute, float requiredStatValue, boolean useMaxStatValue) {
            this.attribute = attribute;
            this.requiredStatValue = requiredStatValue;
            this.useMaxStatValue = useMaxStatValue;
        }

        @Override
        public boolean meetsRequirement(EntityActor entity) {
            AttributeComponent entityAttributeComponent = entity.getEntityAttributeComponent();
            AdjustableValueComponent stat = entityAttributeComponent.getStat(attribute);

            if (useMaxStatValue) {
                return stat.getMaxValue() >= requiredStatValue;
            } else {
                return stat.getCurrentValue() >= requiredStatValue;
            }
        }
    }

}
