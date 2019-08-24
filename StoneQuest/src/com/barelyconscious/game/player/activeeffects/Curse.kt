package com.barelyconscious.game.player.activeeffects

import com.barelyconscious.game.graphics.UIElement
import com.barelyconscious.game.player.AttributeMod

/**
 * Creates a new Curse with the following parameters
 * @param name the displayName of the Curse
 * @param dur how long the Curse lasts
 * @param attributeMods an array of affected attributes
 */
class Curse(
    name: String,
    dur: Int,
    vararg attributeMods: AttributeMod
) : Debuff(name, dur, Debuff.CURSE, UIElement.CURSE_ICON, *attributeMods)
