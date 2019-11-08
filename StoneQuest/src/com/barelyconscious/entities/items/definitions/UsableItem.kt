package com.barelyconscious.entities.items.definitions

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.items.AUsableItem
import com.barelyconscious.entities.items.ItemRarity
import com.barelyconscious.systems.MessageSystem

class UsableItem(
    itemId: String,
    displayName: String,
    itemLevel: Int,
    rarityType: ItemRarity,
    itemDescription: String,
    val modifierComponents: List<IComponent>
) : AUsableItem(
    itemId,
    displayName,
    itemLevel,
    rarityType,
    itemDescription
) {

    override fun onUse(messageSystem: MessageSystem, entity: AEntity) {
    }
}
