package com.barelyconscious.game.spawnable

import com.barelyconscious.game.Game
import com.barelyconscious.game.Sound

/**
 * Creates a new Entity with the following parameters
 * @param displayName the displayName of the Entity
 * @param tileId the id of the artwork for the Entity
 */
abstract class Entity(
    displayName: String,
    tileId: Int
) : Sprite(displayName, tileId) {

    var level: Int = 0
        protected set
    var lightRadius: Int = 10
    open var maxHealth: Double = 0.0
        protected set
    open var currentHealth: Double = 0.0
        protected set

    private val faction: Int = 0

    init {
        /* All Entities cause collision with the Player and each other */
        super.setCollision(true)
    }

    constructor(
        displayName: String,
        tileId: Int,
        x: Int,
        y: Int
    ) : this(
        displayName,
        tileId
    ) {
        super.setCollision(true)
        super.setPosition(x, y)
    }

    /**
     * Change the health of the Entity by amount
     * @param amount the amount to change the health of the Entity by
     */
    open fun changeHealthBy(amount: Double) {
        currentHealth += amount
    }

    /**
     * Whatever the Entity needs to do on a game tick, including
     * marking the Entity for removal if its health points fall below 0.
     */
    override fun tick() {
        if (currentHealth <= 0) {
            remove()
        }
    }

    /**
     * Removes the Entity from the world.
     */
    override fun remove() {
        Sound.ENTITY_DEATH.play()
        Game.world.removeEntity(this)
    }
}
