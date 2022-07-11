package com.barelyconscious.game.entity.item;

import com.barelyconscious.game.entity.AEntity;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.components.AdjustableValueComponent;
import com.barelyconscious.game.entity.components.EntityLevelComponent;
import com.barelyconscious.game.entity.components.StatsComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A check against an Entity to ensure some requirement is met before an item can be used/equipped.
 */
public abstract class ItemRequirement {

    public abstract boolean meetsRequirement(final AEntity entity);

    @AllArgsConstructor
    public static class LevelItemRequirement extends ItemRequirement {

        private final int requiredLevel;

        @Override
        public boolean meetsRequirement(AEntity entity) {
            EntityLevelComponent entityLevelComponent = entity.getEntityLevelComponent();
            return entityLevelComponent.getEntityLevel() >= requiredLevel;
        }
    }

    @Getter
    public static class StatItemRequirement extends ItemRequirement {
        private final Stats.StatName statName;
        private final float requiredStatValue;
        /**
         * If true, requirement will only use the max stat value instead of the current
         * <p>
         * defaults to true
         */
        private final boolean useMaxStatValue;

        public StatItemRequirement(Stats.StatName statName, float requiredStatValue) {
            this(statName, requiredStatValue, true);
        }

        public StatItemRequirement(Stats.StatName statName, float requiredStatValue, boolean useMaxStatValue) {
            this.statName = statName;
            this.requiredStatValue = requiredStatValue;
            this.useMaxStatValue = useMaxStatValue;
        }

        @Override
        public boolean meetsRequirement(AEntity entity) {
            StatsComponent entityStatsComponent = entity.getEntityStatsComponent();
            AdjustableValueComponent stat = entityStatsComponent.getStat(statName);

            if (useMaxStatValue) {
                return stat.getMaxValue() >= requiredStatValue;
            } else {
                return stat.getCurrentValue() >= requiredStatValue;
            }
        }
    }

}
