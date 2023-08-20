package com.barelyconscious.worlds.gamedata;

public class EntityStatsCalculator {

    private static final double CONS_HEALTH_MODIFIER = 0.6f;
    private static final double LEVEL_HEALTH_MODIFIER = 2.5f;
    private static final double DIFFICULTY_CLASS_MODIFIER = 8f;

    public double toHealth(
        double constitutionValue,
        int level,
        int difficultyClass
    ) {
        double healthFromCons = constitutionValue * CONS_HEALTH_MODIFIER;
        double healthFromLevel = level * LEVEL_HEALTH_MODIFIER;
        double healthFromDc = difficultyClass * DIFFICULTY_CLASS_MODIFIER;

        double totalContributedHealth = healthFromCons + healthFromLevel + healthFromDc;
        return totalContributedHealth;
    }

    public double toPower(
        double intValue,
        int level,
        int difficultyClass
    ) {
        double powerFromInt = intValue;
        double powerFromLevel = level * LEVEL_HEALTH_MODIFIER;
        double powerFromDc = difficultyClass * DIFFICULTY_CLASS_MODIFIER;

        double totalContributedPower = powerFromInt + powerFromLevel + powerFromDc;
        return totalContributedPower;
    }
}
