package com.barelyconscious.components

import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.EquipmentSlot
import com.barelyconscious.entities.items.definitions.EquipmentItem
import com.barelyconscious.systems.EntitySystem

/**
 * Defines equipment slots that holds equipment for an entity.
 */
class EquipmentDollComponent : IComponent {

    private val equipmentDoll = mutableMapOf<EquipmentSlot, EquipmentItem>()

    fun isSlotReserved(slotId: EquipmentSlot): Boolean =
        equipmentDoll.containsKey(slotId)

    fun getEquippedItem(slotId: EquipmentSlot): EquipmentItem? =
        equipmentDoll[slotId]

    fun equipItem(entitySystem: EntitySystem, entity: AEntity, equipmentItem: EquipmentItem) {
        equipmentItem.modifierComponents.forEach {
            entitySystem.addComponent(entity, it)
        }

        equipmentDoll[equipmentItem.slotId] = equipmentItem
    }

    fun unequipItem(entitySystem: EntitySystem, entity: AEntity, equipmentItem: EquipmentItem) {
        equipmentItem.modifierComponents.forEach {
            entitySystem.removeComponent(entity, it)
        }

        equipmentDoll.remove(equipmentItem.slotId)
    }
}
