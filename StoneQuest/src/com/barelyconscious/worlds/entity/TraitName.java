package com.barelyconscious.worlds.entity;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TraitName {
    STRENGTH("Strength", "str",
        "Determines how strong you are. Improves \ndamage for heavy type weapons."),
    DEXTERITY("Dexterity", "dex",
        "Determines how mobile and acrobatic you are.\nImproves evasive defences and damage for swift \ntype weapons."),
    CONSTITUTION("Constitution", "con",
        "Determines how health you are. Improves health \nand physical resistence"),
    INTELLIGENCE("Intelligence", "int",
        "Determines how bigbrain you are. Improves brain."),
    WISDOM("Wisdom", "wis",
        "Determines how insightful and wise you are. Improves \nintuition to learn stats about monsters, \nexposing weaknesses and identifying \nstrengths."),
    CHARISMA("Charisma", "cha",
        "Determines how suave you are.");

    public final String name;
    public final String shortName;
    public final String description;
}
