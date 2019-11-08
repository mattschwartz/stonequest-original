package com.barelyconscious.components

import com.barelyconscious.entities.AEntity
import com.barelyconscious.systems.EntitySystem

abstract class ATickComponent : IComponent {

    /**
     * Called each game tick.
     */
    abstract fun onTick(entitySystem: EntitySystem, entity: AEntity)
}

/**
 * Temporary positive or negative condition component applied to entities.
 */
abstract class AConditionComponent(val durationInTicks: Int) : ATickComponent() {

    private var ticksRemaining = durationInTicks

    /**
     * Called when the condition is first applied.
     */
    abstract fun onApply(entitySystem: EntitySystem, entity: AEntity)

    /**
     * Called when the condition expires or is removed.
     */
    abstract fun onRemove(entitySystem: EntitySystem, entity: AEntity)

    override fun onTick(entitySystem: EntitySystem, entity: AEntity) {
        --ticksRemaining
        if (ticksRemaining <= 0) {
            onRemove(entitySystem, entity)
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

    override fun onApply(entitySystem: EntitySystem, entity: AEntity) {
        entitySystem.addComponent(entity, agilityComponent)
        entitySystem.addComponent(entity, hitpointsComponent)
        entitySystem.addComponent(entity, defenseComponent)
    }

    override fun onRemove(entitySystem: EntitySystem, entity: AEntity) {
        entitySystem.removeComponent(entity, agilityComponent)
        entitySystem.removeComponent(entity, hitpointsComponent)
        entitySystem.removeComponent(entity, defenseComponent)
    }

}
