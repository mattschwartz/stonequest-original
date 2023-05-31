package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;

public class HealthComponent extends AdjustableValueComponent {

    private static final float CONS_HEALTH_MODIFIER = 0.6f;
    private static final float LEVEL_HEALTH_MODIFIER = 2.5f;
    private static final float DIFFICULTY_CLASS_MODIFIER = 8f;

    public HealthComponent(Actor parent, int level, int difficultyClass) {
        super(parent);

        adjustHealthValue(10, level, difficultyClass);

//        consStat.delegateOnValueChanged.bindDelegate(e -> {
//            adjustHealthValue(e.currentValue, level, difficultyClass);
//            return null;
//        });
    }

    private void adjustHealthValue(
        float constitutionValue,
        int level,
        int difficultyClass
    ) {
        float healthFromCons = constitutionValue * CONS_HEALTH_MODIFIER;
        float healthFromLevel = level * LEVEL_HEALTH_MODIFIER;
        float healthFromDc = difficultyClass * DIFFICULTY_CLASS_MODIFIER;

        float totalContributedHealth = healthFromCons + healthFromLevel + healthFromDc;
        adjustMaxValueBy(totalContributedHealth);
    }
}
