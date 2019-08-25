package com.barelyconscious.game.item.definitions

import com.barelyconscious.game.Game
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.OptionsKey
import com.barelyconscious.game.player.AttributeMod
import com.barelyconscious.game.player.Player

/**
 * Creates a new food item with the following parameters
 *
 * @param name the displayName of the food that is visible to the player
 * @param sellV the value in gold vendors will give to the player in exchange for the item
 * @param stackSize the initial stack size of the food item
 * @param tileId the id of the Tile that is drawn if the item needs to be drawn to the screen
 * @param healthChange the change in health when the food is consumed
 */
class Food(
    displayName: String,
    sellValue: Int,
    stackSize: Int,
    val changeInHealth: Double
) : Item(
    displayName,
    1,
    1,
    "Eat me.",
    arrayListOf(AttributeMod(Player.HITPOINTS, changeInHealth)),
    sellValue,
    stackSize,
    Tile.FOOD_TILE_ID
) {

    init {
        optionsDescriptions[OptionsKey.USE] = "eat"
    }

    override fun onUse() {
        Game.player.setTemporaryAttribute(
            Player.HITPOINTS,
            changeInHealth
        )

        // todo move
        if (--stackSize <= 0) {
            Game.inventory.removeItem(this)
        }
    }

    override fun toString(): String {
        val article = when (displayName[0]) {
            'a', 'e', 'i', 'o', 'u' -> "an"
            else -> "a"
        }
        return "$article $displayName that heals for $changeInHealth"
    }
}
