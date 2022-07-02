package com.barelyconscious.game.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class Stats {

    @AllArgsConstructor
    public enum StatName {
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
    private final Map<StatName, Float> stats;

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
    public Stats(final Map<StatName, Float> statOverrides) {
        this.stats = new HashMap<>();

        for (final StatName statName : StatName.values()) {
            stats.put(statName,
                statOverrides.getOrDefault(statName, 0f));
        }
    }

    /**
     * note: every stat is guaranteed to have a value.
     */
    public float getStat(final StatName statName) {
        return stats.get(statName);
    }

    public float setStat(final StatName statName, final float newValue) {
        final Float prevValue = stats.put(statName, newValue);
        // will never actually be null but IDEs are picky and EnumMaps
        // are messy.
        if (prevValue == null) {
            return 0;
        }
        return prevValue;
    }
}
