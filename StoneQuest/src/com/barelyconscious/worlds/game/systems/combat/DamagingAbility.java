package com.barelyconscious.worlds.game.systems.combat;

public final class DamagingAbility {

    public enum DamageType {
        PHYSICAL,
        MAGICAL,
        FIRE,
        ICE,
        LIGHTNING,
        POISON,
        BLEEDING,
        PIERCING,
        CRUSHING,
        SLASHING,
        ARCANE,
        HOLY,
        UNHOLY,
        PURE
    }

    public double damage;
    public DamageType damageType;
    /**
     * The multiplier applied to the damage dealt by this ability as threat.
     * Default=1x
     */
    public double threatMultiplier = 1.0;

    public DamagingAbility(final double damage) {
        this.damage = damage;
        this.damageType = DamageType.PHYSICAL;
    }
}
