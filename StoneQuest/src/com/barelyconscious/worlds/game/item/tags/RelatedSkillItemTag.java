package com.barelyconscious.worlds.game.item.tags;

import com.barelyconscious.worlds.game.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classification of item by skill they are associated with (as ingredients/products)
 */
@Getter
@AllArgsConstructor
public enum RelatedSkillItemTag implements ItemTag {
    MEDICINE("Medicine"),
    SURVIVAL("Survival"),
    ANIMAL_HANDLING("Animal Handling"),
    COOKING("Cooking"),
    WOODWORKING("Woodworking"),

    CHEMISTRY("Chemistry"),
    METALWORKING("Metalworking"),
    TAILORING("Tailoring"),
    GUNSMITHING("Gunsmithing"),
    TECHSMITHING("Techsmithing"),
    FLETCHING("Fletching"),
    STONEWORKING("Stoneworking");

    private final String tagName;
}
