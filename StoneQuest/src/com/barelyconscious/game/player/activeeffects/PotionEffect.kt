package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.graphics.UIElement

abstract class PotionEffect(
    duration: Int,
    val displayName: String,
    val potionType: Int
) : Buff(duration, Buff.POTION, UIElement.POTION_ICON) {

    companion object {
        const val STATBUFF: Int = 0
        const val ANTIMAGIC: Int = 1
        const val ANTITOXIN: Int = 2
    }
}
