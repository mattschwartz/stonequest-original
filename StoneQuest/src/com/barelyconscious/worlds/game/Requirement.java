package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.entity.components.EntityLevelComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class Requirement {

    public abstract boolean meetsRequirement(Actor actor);

    @AllArgsConstructor
    public static class LevelRequirement extends Requirement {

        private final int requiredLevel;

        @Override
        public boolean meetsRequirement(Actor actor) {

            EntityLevelComponent component = actor.getComponent(EntityLevelComponent.class);
            if (component == null) {
                return false;
            } else {
                return component.getEntityLevel() >= requiredLevel;
            }
        }
    }

    @Getter
    public static class StatRequirement extends Requirement {
        private final StatName stat;
        private final float requiredStatValue;
        /**
         * If true, requirement will only use the max stat value instead of the current
         * <p>
         * defaults to true
         */
        private final boolean useMaxStatValue;

        public StatRequirement(StatName stat, float requiredStatValue) {
            this(stat, requiredStatValue, true);
        }

        public StatRequirement(StatName stat, float requiredStatValue, boolean useMaxStatValue) {
            this.stat = stat;
            this.requiredStatValue = requiredStatValue;
            this.useMaxStatValue = useMaxStatValue;
        }

        @Override
        public boolean meetsRequirement(Actor actor) {
            if (!(actor instanceof EntityActor entity)) {
                return false;
            }

            DynamicValueComponent adjStat = entity.stat(stat).get();

            if (useMaxStatValue) {
                return adjStat.getMaxValue() >= requiredStatValue;
            } else {
                return adjStat.getCurrentValue() >= requiredStatValue;
            }
        }
    }

    @AllArgsConstructor
    public static class TraitRequirement extends Requirement {
        private final TraitName trait;
        private final float requiredTraitValue;

        @Override
        public boolean meetsRequirement(Actor actor) {
            if (!(actor instanceof EntityActor entity)) {
                return false;
            }

            return entity.trait(trait).get().getCurrentValue() >= requiredTraitValue;
        }
    }
}
