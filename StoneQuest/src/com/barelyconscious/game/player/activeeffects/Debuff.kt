package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.Game
import com.barelyconscious.game.graphics.UIElement
import com.barelyconscious.game.player.AttributeMod

/**
 * Create a new Debuff with the following parameters
 * @param displayName the name visible to the player of the debuff
 * @param duration how long the debuff lasts before it dissolves
 * @param affectedAttributes an array of attributes that are affected
 * for the duration of the Debuff
 */
abstract class Debuff(
    val displayName: String,
    var duration: Int,
    val debuffType: Int,
    val debuffIcon: UIElement,
    vararg affectedAttributes: AttributeMod
) {

    companion object {
        const val CURSE: Int = 0
        const val TOXIN: Int = 1
    }

    private val affectedAttributes: List<AttributeMod> = affectedAttributes.toList()

    public fun getNumAffectedAttributes(): Int =
        affectedAttributes.size

    /**
     *
     * @param index the index requested
     * @return the affected attribute at index
     */
    public fun getAffectedAttributeAt(index: Int) =
        affectedAttributes[index]

    public open fun tick() {
        if (--duration <= 0) {
            onRemoval()
        }
    }

    /**
     * Extra effects that the debuff will apply when it is inflicted upon
     * the player.
     */
    public fun onApplied() {

    }

    /**
     * When the debuff is removed for any reason, this function will be called
     * so that the debuff can do or undo any effects that it needs to.
     */
    public fun onRemoval() {
        Game.player.removeDebuff(this)
    }
}
