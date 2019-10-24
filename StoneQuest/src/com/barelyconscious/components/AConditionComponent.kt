package com.barelyconscious.components

import com.barelyconscious.entities.AEntity
import com.barelyconscious.systems.EntityManager

abstract class ATickComponent : IComponent {

    /**
     * Called each game tick.
     */
    abstract fun onTick(entityManager: EntityManager, entity: AEntity)
}

/**
 * Temporary positive or negative condition component applied to entities.
 */
abstract class AConditionComponent(val durationInTicks: Int) : ATickComponent() {

    private var ticksRemaining = durationInTicks

    /**
     * Called when the condition is first applied.
     */
    abstract fun onApply(entityManager: EntityManager, entity: AEntity)

    /**
     * Called when the condition expires or is removed.
     */
    abstract fun onRemove(entityManager: EntityManager, entity: AEntity)

    override fun onTick(entityManager: EntityManager, entity: AEntity) {
        --ticksRemaining
        if (ticksRemaining <= 0) {
            onRemove(entityManager, entity)
        }
    }
}

/**
 * Buffs are positive conditions.
 */
abstract class ABuffComponent(durationInTicks: Int) : AConditionComponent(durationInTicks)

/**
 * Debuffs are negative conditions.
 */
abstract class ADebuffComponent(durationInTicks: Int) : AConditionComponent(durationInTicks)

class StatPotionConditionComponent : AConditionComponent(125) {

    private val agilityComponent = AgilityAttributeComponent(4.0)
    private val hitpointsComponent = HitpointsAttributeComponent(25.0)
    private val defenseComponent = DefenseAttributeComponent(-5.0)

    override fun onApply(entityManager: EntityManager, entity: AEntity) {
        entityManager.addComponent(entity, agilityComponent)
        entityManager.addComponent(entity, hitpointsComponent)
        entityManager.addComponent(entity, defenseComponent)
    }

    override fun onRemove(entityManager: EntityManager, entity: AEntity) {
        entityManager.removeComponent(entity, agilityComponent)
        entityManager.removeComponent(entity, hitpointsComponent)
        entityManager.removeComponent(entity, defenseComponent)
    }

}
