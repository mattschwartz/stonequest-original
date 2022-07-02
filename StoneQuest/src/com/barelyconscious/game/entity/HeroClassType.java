package com.barelyconscious.game.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeroClassType {

    /**
     * Abilities centered around natural/survivalist theme.
     */
    NOMAD("Nomad"),
    /**
     * Typical sneakyboi
     */
    ROGUE("Rogue"),
    /**
     * Assassins are like stealth hunters
     */
    ASSASSIN("Assassin"),
    /**
     * Combines death magic with sneakyboi twist
     */
    SHADOW_ROGUE("Shadow Rogue"),
    /**
     *
     */
    SHADOW_ASSASSIN("Shadow Assassin"),
    /**
     * Magic through spells and incantations. Ability to imbue magical properties onto physical objects. Magic bombs,
     * Magic traps, etc. Spells work like individual properties that can stack and modify in unique ways based on the
     * different combinations used.
     *
     * eg - magic crossbow that is imbued with faster attack rate and fire arrows that never run out.
     *
     * eg - a broadsword that spawns a copy of itself, mirroring its actions; and an echo spell that causes the effect
     * to trigger once more (cloning the cloned broadsword)
     */
    ARCANIST("Arcanist"),

    /**
     * Melee-focused with bombs and oils. Can combine bombs/oils with traps, arrows to imbue properties. Also has the
     * ability to create potions on expedition up to any potion that otherwise could only be made within cities.
     */
    ALCHEMIST("Alchemist"),

    /**
     * Deeply in tune with eldritch magic and has abilities that draw on power from the fabric of the universe.
     * At higher abilities, the occultist can teleport some distance through space, briefly banish certain creatures to
     * other universes, collapse space around entities, redirect projectiles (like portals). Can imbue physical items
     * with eldritch gel to impart those benefits onto the item:
     *
     * eg - summon various forms from other universes for a limited amount of time (up to permanent at higher levels)
     *    - imbue eldritch summoning into a sword to cause it to slap enemies around with tentacles on hit;
     *    - fuse knives to your fingers
     */
    OCCULTIST("Occultist"),

    /**
     * Has divine abilities and has increased defense against magic. Can create anti-magic barriers and counterspells to
     * reflect enemy magic. Ability to mend wounds with magic as well.
     *
     * offensive combat is primarily based around using an enemies strength against them: by reflecting magic, smiting
     * unnatural creatures, mind control/confusion.
     */
    PRIEST("Priest"),
    /**
     * Abilities centered around machines/mechanical/metal stuff
     *
     * Maybe coupled with like nomad to create robo druid?
     */
    MACHINIST("Machinist")
    ;

    private final String heroClassName;
}
