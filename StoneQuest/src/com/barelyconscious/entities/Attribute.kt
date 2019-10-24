package com.barelyconscious.entities

enum class Attribute {

    /**
     * Represents the health of the player; if it goes to 0, the Player dies.
     */
     HITPOINTS,
    /**
     * Increases the chance for the player to completely evade incoming attacks; evaded attacks deal no damage to the
     * Player.
     */
     AGILITY,
    /**
     * Increases the chance for the player to hit with a critical strike, significantly increasing the damage of the
     * attack.
     */
     ACCURACY,
    /**
     * Decreases the amount of incoming physical damage done to the Player.
     */
     DEFENSE,
    /**
     * Increases the overall physical damage output for the Player.
     */
     STRENGTH,
    /**
     * The Player's increased bonus to both offensive and defensive Fire magic.
     */
     FIRE_MAGIC,
    /**
     * The Player's increased bonus to both offensive and defensive Frost magic.
     */
     FROST_MAGIC,
    /**
     * The Player's increased bonus to both offensive and defensive Holy magic.
     */
     HOLY_MAGIC,
    /**
     * The Player's increased bonus to both offensive and defensive Chaos magic.
     */
     CHAOS_MAGIC,
    /**
     * Provides no direct bonus to the Player, but increasing this attribute increases all magic schools by an equal
     * amount; however, this attribute is more expensive to level, though not as expensive to level as leveling all
     * magic schools independently.
     */
     SPELL_MAGIC,
}
