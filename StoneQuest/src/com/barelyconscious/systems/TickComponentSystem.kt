package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.systems.messaging.data.EmptyMessageData

class HealthPointsComponent(
    var currentHealthPoints: Int
) : IComponent {
}

class MoveComponent : IComponent {

    fun move() {}
}

class DeathComponentSystem(
    entitySystem: EntitySystem
) : AComponentSystem(entitySystem) {

    override fun onUpdate() {
        forEach(HealthPointsComponent::class.java) { entity, healthPointsComponent ->
            if (healthPointsComponent.currentHealthPoints <= 0) {
                entitySystem.removeEntity(entity)
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
    entitySystem: EntitySystem,
    private val messageSystem: MessageSystem
) : AComponentSystem(entitySystem) {

    override fun onUpdate() {
        // Get all components that have a temporary effect component and apply
        //  its behavior to that entity
        forEach(TemporaryEffectComponent::class.java) { entity, component ->
            component.update()

            messageSystem.sendMessage("ENTITY_DAMAGED", EmptyMessageData(), this)

            if (component.remainingTicks <= 0) {
                entitySystem.removeComponent(entity, component)
            }
        }
    }
}
