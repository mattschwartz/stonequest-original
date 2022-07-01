package com.barelyconscious.game.entity.item;

import com.barelyconscious.game.entity.AEntity;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.components.EntityLevelComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A check against an Entity to ensure some requirement is met.
 */
public abstract class ItemRequirement {

    public abstract boolean meetsRequirement(final Actor actor);

    @Getter
    @AllArgsConstructor
    public static final class ItemRequirementStat extends ItemRequirement {

        private final Stats.StatName statName;
        private final float requiredStatValue;

        @Override
        public boolean meetsRequirement(final Actor actor) {
            if (actor instanceof AEntity) {
                final AEntity entity = (AEntity) actor;
                final float entityStatValue = entity.getEntityStatsComponent().getStat(statName).getCurrentValue();
                return entityStatValue >= requiredStatValue;
            }

            return false;
        }
    }

    @Getter
    @AllArgsConstructor
    public static final class ItemRequirementLevel extends ItemRequirement {

        private final int minLevelRequired;

        @Override
        public boolean meetsRequirement(final Actor actor) {
            final EntityLevelComponent levelComponent = actor.getComponent(EntityLevelComponent.class);

            if (levelComponent != null) {
                return levelComponent.getEntityLevel() >= minLevelRequired;
            } else {
                return false;
            }
        }
    }
}
