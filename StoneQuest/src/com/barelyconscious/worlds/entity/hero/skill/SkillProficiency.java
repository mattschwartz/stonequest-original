package com.barelyconscious.worlds.entity.hero.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * https://quip.com/1t8fAacr1mVU/Skills-Proficiencies-2021#temp:C:OJR668db46ab5804d55969916bc0
 *
 * Party members are assigned proficiencies based on their background professions.
 * Foresters would have higher proficiencies in woodcutting than a Miner; and vice
 * versa for Mining. Multi-classing will allow the player to choose some from a
 * pool containing both class’s proficiencies. Citizens cannot progress beyond
 * Basic tier in proficiencies other than their starting proficiencies.
 */
@Getter
@AllArgsConstructor
public enum SkillProficiency {

    NOVICE("Novice", "The citizen has never performed this skill before and is likely to make many mistakes without help."),
    BASIC("Basic", "The citizen has a fundamental understanding of the skill. Simple tasks may fail"),
    WORKING("Working", "The citizen has a solid understanding of the skill. Simple tasks never fail. Medium tasks will sometimes fail."),
    EXTENSIVE("Extensive", "Unlocks special enhancements and abilities for certain skills. Simple and medium tasks never fail. Difficult tasks will sometimes fail."),
    EXPERT("Expert", "Unlocks basic experimentation. Simple, medium, and difficult tasks never fail. Experiments can fail."),
    RENOWN("Renown", "Unlocks research funding. Simple, medium, difficult tasks never fail. Experiments never fail.");

    private final String title;
    private final String description;
}
