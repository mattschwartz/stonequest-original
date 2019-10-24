package com.barelyconscious.entities

import com.barelyconscious.components.*
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.systems.EntityManager
import com.barelyconscious.systems.MoveComponent

class PlayerAEntity : AEntity("cassiius")

fun addAttributesToPlayer(entityManager: EntityManager) {
    val player = PlayerAEntity()

    entityManager.addComponent(
        player,
        SpriteComponent(Tile.PLAYER_TILE_ID))

    entityManager.addComponent(
        player,
        LevelComponent(1))
    entityManager.addComponent(
        player,
        ExperienceComponent(0.0))

    entityManager.addComponent(
        player,
        MoveComponent())
    entityManager.addComponent(
        player,
        InventoryComponent())

    entityManager.addComponent(
        player,
        HitpointsAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        AgilityAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        AccuracyAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        DefenseAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        StrengthAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        FireMagicAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        FrostMagicAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        HolyMagicAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        ChaosMagicAttributeComponent(1.0, 1.0))
    entityManager.addComponent(
        player,
        SpellMagicAttributeComponent(1.0, 1.0))
}
