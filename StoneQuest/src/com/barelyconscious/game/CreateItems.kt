package com.barelyconscious.game

import com.barelyconscious.components.*
import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.EquipmentSlot
import com.barelyconscious.entities.items.ItemRarity
import com.barelyconscious.entities.items.definitions.EquipmentItem
import com.barelyconscious.entities.items.definitions.UsableItem
import com.barelyconscious.systems.PeriodicApplicationComponent
import com.barelyconscious.systems.TemporaryComponent
import com.barelyconscious.systems.TemporaryComponentSystem

fun createTestItems(inventoryComponent: InventoryComponent) {
    val minorHealingPotion = UsableItem(
        "potion_hitpoints_minor",
        "Minor Healing Potion",
        1,
        ItemRarity.COMMON,
        "Restores a small amount of health",
        listOf(DamageComponent(-5.0))
    )

    // todo how to implement
    val curingPotion = UsableItem(
        "potion_poison_curing",
        "Curing Potion",
        1,
        ItemRarity.COMMON,
        "Cures you of all poisons when imbibed.",
        listOf()
    )

    val vitriolicStrengthPotion = UsableItem(
        "potion_strength_vitriolic",
        "Vitriolic Strength Potion",
        100,
        ItemRarity.EPIC,
        "Grants the imbiber immense strength for 50 seconds and reduces hitpoints for 200 seconds. Can be fatal.",
        listOf(
            StrengthAttributeComponent(500.0),
            HitpointsAttributeComponent(-10.0)
        )
    )

    val leatherHelmetOfAgility = EquipmentItem(
        "helmet_leather_ofAgility",
        "Leather Helmet of Agility",
        12,
        ItemRarity.UNCOMMON,
        "A helmet made of leather. Suitable for the dexterous.",
        EquipmentSlot.HEAD,
        listOf(AgilityAttributeComponent(10.0))
    )

    val cheeseWheel = UsableItem(
        "food_cheeseWheel",
        "Cheese Wheel",
        1,
        ItemRarity.COMMON,
        "A wheel of cheese.",
        listOf(DamageComponent(-5.0))
    )

    inventoryComponent.addItem(minorHealingPotion)
    inventoryComponent.addItem(curingPotion)
    inventoryComponent.addItem(vitriolicStrengthPotion)
    inventoryComponent.addItem(leatherHelmetOfAgility)
    inventoryComponent.addItem(cheeseWheel)
}

fun afflictCurses(temporaryComponentSystem: TemporaryComponentSystem, player: AEntity) {
    val curseOfEvilness = TemporaryComponent(
        120,
        listOf(
            AccuracyAttributeComponent(-15.0),
            DefenseAttributeComponent(-5.0))
    )
    val curseOfWeakness = TemporaryComponent(
        1337,
        listOf(
            DefenseAttributeComponent(-30.0),
            StrengthAttributeComponent(-10.0)
        )
    )

    temporaryComponentSystem.applyTemporaryComponent(player, curseOfEvilness)
    temporaryComponentSystem.applyTemporaryComponent(player, curseOfWeakness)
}

fun afflictPoisons(temporaryComponentSystem: TemporaryComponentSystem, player: AEntity) {
    val snakeVenomPoison = PeriodicApplicationComponent(
        69,
        7,
        DamageComponent(1.5)
    )
    val spiderVenom = PeriodicApplicationComponent(
        24,
        6,
        DamageComponent(0.5)
    )


}
