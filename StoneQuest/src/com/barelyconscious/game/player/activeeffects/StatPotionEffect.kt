package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.Game
import com.barelyconscious.game.player.AttributeMod

class StatPotionEffect(
    duration: Int,
    displayName: String,
    vararg affixes: AttributeMod
) : PotionEffect(duration, displayName, PotionEffect.STATBUFF) {

    private val affixes: List<AttributeMod> = affixes.toList()

    public fun getNumAffixes(): Int =
        affixes.size

    public fun getAffectedAttributeAt(index: Int) =
        affixes[index]

    public fun getAffixesAsArray(): Array<AttributeMod> =
        affixes.toTypedArray()

    override fun onApplied() {
        Game.player.applyBuff(this)

        affixes.forEach {
            Game.player.setTemporaryAttribute(it.attributeId, it.attributeModifier)
        }
    }

    override fun tick() {
        if (--durationInTicks <= 0) {
            onRemoval()
        }
    }

    private fun onRemoval() {
        Game.player.removeBuff(this)

        affixes.forEach {
            Game.player.setTemporaryAttribute(it.attributeId, -it.attributeModifier)
        }
    }

    override fun toString(): String {
        return "Quaff to provide a temporary change in attributes."
    }
}
