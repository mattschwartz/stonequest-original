package com.barelyconscious.game.spawnable

import com.barelyconscious.game.Common
import com.barelyconscious.game.Game
import com.barelyconscious.game.item.Item

class Container(
    displayName: String,
    initialTileId: Int,
    spentTileId: Int,
    x: Int,
    y: Int
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
            Game.textLog.writeFormattedString(
                "The $displayName is empty.",
                Common.FONT_NULL_RGB
            )
        } else {
            Game.textLog.writeFormattedString(
                "You open the $displayName.",
                Common.FONT_NULL_RGB
            )

            Game.lootWindow.setItemList(itemList)
            Game.lootWindow.setActive()
        }

        super.interact()
    }
}
