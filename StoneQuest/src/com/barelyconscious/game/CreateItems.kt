package com.barelyconscious.game

import com.barelyconscious.components.*
import com.barelyconscious.entities.AEntity
import com.barelyconscious.entities.EquipmentSlot
import com.barelyconscious.entities.items.ItemRarity
import com.barelyconscious.entities.items.definitions.EquipmentItem
import com.barelyconscious.entities.items.definitions.UsableItem
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.item.definitions.Armor
import com.barelyconscious.game.item.definitions.Food
import com.barelyconscious.game.item.definitions.ItemKey
import com.barelyconscious.game.item.definitions.Potion
import com.barelyconscious.game.item.definitions.Projectile
import com.barelyconscious.game.item.definitions.Scroll
import com.barelyconscious.game.item.definitions.Weapon
import com.barelyconscious.game.player.AttributeMod
import com.barelyconscious.game.player.Inventory
import com.barelyconscious.game.player.Player
import com.barelyconscious.game.player.activeeffects.AntimagicPotionEffect
import com.barelyconscious.game.player.activeeffects.AntitoxinPotionEffect
import com.barelyconscious.game.player.activeeffects.Curse
import com.barelyconscious.game.player.activeeffects.Poison
import com.barelyconscious.game.player.activeeffects.StatPotionEffect
import com.barelyconscious.game.spawnable.Loot
import com.barelyconscious.systems.MessageSystem
import com.barelyconscious.systems.PeriodicApplicationComponent
import com.barelyconscious.systems.TemporaryComponent
import com.barelyconscious.systems.TemporaryComponentSystem

