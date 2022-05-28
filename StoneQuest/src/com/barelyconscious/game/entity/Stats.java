package com.barelyconscious.game.entity;

public enum Stats {

    STRENGTH("strength", "str"),
    DEXTERITY("dexterity", "dex"),
    CONSTITUTION("constitution", "con"),
    INTELLIGENCE("", "int"),
    WISDOM("wisdom", "wis"),
    CHARISMA("charisma", "cha");

    public final String name;
    public final String shortName;

    Stats(final String name, final String shortName) {
        this.name = name;
        this.shortName = shortName;
    }
}
