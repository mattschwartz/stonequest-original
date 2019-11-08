package com.barelyconscious.services

import com.barelyconscious.components.EquipmentDollComponent
import com.barelyconscious.components.InventoryComponent
import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.items.definitions.EquipmentItem
import com.barelyconscious.systems.EntitySystem
import com.barelyconscious.systems.messaging.IMessageObserver
import com.barelyconscious.systems.messaging.data.IMessageData
import com.barelyconscious.systems.messaging.data.MessageResponse

class EquipmentMessageData(
    val entity: AEntity,
    val equipmentItem: EquipmentItem
) : IMessageData

class EquipmentService(
    private val entitySystem: EntitySystem
) : IMessageObserver {

    companion object {
        const val EQUIP_ITEM = "stonequest/EquipmentService/EQUIP_ITEM"
        const val UNEQUIP_ITEM = "stonequest/EquipmentService/UNEQUIP_ITEM"
    }

    override fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse {
        if (data !is EquipmentMessageData) return MessageResponse.ok()

        val inventoryComponent = entitySystem.getSingleComponent(data.entity, InventoryComponent::class)
        val doll = entitySystem.getSingleComponent(data.entity, EquipmentDollComponent::class)

        return when (eventCode) {
            EQUIP_ITEM -> equipItem(inventoryComponent, doll, data)
            UNEQUIP_ITEM -> unequipItem(inventoryComponent, doll, data)
            else -> MessageResponse.ok()
        }
    }

    private fun equipItem(
        inventoryComponent: InventoryComponent,
        doll: EquipmentDollComponent,
        data: EquipmentMessageData
    ): MessageResponse {
        val equipmentSlot = data.equipmentItem.slotId
        val equippedItem = doll.getEquippedItem(equipmentSlot)

        // Remove the item to be equipped first so we have room in the inventory
        //  for an item already equipped
        inventoryComponent.removeItem(data.equipmentItem)

        if (equippedItem != null) {
            doll.unequipItem(entitySystem, data.entity, equippedItem)
            inventoryComponent.addItem(equippedItem)
        }

        doll.equipItem(entitySystem, data.entity, data.equipmentItem)

        return MessageResponse.ok()
    }

    private fun unequipItem(
        inventoryComponent: InventoryComponent,
        doll: EquipmentDollComponent,
        data: EquipmentMessageData
    ): MessageResponse {
        val equipmentSlot = data.equipmentItem.slotId
        val equippedItem = doll.getEquippedItem(equipmentSlot)

        return if (equippedItem != null) {
            return if (inventoryComponent.addItem(equippedItem) == -1) {
                MessageResponse.failed("Inventory is full")
            } else {
                doll.unequipItem(entitySystem, data.entity, equippedItem)
                MessageResponse.ok()
            }
        } else {
            MessageResponse.ok()
        }
    }
}
