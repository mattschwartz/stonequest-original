package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.Game

class AntitoxinPotionEffect(
    displayName: String
) : PotionEffect(0, displayName, PotionEffect.ANTITOXIN) {

    override fun tick() {
        // Does nothing
    }

    override fun onApplied() {
        println("Removing toxins")
        Game.player.debuffs
            .filter { it.debuffType == Debuff.TOXIN }
            .forEach { Game.player.removeDebuff(it) }
    }

    override fun toString(): String {
        return "Quaff to cure your infections."
    }
}
