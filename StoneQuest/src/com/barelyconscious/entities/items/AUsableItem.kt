package com.barelyconscious.entities.items

import com.barelyconscious.entities.AEntity
import com.barelyconscious.systems.MessageSystem

abstract class AUsableItem(
    override val itemId: String,
    override val displayName: String,
    override val itemLevel: Int,
    override val rarityType: ItemRarity,
    override val itemDescription: String
) : IItem {

    abstract fun onUse(messageSystem: MessageSystem, entity: AEntity)
}
