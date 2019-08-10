package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.services.messaging.Message
import com.barelyconscious.services.messaging.MessageSystem

class HealthPointsComponent(
    var currentHealthPoints: Int
) : IComponent {
}

class MoveComponent : IComponent {

    fun move() {}
}

class DeathComponentSystem(
    entityManager: EntityManager
) : AComponentSystem(entityManager) {

    override fun onUpdate() {
        forEach(HealthPointsComponent::class.java) { entity, healthPointsComponent ->
            if (healthPointsComponent.currentHealthPoints <= 0) {
                entityManager.removeEntity(entity)
            }
        }
    }
}

class TemporaryEffectComponent(
    var remainingTicks: Int,
    private val duration: Int
) : IComponent {

    fun update() {}
}

class TemporaryEffectsComponentSystem(
    entityManager: EntityManager,
    private val messageSystem: MessageSystem
) : AComponentSystem(entityManager) {

    override fun onUpdate() {
        // Get all components that have a temporary effect component and apply
        //  its behavior to that entity
        forEach(TemporaryEffectComponent::class.java) { entity, component ->
            component.update()

            val damageMessage = Message("ENTITY_DAMAGED", null, this)
            messageSystem.sendMessage(damageMessage)

            if (component.remainingTicks <= 0) {
                entityManager.removeComponent(entity, component)
            }
        }
    }
}
