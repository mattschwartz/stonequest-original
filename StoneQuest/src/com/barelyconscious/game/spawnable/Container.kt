package com.barelyconscious.game.spawnable

import com.barelyconscious.game.item.Item
import com.barelyconscious.game.menu.LootPickupMenu
import com.barelyconscious.systems.MessageSystem
import com.barelyconscious.services.TextLogMessageData
import com.barelyconscious.services.TextLogWriterService

class Container(
    displayName: String,
    initialTileId: Int,
    spentTileId: Int,
    x: Int,
    y: Int,
    private val messageSystem: MessageSystem,
    private val lootWindow: LootPickupMenu
) : Doodad(displayName, initialTileId, spentTileId, x, y) {

    private val itemList: ArrayList<Item> = ArrayList()

    /**
     * Fill the contents of the container with loot
     * @param itemContents the conents of the container
     */
    fun setContents(itemContents: List<Item>) {
        itemList.addAll(itemContents)
    }

    override fun interact() {
        if (itemList.isEmpty()) {
            messageSystem.sendMessage(
                TextLogWriterService.LOG_WRITE_TEXT,
                TextLogMessageData("The $displayName is empty."),
                this
            )
        } else {
            messageSystem.sendMessage(
                TextLogWriterService.LOG_WRITE_TEXT,
                TextLogMessageData("You open the $displayName."),
                this
            )


            lootWindow.setItemList(itemList)
            lootWindow.setActive()
        }

        super.interact()
    }
}
