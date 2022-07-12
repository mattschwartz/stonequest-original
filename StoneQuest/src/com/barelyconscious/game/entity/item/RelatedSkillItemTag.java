package com.barelyconscious.game.entity.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelatedSkillItemTag implements ItemTag {
    MEDICINE("Medicine"),
    SURVIVAL("Survival"),
    ANIMAL_HANDLING("Animal Handling"),
    COOKING("Cooking"),

    CHEMISTRY("Chemistry"),
    METALWORKING("Metalworking"),
    TAILORING("Tailoring"),
    GUNSMITHING("Gunsmithing"),
    TECHSMITHING("Techsmithing"),

    ;

    private final String tagName;
}
