package com.barelyconscious.worlds.game.hero.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Skills that a Hero can do.
 */
@Getter
@AllArgsConstructor
public enum HeroSkill implements Skill {

    MEDICINE("Medicine", ""),
    SURVIVAL("Survival", ""),
    ANIMAL_HANDLING("Animal Handling", ""),
    COOKING("Cooking", "");

    private final String skillName;
    private final String description;
}
