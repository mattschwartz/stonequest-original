package com.barelyconscious.worlds;

import lombok.AllArgsConstructor;

public class StatsFormulaAnalyzer {

    public static void main(String[] args) {

        float minWeaponDamage = 24;
        float maxWeaponDamage = 33;
        float precision = .03f;
        int numMisses = 0;
        int numGlancing = 0;
        int numBrutal = 0;
        int numOther = 0;
        int numTotal= 100000;
        for (int i = 0; i < numTotal; ++i) {
            var output = calculateDamage(
                minWeaponDamage, maxWeaponDamage, precision
            );
            if (output.isBrutal) {
                ++numBrutal;
            } else if (output.isGlancing) {
                ++numGlancing;
            } else if (output.damage == 0) {
                ++numMisses;
            }else {
                ++numOther;
            }
        }

        System.out.println(
            numMisses + " misses (" + Math.round(100*numMisses / numTotal) + "%), " +
            numGlancing + " glancing blows (" + Math.round(100*numGlancing / numTotal) + "%), " +
            numBrutal + " brutal blows (" + Math.round(100*numBrutal/numTotal) + "%), and " +
            numOther + " normal blows (" + Math.round(100*numOther /numTotal) + "%)");

//        System.out.println("Ability Power Scaling");
//        for (int level = 1; level <= 25; level += 1) {
//            System.out.println("Level: " + level + ", attribute: " + calculateLevelScaledTrait(-1, level, .25f));
//            var abilityPowerSb = new StringBuilder();
//            var armorSb = new StringBuilder();
//            var attrSb = new StringBuilder();
//
//            for (int primaryTrait = 1; primaryTrait <= 20; ++primaryTrait) {
//
//                var ab = calculateAbilityPower(level, primaryTrait);
//                abilityPowerSb.append(Math.round(ab)).append(", ");
//
//                var armor = calculateArmor(level, primaryTrait);
//                armorSb.append(Math.round(armor)).append(", ");
//            }
////            System.out.println("Armor:         " + armorSb);
////            System.out.println("Ability Power: " + abilityPowerSb);
//        }
    }

    @AllArgsConstructor
    static class DamageOutput {
        float damage;
        boolean isBrutal;
        boolean isGlancing;
    }

    private static DamageOutput calculateDamage(
        float minWeaponDamage,
        float maxWeaponDamage,
        float precision
    ) {
        float missChance = 0.03f; // unchangeable, ew
        float glancingChance = Math.max(0, 0.25f - precision);
        float brutalChance = Math.max(0, 0.05f + precision);

        var weaponDamageRoll = (float) Math.floor(
            Math.random() *(maxWeaponDamage - minWeaponDamage + 1) + minWeaponDamage);

        double critChance = Math.random();
        if (critChance < brutalChance) {
            // brutal blows deal 167% damage
            return new DamageOutput(
                weaponDamageRoll * 1.67f, true, false
            );
        } else if (critChance < (glancingChance + brutalChance)) {
            // glancing blows deal 33% damage
            return new DamageOutput(
                weaponDamageRoll * 0.33f, false, true
            );
        } else if (critChance < (missChance + glancingChance + brutalChance)) {
            return new DamageOutput(0, false, false);
        } else {
            return new DamageOutput(weaponDamageRoll, false, false);
        }
    }

    private static float calculatePrecision() {
        return 0.5f;
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
