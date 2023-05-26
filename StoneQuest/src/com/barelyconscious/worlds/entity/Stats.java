package com.barelyconscious.worlds.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Properties derived from an entity's attributes, such as its strength.
 */
public final class Stats {

    public enum Stat {
        ARMOR("Armor", "Lessens the impact of physical damage."),
        HITPOINTS("Hitpoints", "The amount of damage you can take before death."),
        ATTACK_SPEED("Attack Speed", "Improves swing speed of weapons."),
        CASTING_SPEED("Casting Speed", "Improves casting time of magical abilities."),
        LUCK("Luck", "Improves likelihood of delivering a brutal blow and receiving better loot."),
        ACCURACY("Accuracy", "Reduces likelihood of glancing blows and increases likelihood of delivering a brutal blow."),

        RESISTANCE_PIERCING("Piercing Resistance", "Resistance to Piercing abilities"),
        RESISTANCE_BLUNT("Blunt Resistance", "Resistance to Blunt abilities"),
        RESISTANCE_SLASHING("Slashing Resistance", "Resistance to Slashing abilities"),
        RESISTANCE_FIRE("Fire Resistance", "Resistance to Fire abilities"),
        RESISTANCE_FROST("Frost Resistance", "Resistance to Frost abilities"),
        RESISTANCE_ELDRITCH("Eldritch Resistance", "Resistance to Eldritch abilities"),
        RESISTANCE_FAITH("Faith Resistance", "Resistance to harmful Faith abilities."),
        RESISTANCE_ELECTRICITY("Electrical Resistance", "Resistance to Electrical abilities"),
        RESISTANCE_BALLISTIC("Ballistic Resistance", "Resistance to Ballistic abilities"),

        BONUS_PIERCING("Piercing bonus", "Bonus to Piercing abilities."),
        BONUS_BLUNT("Blunt bonus", "Bonus to Blunt abilities"),
        BONUS_SLASHING("Slashing bonus", "Bonus to Slashing abilities"),
        BONUS_FIRE("Fire bonus", "Bonus to Fire abilities"),
        BONUS_FROST("Frost bonus", "Bonus to Frost abilities"),
        BONUS_ELDRITCH("Eldritch bonus", "Bonus to Eldritch abilities"),
        BONUS_FAITH("Faith bonus", "Bonus to Faith abilities"),
        BONUS_ELECTRICITY("Electricity bonus", "Bonus to Electrical abilities"),
        BONUS_BALLISTIC("Ballistic bonus", "Bonus to Ballistic abilities"),
        ;

        public final String name;
        public final String description;

        private Stat(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    @AllArgsConstructor
    public enum Attribute {
        STRENGTH("Strength", "str",
            "Determines how strong you are. Improves \ndamage for heavy type weapons."),
        DEXTERITY("Dexterity", "dex",
            "Determines how mobile and acrobatic you are.\nImproves evasive defences and damage for swift \ntype weapons."),
        CONSTITUTION("Constitution", "con",
            "Determines how health you are. Improves health \nand physical resistence"),
        INTELLIGENCE("Intelligence", "int",
            "Determines how bigbrain you are. Improves brain."),
        WISDOM("Wisdom", "wis",
            "Determines how insightful and wise you are. Improves \nintuition to learn stats about monsters, \nexposing weaknesses and identifying \nstrengths."),
        CHARISMA("Charisma", "cha",
            "Determines how suave you are.");

        public final String name;
        public final String shortName;
        public final String description;
    }

    @Getter
    private final Map<Attribute, Float> stats;

    /**
     * Creates Stats with default values.
     */
    public Stats() {
        this(new HashMap<>());
    }

    /**
     * Creates a Stats object with every stat in StatName. Overrides may be
     * supplied to set the value of any/all stats.
     *
     * @param statOverrides sets values of these stats only
     */
    public Stats(final Map<Attribute, Float> statOverrides) {
        this.stats = new HashMap<>();

        for (final Attribute attribute : Attribute.values()) {
            stats.put(attribute,
                statOverrides.getOrDefault(attribute, 0f));
        }
    }

    /**
     * note: every stat is guaranteed to have a value.
     */
    public float getStat(final Attribute attribute) {
        return stats.get(attribute);
    }

    public float setStat(final Attribute attribute, final float newValue) {
        final Float prevValue = stats.put(attribute, newValue);
        // will never actually be null but IDEs are picky and EnumMaps
        // are messy.
        if (prevValue == null) {
            return 0;
        }
        return prevValue;
    }
}
