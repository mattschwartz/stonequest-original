package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.services.messaging.data.IMessageData
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.data.EmptyMessageData

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
     duration: Int
) : IComponent {

    var remainingTicks = duration

    fun update() {
        --remainingTicks
    }
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

            messageSystem.sendMessage("ENTITY_DAMAGED", EmptyMessageData(), this)

            if (component.remainingTicks <= 0) {
                entityManager.removeComponent(entity, component)
            }
        }
    }
}
