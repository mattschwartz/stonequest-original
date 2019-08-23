package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.Common
import com.barelyconscious.game.Game
import com.barelyconscious.game.graphics.LineElement
import com.barelyconscious.game.graphics.UIElement
import com.barelyconscious.game.menu.TextLog

/**
 * Create a new Poison with the following parameters
 *
 * @param name the name visible to the player of the Poison
 * @param dur how long the Poison lasts before it is removed
 * @param tickDamage the amount of damage dealt to the Player every freq tick
 * @param damageFrequency how often the Poison deals damage to the Player
 */
class Poison(
    name: String,
    dur: Int,
    val tickDamage: Double,
    val damageFrequency: Int,
    private val textLog: TextLog
) : Debuff(name, dur, Debuff.TOXIN, UIElement.POISON_ICON) {

    private var tick: Int = damageFrequency

    /**
     * Counts down until the next time the poison is to deal damage to the player
     *
     * @return true if the poison is to deal damage to the player on this tick
     */
    public fun nextTick(): Boolean = if (--tick <= 0) {
        tick = damageFrequency
        true
    } else {
        false
    }

    override fun tick() {
        if (nextTick()) {
            Game.player.changeHealthBy(-tickDamage)
            textLog.writeFormattedString(
                toString(),
                Common.FONT_DAMAGE_TEXT_RGB,
                LineElement(displayName, true, Common.FONT_POISON_LABEL_RGB)
            )
        }

        super.tick()
    }

    /**
     * The message written to the TextLog when the Poison deals damage to the Player; the Player class actually writes
     * the message to the TextLog
     *
     * @return
     */
    override fun toString(): String {
        return "$displayName inflicts ${tickDamage.toInt()} damage."
    }
}
