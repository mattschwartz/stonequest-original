package com.barelyconscious.game.item.definitions

import com.barelyconscious.game.Game
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.OptionsKey
import com.barelyconscious.game.player.AttributeMod
import com.barelyconscious.game.player.activeeffects.PotionEffect
import com.barelyconscious.game.player.activeeffects.StatPotionEffect
import com.barelyconscious.services.SoundService
import com.barelyconscious.services.audio.PlayableSound
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.data.SoundMessageData

/**
 * Create a new potion with the following parameters
 *
 * @param name the displayName of the potion, visible to the player
 * @param sellV the amount in gold that a player will receive in exchange for the Item
 * @param stack the amount of Items in one stack
 * @param dur the duration in ticks if the potion is STATBUFF
 * @param type the type of potion being created
 * @param effects if the potion type is STATBUFF, this is an array of attributes which are affected when the potion
 * is quaffed
 */
class Potion(
    displayName: String,
    sellValue: Int,
    stackSize: Int,
    val effects: PotionEffect,
    private val messageSystem: MessageSystem,
    tileId: Int = Tile.POTION_TILE_ID
) : Item(
    displayName,
    1,
    1,
    effects.toString(),
    arrayListOf(),
    sellValue,
    1,
    tileId
) {

    init {
        optionsDescriptions[OptionsKey.USE] = "quaff"
    }

    override val itemAffixes: ArrayList<AttributeMod>
        get() {
            val result = ArrayList<AttributeMod>()
            if (effects is StatPotionEffect) {
                result.addAll(effects.getAffixesAsArray())
            }
            return result
        }

    override fun onUse() {
        messageSystem.sendMessage(
            SoundService.PLAY_SOUND,
            SoundMessageData(PlayableSound.DRINK_POTION),
            this)

        if (effects is StatPotionEffect) {
            val newEffects = StatPotionEffect(
                effects.durationInTicks,
                effects.displayName,
                *effects.getAffixesAsArray()
            )
            newEffects.onApplied()
        } else {
            effects.onApplied()
        }

        if (--stackSize <= 0) {
            Game.inventory.removeItem(this)
        }
    }
}
