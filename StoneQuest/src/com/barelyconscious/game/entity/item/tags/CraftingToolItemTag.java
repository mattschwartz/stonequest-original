package com.barelyconscious.game.entity.item.tags;

import com.barelyconscious.game.entity.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Flag which signifies what capability an item has
 */
@Getter
@AllArgsConstructor
public enum CraftingToolItemTag implements ItemTag {

    WATER_SOURCE("Water source"),
    HEAT_SOURCE("Heat source"),
    FLAME_SOURCE("Open flame"),
    ALCHEMY_LAB("Alchemy lab"),
    /**
     * like somewhere inside for sterilization
     */
    CLEAN_SURFACE("Clean surface"),
    ANVIL("Anvil"),
    /**
     * like a pestle & mortar
     */
    PULVERIZING("Pulverizing tool"),
    /**
     * scissors
     */
    TEARING("Tearing tool"),

    /**
     * needle and thread
     */
    SEWING("Sewing tool"),

    // faction-level needs.... is this right?
    LUMBER_MILL("Lumber mill"),
    BLACKSMITH("Blacksmith"),
    BARRACKS("Barracks"),
    ;

    private final String tagName;
}
