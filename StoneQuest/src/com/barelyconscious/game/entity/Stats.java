package com.barelyconscious.game.entity;

import java.util.HashMap;
import java.util.Map;

public final class Stats {

    public enum StatName {
        STRENGTH("strength", "str"),
        DEXTERITY("dexterity", "dex"),
        CONSTITUTION("constitution", "con"),
        INTELLIGENCE("", "int"),
        WISDOM("wisdom", "wis"),
        CHARISMA("charisma", "cha");

        public final String name;
        public final String shortName;

        StatName(final String name, final String shortName) {
            this.name = name;
            this.shortName = shortName;
        }
    }

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
