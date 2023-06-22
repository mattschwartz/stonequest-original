package com.barelyconscious.worlds.game.hero;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeroClassType {

    THIEF("Thief",
        "Stealth and subterfuge. Can pickpocket, pick locks, and sneak around. Can use traps and poisons. Can use a variety of weapons, but is most effective with daggers and bows.",
        16, 14, 8, 7, 5),
    ALCHEMIST("Alchemist",
        "Melee-focused with bombs and oils. Can combine bombs/oils with traps, arrows to imbue properties. Also has the ability to create potions on expedition up to any potion that otherwise could only be made within cities.",
        14, 5, 8, 16, 7),
    MACHINIST("Machinist",
        "Specializes in mutating and augmenting the body with mechanical parts through the use of Worldblood.",
        7, 16, 14, 8, 5),
    PRIEST("Priest",
        "Has divine abilities and has increased defense against magic. Can create anti-magic barriers and counterspells to reflect enemy magic. Ability to mend wounds with magic as well. Offensive combat is primarily based around using an enemies strength against them: by reflecting magic, smiting unnatural creatures, mind control/confusion.",
        7, 7, 7, 13, 16),
    ;

    private final String heroClassName;
    private final String classDescription;

    public final int startingDexterity;
    public final int startingStrength;
    public final int startingConstitution;
    public final int startingIntelligence;
    public final int startingFaith;
}
