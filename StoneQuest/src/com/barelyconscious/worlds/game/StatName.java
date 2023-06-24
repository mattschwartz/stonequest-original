package com.barelyconscious.worlds.game;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatName {
    // Defensive stats
    ARMOR("Armor", "Lessens the impact of physical damage."),
    FORTITUDE("Fortitude", "Lessens the impact of mental damage."),
    WARDING("Warding", "Lessens the impact of spiritual damage."),

    // Offensive stats
    ABILITY_POWER("Ability Power", "Increases the effectiveness of abilities."),
    ABILITY_SPEED("Ability Speed", "Increases the speed at which abilities are performed."),
    PRECISION("Precision", "Reduces likelihood of glancing blows and increases likelihood of delivering a brutal blow."),


    // Combat Resources
    HEALTH("Health", "The amount of damage you can take before death."),
    ENERGY("Energy", "Combat resource for performing Body abilities."),
    FOCUS("Focus", "Combat resource for performing Mind abilities."),
    SPIRIT("Spirit", "Combat resource for performing Soul abilities."),
    ;

    public final String name;
    public final String description;
}
