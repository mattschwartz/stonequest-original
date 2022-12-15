package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.Stats;

public class HealthComponent extends AdjustableValueComponent {

    private static final float CONS_HEALTH_MODIFIER = 0.6f;
    private static final float LEVEL_HEALTH_MODIFIER = 2.5f;
    private static final float DIFFICULTY_CLASS_MODIFIER = 8f;

    public HealthComponent(final Actor parent, final StatsComponent stats, final int level, final int difficultyClass) {
        super(parent);
        final AdjustableValueComponent consStatValueComponent = stats.getStat(Stats.StatName.CONSTITUTION);

        adjustHealthValue(consStatValueComponent.getCurrentValue(), level, difficultyClass);

        stats.getStat(Stats.StatName.CONSTITUTION).delegateOnValueChanged.bindDelegate(e -> {
            adjustHealthValue(e.currentValue, level, difficultyClass);
            return null;
        });
    }

    private void adjustHealthValue(
        final float constitutionValue,
        final int level,
        final int difficultyClass
    ) {
        final float healthFromCons = constitutionValue * CONS_HEALTH_MODIFIER;
        final float healthFromLevel = level * LEVEL_HEALTH_MODIFIER;
        final float healthFromDc = difficultyClass * DIFFICULTY_CLASS_MODIFIER;

        final float totalContributedHealth = healthFromCons + healthFromLevel + healthFromDc;
        adjustMaxValueBy(totalContributedHealth);
    }
}