fun createTestSubjects(
    player: Player,
    inventory: Inventory,
    messageSystem: MessageSystem
) {
    val lHelm = Armor(
        "Leather Helmet",
        2567,
        15,
        Player.HELM_SLOT_ID,
        Tile.HELMET_CLOTH_TILE_ID,
        arrayListOf(
            AttributeMod(Player.ACCURACY, -11.0),
            AttributeMod(Player.FIRE_MAGIC_BONUS, 54.0),
            AttributeMod(Player.HOLY_MAGIC_BONUS, 22.0),
            AttributeMod(Player.DEFENSE, 25.0),
            AttributeMod(Player.HITPOINTS, 12.0),
            AttributeMod(Player.AGILITY, 5.0),
            AttributeMod(Player.DEFENSE, 200.0)
        ),
        1
    )

    val lChest = Armor("Leather Chest", 1500, 27, Player.CHEST_SLOT_ID, Tile.CHEST_CLOTH_TILE_ID, arrayListOf(), 1)
    val lGreaves = Armor("Leather Greaves", 220, 15, Player.GREAVES_SLOT_ID, Tile.GREAVES_LEATHER_TILE_ID, arrayListOf(), 1)
    val lboots = Armor("Leather Boots", 1789, 10, Player.BOOTS_SLOT_ID, Tile.BOOTS_LEATHER_TILE_ID, arrayListOf(), 1)
    val lBelt = Armor("Leather Belt", 1677, 11, Player.BELT_SLOT_ID, Tile.BELT_LEATHER_TILE_ID, arrayListOf(), 1)
    val lShield = Armor("Wooden Shield", 1500, 55, Player.OFF_HAND_SLOT_ID, Tile.OFFHAND_SHIELD_WOOD_TILE_ID, arrayListOf(), 1)
    val wSword = Weapon("Wooden Sword", 1358, 1.0, 4.0, Tile.MAINHAND_SWORD_TILE_ID, arrayListOf(), 1)
    val bNeck = Armor("Epic Awesome SLJ Necklace", 256888, 0, Player.NECK_SLOT_ID, Tile.NECKLACE_TILE_ID, arrayListOf(AttributeMod(Player.HITPOINTS, 200.0)), 1)
    val bRing = Armor("Lousy Piece of Boring Ring", 0, 0, Player.RING_SLOT_ID, Tile.RING_TILE_ID, arrayListOf(), 1)
    val bERing = Armor("Broken Earring", 0, 0, Player.EARRING_SLOT_ID, Tile.EARRING_TILE_ID, arrayListOf(), 1)

    lChest.rarityColorRgb = Common.ITEM_RARITY_RARE_RGB

    val apple = Food("Apple", 0, 1, 99.0)
    val apple2 = Food("Apple", 0, 2, 99.0)
    val healthPotion = Potion("Potion of Might", 2990, 1, StatPotionEffect(129, "Potion of Might", AttributeMod(Player.HITPOINTS, 21.0), AttributeMod(Player.STRENGTH, 15.0)), messageSystem)
    val newPotion = Potion("Potion of Awesome", 159, 3, StatPotionEffect(300, "Potion of Awesome", AttributeMod(Player.DEFENSE, 12.0), AttributeMod(Player.STRENGTH, 3.0)), messageSystem)
    val pootion = Potion("Potion of Pi", 1, 5, StatPotionEffect(95, "Potion of Pi", AttributeMod(Player.DEFENSE, 12.0), AttributeMod(Player.STRENGTH, 3.0), AttributeMod(Player.AGILITY, 15.0), AttributeMod(Player.DEFENSE, 55.0), AttributeMod(Player.FIRE_MAGIC_BONUS, 180.0)), messageSystem)
    val antiMagicPotion = Potion(
        "Antimagic Potion",
        1589,
        2,
        AntimagicPotionEffect("Antimagic Potion"),
        messageSystem,
        Tile.ANTIMAGIC_POTION_TILE_ID)
    val antivenomPotion = Potion(
        "Antivenom",
        18890,
        3,
        AntitoxinPotionEffect("Antivenom"),
        messageSystem)

    val curse = Curse("Curse of Evilness", 299, AttributeMod(Player.ACCURACY, 15.0), AttributeMod(Player.DEFENSE, 5.0))
    val curse2 = Curse("Curse of Evilness", 1000, AttributeMod(Player.AGILITY, 15.0), AttributeMod(Player.DEFENSE, 5.0))
    val curse3 = Curse("Curse of Evilness", 1335, AttributeMod(Player.AGILITY, 155.0), AttributeMod(Player.DEFENSE, 25.0))
    val curse4 = Curse("Curse of Evilness", 1240, AttributeMod(Player.AGILITY, 16.0), AttributeMod(Player.DEFENSE, 15.0))

    val poi = Poison("Snake Venom", 69, 1.5, 7, messageSystem)
    val poi2 = Poison("Spider Bite", 24, 0.5, 6, messageSystem)

    val someBronzeArrows = Projectile("Bronze-tipped Arrows", 255, 15, Tile.ARROW_TILE_ID, true, Projectile.BRONZE_TIP)

    val scr1 = Scroll("Invisibility", 258, 1, messageSystem, arrayListOf(AttributeMod(Player.ACCURACY, 255.0)))

    val key = ItemKey("Key, bitches", 255)

    inventory.addItem(scr1)

    player.applyDebuff(poi)
    player.applyDebuff(curse)
    player.applyDebuff(curse2)
    player.applyDebuff(curse3)
    player.applyDebuff(curse4)
    player.applyDebuff(poi2)

    inventory.addItem(apple)
    inventory.addItem(healthPotion)
    inventory.addItem(apple2)
    inventory.addItem(newPotion)
    inventory.addItem(pootion)
    inventory.addItem(antiMagicPotion)
    inventory.addItem(antivenomPotion)

    inventory.addItem(lHelm)
    inventory.addItem(lChest)
    inventory.addItem(lGreaves)
    inventory.addItem(lboots)
    inventory.addItem(lBelt)
    inventory.addItem(lShield)
    inventory.addItem(wSword)
    inventory.addItem(bNeck)
    inventory.addItem(bRing)
    inventory.addItem(bERing)

    inventory.addItem(someBronzeArrows)

    Game.world.addLoot(Loot(key, 80, 100, messageSystem))
}

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
