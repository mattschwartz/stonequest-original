package com.barelyconscious.worlds.game;

public class EntityStatsCalculator {

    private static final float CONS_HEALTH_MODIFIER = 0.6f;
    private static final float LEVEL_HEALTH_MODIFIER = 2.5f;
    private static final float DIFFICULTY_CLASS_MODIFIER = 8f;

    public float toHealth(
        float constitutionValue,
        int level,
        int difficultyClass
    ) {
        float healthFromCons = constitutionValue * CONS_HEALTH_MODIFIER;
        float healthFromLevel = level * LEVEL_HEALTH_MODIFIER;
        float healthFromDc = difficultyClass * DIFFICULTY_CLASS_MODIFIER;

        float totalContributedHealth = healthFromCons + healthFromLevel + healthFromDc;
        return totalContributedHealth;
    }

    public float toPower(
        float intValue,
        int level,
        int difficultyClass
    ) {
        float powerFromInt = intValue;
        float powerFromLevel = level * LEVEL_HEALTH_MODIFIER;
        float powerFromDc = difficultyClass * DIFFICULTY_CLASS_MODIFIER;

        float totalContributedPower = powerFromInt + powerFromLevel + powerFromDc;
        return totalContributedPower;
    }
}
