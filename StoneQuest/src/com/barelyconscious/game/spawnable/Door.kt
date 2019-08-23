package com.barelyconscious.game.spawnable

import com.barelyconscious.game.Common
import com.barelyconscious.game.Game
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.Key
import com.barelyconscious.game.menu.TextLog

class Door(
    displayName: String,
    x: Int,
    y: Int,
    private val lockId: Int,
    private val textLog: TextLog
) : Doodad(displayName, Tile.DOOR_IRON_CLOSED_TILE_ID, Tile.DOOR_IRON_OPEN_TILE_ID, x, y) {

    /**
     * Attempts to open the Door, allowing Entities to pass through it; some doors require keys that match the lock
     * in order to open them.
     * @return true if the Player has the key that will unlock the door, false otherwise
     */
    fun openDoor(): Boolean {
        val playerHasKey = Game.inventory.containsLike {
            it is Key && it.lockId == lockId
        }

        return if (playerHasKey) {
            interact()
            textLog.writeFormattedString("You open the door.", Common.FONT_NULL_RGB)
            true
        } else {
            textLog.writeFormattedString("The door is locked.", Common.FONT_NULL_RGB)
            false
        }
    }
}
