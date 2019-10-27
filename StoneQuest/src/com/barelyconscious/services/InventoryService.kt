package com.barelyconscious.services

import com.barelyconscious.game.item.Item
import com.barelyconscious.services.messaging.IMessageObserver
import com.barelyconscious.services.messaging.data.IMessageData
import com.barelyconscious.services.messaging.data.MessageResponse
import com.barelyconscious.systems.EntityManager

class NewItemMessageData(val item: Item) : IMessageData
class ItemSlotMessageData(val itemSlot: Int) : IMessageData

class InventoryService(private val entityManager: EntityManager) : IMessageObserver {

    companion object {
        private const val MAX_ITEM_SLOTS = 28

        const val ADD_ITEM = "stonequest/InventorySystem/ADD_ITEM"
        const val REMOVE_ITEM = "stonequest/InventorySystem/REMOVE_ITEM"
        const val USE_ITEM = "stonequest/InventorySystem/USE_ITEMS"
        const val GET_ITEM_DETAILS = "stonequest/InventorySystem/GET_ITEM_DETAILS"
    }

    private val inventoryItems: MutableList<Item> = mutableListOf()

    override fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse {
        return when (eventCode) {
            ADD_ITEM -> {
                if (data is NewItemMessageData) {
                    addItem(data.item)
                    MessageResponse.ok()
                } else {
                    MessageResponse.Companion.failed("Cannot add Item \"$data\" to inventory")
                }
            }
            REMOVE_ITEM -> {
                if (data is ItemSlotMessageData) {
                    MessageResponse.ok(removeItem(data.itemSlot))
                } else {
                    MessageResponse.Companion.failed("Cannot add Item \"$data\" to inventory")
                }
            }
            USE_ITEM -> {
                if (data is ItemSlotMessageData) {
                    MessageResponse.ok(useItem(data.itemSlot))
                } else {
                    MessageResponse.Companion.failed("Cannot add Item \"$data\" to inventory")
                }
            }
            GET_ITEM_DETAILS -> {
                if (data is ItemSlotMessageData) {
                    MessageResponse.ok(getItemDetails(data.itemSlot))
                } else {
                    MessageResponse.Companion.failed("Cannot add Item \"$data\" to inventory")
                }
            }
            else -> MessageResponse.failed("Unsupported event code $eventCode")
        }
    }

    /**
     * Attempts to add the item to the inventory. Will not add if inventory is full.
     *
     * @param item the item to add to the inventory
     * @return index of the Item in the inventory if added, or -1 if inventory is full
     */
    private fun addItem(item: Item): Int {
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
    private fun removeItem(inventorySlot: Int): Item? {
        if (inventorySlot >= 0 && inventorySlot < inventoryItems.size) {
            return inventoryItems.removeAt(inventorySlot)
        }
        return null
    }

    private fun useItem(inventorySlot: Int): Boolean {
        val item = getItemDetails(inventorySlot)

        return if (item == null) {
            false
        } else {
            item.onUse()
            true
        }
    }

    private fun getItemDetails(inventorySlot: Int): Item? {
        if (inventorySlot >= 0 && inventorySlot < inventoryItems.size) {
            return inventoryItems[inventorySlot]
        }
        return null
    }

    private fun stackItem() {
    }

    private fun splitItemStack() {
    }
}
