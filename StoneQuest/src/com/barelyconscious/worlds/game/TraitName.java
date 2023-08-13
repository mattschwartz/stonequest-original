package com.barelyconscious.worlds.game;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TraitName {
    STRENGTH("Strength", "STR",
        "Determines how strong you are. Improves \ndamage for heavy type weapons."),
    DEXTERITY("Dexterity", "DEX",
        "Determines how mobile and acrobatic you are.\nImproves evasive defences and damage for swift \ntype weapons."),
    CONSTITUTION("Constitution", "CON",
        "Determines how health you are. Improves health \nand physical resistence"),
    INTELLIGENCE("Intelligence", "INT",
        "Determines how bigbrain you are. Improves brain."),
    FAITH("Faith", "FAI",
        "Determines how strong your bond with the \nspiritual world is. Improves spiritual resistance.")
    ;

    public final String name;
    public final String shortName;
    public final String description;
}
