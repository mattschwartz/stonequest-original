package com.barelyconscious.worlds.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class EntityAttributes {

    @Getter
    private final Map<AttributeName, Float> attributes;

    public EntityAttributes() {
        this(new HashMap<>());
    }
    public EntityAttributes(final Map<AttributeName, Float> attributeOverrides) {
        this.attributes = new HashMap<>();
        for (final AttributeName attributeName : AttributeName.values()) {
            attributes.put(attributeName,
                attributeOverrides.getOrDefault(attributeName, 0f));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum AttributeName {
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

        private final String attributeName;
        private final String attributeDescription;
    }
}
