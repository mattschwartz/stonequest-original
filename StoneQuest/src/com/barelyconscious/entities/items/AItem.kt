package com.barelyconscious.entities.items

import com.barelyconscious.entities.AEntity
import com.barelyconscious.systems.EntityManager

enum class ItemRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    UNIQUE,
}

abstract class AItem(
    val displayName: String,
    val itemLevel: Int,
    val rarityType: ItemRarity,
    val itemDescription: String
) {

    abstract fun onUse(entityManager: EntityManager, entity: AEntity)
}
