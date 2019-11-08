package com.barelyconscious.services

import com.barelyconscious.components.InventoryComponent
import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.items.IItem
import com.barelyconscious.systems.messaging.IMessageObserver
import com.barelyconscious.systems.messaging.data.IMessageData
import com.barelyconscious.systems.messaging.data.MessageResponse
import com.barelyconscious.systems.EntitySystem
import com.barelyconscious.systems.MessageSystem

class NewItemMessageData(val entity: AEntity, val item: IItem) : IMessageData
class ItemSlotMessageData(val entity: AEntity, val itemSlot: Int) : IMessageData

class InventoryService(
    private val messageSystem: MessageSystem,
    private val entitySystem: EntitySystem
) : IMessageObserver {

    companion object {
        const val ADD_ITEM = "stonequest/InventorySystem/ADD_ITEM"
        const val REMOVE_ITEM = "stonequest/InventorySystem/REMOVE_ITEM"
        const val USE_ITEM = "stonequest/InventorySystem/USE_ITEMS"
        const val GET_ITEM_DETAILS = "stonequest/InventorySystem/GET_ITEM_DETAILS"
    }

    override fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse {
        return when (eventCode) {
            ADD_ITEM -> if (data is NewItemMessageData) {
                addItem(data)
                MessageResponse.ok()
            } else {
                MessageResponse.failed("Cannot add Item \"$data\" to inventory")
            }

            REMOVE_ITEM -> if (data is ItemSlotMessageData) {
                MessageResponse.ok(removeItem(data))
            } else {
                MessageResponse.failed("Cannot add Item \"$data\" to inventory")
            }

            USE_ITEM -> if (data is ItemSlotMessageData) {
                MessageResponse.ok(useItem(data))
            } else {
                MessageResponse.failed("Cannot add Item \"$data\" to inventory")
            }

            GET_ITEM_DETAILS -> if (data is ItemSlotMessageData) {
                MessageResponse.ok(getItemDetails(data))
            } else {
                MessageResponse.failed("Cannot add Item \"$data\" to inventory")
            }

            else -> MessageResponse.failed("Unsupported event code $eventCode")
        }
    }

    /**
     * See [InventoryComponent.addItem]
     */
    private fun addItem(data: NewItemMessageData): Int {
        val inventoryComponent = entitySystem.getSingleComponent(data.entity, InventoryComponent::class)

        return inventoryComponent.addItem(data.item)
    }

    /**
     * See [InventoryComponent.removeItem]
     */
    private fun removeItem(data: ItemSlotMessageData): IItem? {
        val inventoryComponent = entitySystem.getSingleComponent(data.entity, InventoryComponent::class)

        return inventoryComponent.removeItem(data.itemSlot)
    }

    /**
     * See [InventoryComponent.useItem]
     */
    private fun useItem(data: ItemSlotMessageData): Boolean {
        val inventoryComponent = entitySystem.getSingleComponent(data.entity, InventoryComponent::class)

        return inventoryComponent.useItem(messageSystem, data.entity, data.itemSlot)
    }

    /**
     * See [InventoryComponent.getItemDetails]
     */
    private fun getItemDetails(data: ItemSlotMessageData): IItem? {
        val inventoryComponent = entitySystem.getSingleComponent(data.entity, InventoryComponent::class)

        return inventoryComponent.getItemDetails(data.itemSlot)
    }
}
