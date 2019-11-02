package com.barelyconscious.components

import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.items.AItem
import com.barelyconscious.systems.EntityManager

class InventoryComponent(entity: AEntity) : AComponent(entity) {

    companion object {
        const val MAX_ITEM_SLOTS = 28
    }

    private val inventoryItems: MutableList<AItem> = mutableListOf()

    /**
     * Attempts to add the item to the inventory. Will not add if inventory is full.
     *
     * @param item the item to add to the inventory
     * @return index of the Item in the inventory if added, or -1 if inventory is full
     */
    fun addItem(item: AItem): Int {
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
    fun removeItem(inventorySlot: Int): AItem? {
        if (inventorySlot >= 0 && inventorySlot < inventoryItems.size) {
            return inventoryItems.removeAt(inventorySlot)
        }
        return null
    }

    fun useItem(entityManager: EntityManager, inventorySlot: Int): Boolean {
        val item = getItemDetails(inventorySlot)

        return if (item == null) {
            false
        } else {
            item.onUse(entityManager, entity)
            true
        }
    }

    fun getItemDetails(inventorySlot: Int): AItem? {
        if (inventorySlot >= 0 && inventorySlot < inventoryItems.size) {
            return inventoryItems[inventorySlot]
        }
        return null
    }

    fun stackItem() {}

    fun splitItemStack() {}
}
