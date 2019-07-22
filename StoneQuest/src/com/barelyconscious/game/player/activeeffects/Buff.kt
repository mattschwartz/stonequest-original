package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.graphics.UIElement

abstract class Buff(
    var durationInTicks: Int,
    val buffType: Int,
    val buffIcon: UIElement
) {

    companion object {
        const val POTION: Int = 0
        const val AURA: Int = 1
        const val MAGIC_EFFECT: Int = 2
        const val SCROLL_EFFECT: Int = 3
    }

    abstract fun onApplied()
    abstract fun tick()
}
