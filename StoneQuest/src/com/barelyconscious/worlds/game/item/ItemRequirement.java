package com.barelyconscious.worlds.game.item;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.entity.components.EntityLevelComponent;
import com.barelyconscious.worlds.game.TraitName;
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
        private final StatName stat;
        private final float requiredStatValue;
        /**
         * If true, requirement will only use the max stat value instead of the current
         * <p>
         * defaults to true
         */
        private final boolean useMaxStatValue;

        public StatItemRequirement(StatName stat, float requiredStatValue) {
            this(stat, requiredStatValue, true);
        }

        public StatItemRequirement(StatName stat, float requiredStatValue, boolean useMaxStatValue) {
            this.stat = stat;
            this.requiredStatValue = requiredStatValue;
            this.useMaxStatValue = useMaxStatValue;
        }

        @Override
        public boolean meetsRequirement(EntityActor entity) {
            DynamicValueComponent adjStat = entity.stat(stat).get();

            if (useMaxStatValue) {
                return adjStat.getMaxValue() >= requiredStatValue;
            } else {
                return adjStat.getCurrentValue() >= requiredStatValue;
            }
        }
    }

    @AllArgsConstructor
    public static class TraitItemRequirement extends ItemRequirement {
        private final TraitName trait;
        private final float requiredTraitValue;

        @Override
        public boolean meetsRequirement(EntityActor entity) {
            return entity.trait(trait).get().getCurrentValue() >= requiredTraitValue;
        }
    }
}
