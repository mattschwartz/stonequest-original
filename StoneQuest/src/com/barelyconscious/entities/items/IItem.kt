package com.barelyconscious.entities.items

interface IItem {
    val itemId: String
    val displayName: String
    val itemLevel: Int
    val rarityType: ItemRarity
    val itemDescription: String
}
