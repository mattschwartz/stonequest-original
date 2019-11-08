package com.barelyconscious.entities.items

import com.barelyconscious.entities.AEntity
import com.barelyconscious.systems.EntitySystem

abstract class AItem(
    val itemId: String,
    val displayName: String,
    val itemLevel: Int,
    val rarityType: ItemRarity,
    val itemDescription: String
) {

    abstract fun onUse(entitySystem: EntitySystem, entity: AEntity)
}
