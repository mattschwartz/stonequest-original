package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.Game

class AntimagicPotionEffect(
    displayName: String
) : PotionEffect(0, displayName, PotionEffect.ANTIMAGIC) {

    override fun tick() {
        // Does nothing
    }

    /**
     * Removes all Magic debuffs from the player when the potion is
     * used.
     */
    override fun onApplied() {
        println("Removing curses")
        Game.player.debuffs
            .filter { it.debuffType == Debuff.CURSE }
            .forEach { Game.player.removeDebuff(it) }
    }

    override fun toString(): String {
        return "Quaff to cleanse your afflictions."
    }
}
