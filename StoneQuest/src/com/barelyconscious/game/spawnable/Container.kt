package com.barelyconscious.game.spawnable

import com.barelyconscious.game.Common
import com.barelyconscious.game.Game
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.menu.LootPickupMenu
import com.barelyconscious.game.menu.TextLog

class Container(
    displayName: String,
    initialTileId: Int,
    spentTileId: Int,
    x: Int,
    y: Int,
    private val textLog: TextLog,
    private val lootWindow: LootPickupMenu
) : Doodad(displayName, initialTileId, spentTileId, x, y) {

    private val itemList: ArrayList<Item> = ArrayList();

    /**
     * Fill the contents of the container with loot
     * @param itemContents the conents of the container
     */
    fun setContents(itemContents: List<Item>) {
        itemList.addAll(itemContents)
    }

    override fun interact() {
        if (itemList.isEmpty()) {
            textLog.writeFormattedString(
                "The $displayName is empty.",
                Common.FONT_NULL_RGB
            )
        } else {
            textLog.writeFormattedString(
                "You open the $displayName.",
                Common.FONT_NULL_RGB
            )

            lootWindow.setItemList(itemList)
            lootWindow.setActive()
        }

        super.interact()
    }
}
