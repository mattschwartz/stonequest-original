package com.barelyconscious.components

import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.items.AUsableItem
import com.barelyconscious.entities.items.IItem
import com.barelyconscious.systems.EntitySystem
import com.barelyconscious.systems.MessageSystem
import javax.imageio.metadata.IIOInvalidTreeException

class InventoryComponent : IComponent {

    companion object {
        const val MAX_ITEM_SLOTS = 28
    }

    private val inventoryItems: MutableList<IItem> = mutableListOf()

    /**
     * Attempts to add the item to the inventory. Will not add if inventory is full.
     *
     * @param item the item to add to the inventory
     * @return index of the Item in the inventory if added, or -1 if inventory is full
     */
    fun addItem(item: IItem): Int {
        if (inventoryItems.size >= MAX_ITEM_SLOTS) {
            return -1
        }
        inventoryItems.add(item)

        return inventoryItems.size - 1
    }

    /**
     * Attempts to remove the item at the specified index. Does nothing if no item exists at that index.
     *
     * @param inventorySlot the location of the item to remove
     * @return the item removed or null if no item exists at that inventory slot
     */
    fun removeItem(inventorySlot: Int): IItem? {
        if (inventorySlot >= 0 && inventorySlot < inventoryItems.size) {
            return inventoryItems.removeAt(inventorySlot)
        }
        return null
    }

    fun removeItem(item: IItem) {
        inventoryItems.remove(item)
    }

    fun useItem(messageSystem: MessageSystem, entity: AEntity, inventorySlot: Int): Boolean {
        val item = getItemDetails(inventorySlot)

        return if (item == null || item !is AUsableItem) {
            false
        } else {
            item.onUse(messageSystem, entity)
            true
        }
    }

    fun getItemDetails(inventorySlot: Int): IItem? {
        if (inventorySlot >= 0 && inventorySlot < inventoryItems.size) {
            return inventoryItems[inventorySlot]
        }
        return null
    }

    fun stackItem() {}

    fun splitItemStack() {}
}
