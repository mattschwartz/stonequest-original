package com.barelyconscious.worlds;

public class StatsFormulaAnalyzer {

    public static void main(String[] args) {
        System.out.println("Ability Power Scaling");
        for (int level = 1; level <= 25; level += 1) {
            System.out.println("Level: " + level + ", attribute: " + calculateLevelScaledTrait(-1, level, .25f));
            var abilityPowerSb = new StringBuilder();
            var armorSb = new StringBuilder();
            var attrSb = new StringBuilder();

            for (int primaryTrait = 1; primaryTrait <= 20; ++primaryTrait) {

                var ab = calculateAbilityPower(level, primaryTrait);
                abilityPowerSb.append(Math.round(ab)).append(", ");

                var armor = calculateArmor(level, primaryTrait);
                armorSb.append(Math.round(armor)).append(", ");
            }
//            System.out.println("Armor:         " + armorSb);
//            System.out.println("Ability Power: " + abilityPowerSb);
        }

    }

    private static float calculateArmor(
        int level, float dex
    ) {
        return 10 + level * 3 + dex / 4;
    }

    private static float calculateAbilityPower(
        int level, float primaryTrait
    ) {
        return 10 + level * 2 + primaryTrait * 5;
    }

    private static float calculateLevelScaledTrait(
        float startingTrait,
        int level,
        float traitPerLevel
    ) {
        return startingTrait + level * traitPerLevel;
    }
}
