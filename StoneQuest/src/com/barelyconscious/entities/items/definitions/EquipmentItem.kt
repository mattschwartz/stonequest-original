package com.barelyconscious.entities.items.definitions

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.EquipmentSlot
import com.barelyconscious.entities.items.AUsableItem
import com.barelyconscious.entities.items.ItemRarity
import com.barelyconscious.services.EquipmentMessageData
import com.barelyconscious.services.EquipmentService.Companion.EQUIP_ITEM
import com.barelyconscious.systems.MessageSystem

class EquipmentItem(
    itemId: String,
    displayName: String,
    itemLevel: Int,
    rarityType: ItemRarity,
    itemDescription: String,
    val slotId: EquipmentSlot,

    /**
     * A list of modifier components that are added to the Entity when the item is equipped.
     */
    val modifierComponents: List<IComponent>
) : AUsableItem(
    itemId,
    displayName,
    itemLevel,
    rarityType,
    itemDescription
) {

    override fun onUse(messageSystem: MessageSystem, entity: AEntity) {
        messageSystem.sendMessage(
            EQUIP_ITEM,
            EquipmentMessageData(entity, this),
            this)
    }
}
