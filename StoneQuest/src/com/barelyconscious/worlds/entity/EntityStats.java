package com.barelyconscious.worlds.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityStats {

    // Attributes are properties intrinsic to an entity. They are used to calculate stats.
    private final Map<Stats.Attribute, Float> attributes = new HashMap<>();
    // Stats are properties that are derived from attributes and are affected by equipment and other modifiers.
    private final Map<Stats.Stat, Float> stats = new HashMap<>();

    public EntityStats() {
    }

    /**
     * Returns a new EntityStatsBuilder object that can be used to create a new EntityStats object.
     * @return
     */
    public EntityStatsBuilder builder() {
        return new EntityStatsBuilder();
    }

    /**
     * Returns the value of the attribute passed in. If the attribute does not exist, 0 is returned.
     *
     * @param attribute
     * @return
     */
    public float getAttribute(Stats.Attribute attribute) {
        return attributes.getOrDefault(attribute, 0f);
    }

    /**
     * Returns the value of the stat passed in. If the stat does not exist, 0 is returned.
     * @param stat
     * @return
     */
    public float getStat(Stats.Stat stat) {
        return stats.getOrDefault(stat, 0f);
    }

    public static class EntityStatsBuilder {

        private EntityStats result;

        public EntityStatsBuilder withAttribute(Stats.Attribute attribute, float value) {
            result.attributes.put(attribute, value);
            return this;
        }

        public EntityStatsBuilder withStat(Stats.Stat stat, float value) {
            result.stats.put(stat, value);
            return this;
        }

        private EntityStatsBuilder() {
            result = new EntityStats();
        }
    }
}
