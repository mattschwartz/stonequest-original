package com.barelyconscious.systems

import com.barelyconscious.components.DamageComponent
import com.barelyconscious.components.HitpointsAttributeComponent
import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.AEntity
import com.barelyconscious.systems.messaging.data.EmptyMessageData

class DeathComponentSystem(
    entitySystem: EntitySystem
) : AComponentSystem(entitySystem) {

    override fun onUpdate() {
        forEach(DamageComponent::class) { entity, hitpointsComponent ->

        }
    }
}

class TemporaryComponent(
    duration: Int,
    val components: List<IComponent>
) : IComponent {

    private var remainingTicks = duration

    fun update() {
        --remainingTicks
    }

    fun isExpired() = remainingTicks <= 0
}

/**
 * Component definition for applying a periodic effect over a duration every n ticks.
 */
class PeriodicApplicationComponent(
    duration: Int,
    val period: Int,
    val component: IComponent
) : IComponent {

    private val componentsApplied: ArrayList<IComponent> = arrayListOf()

    private var remainingTicks = duration

    fun update(entitySystem: EntitySystem, entity: AEntity) {
        --remainingTicks
        if (!isExpired() && remainingTicks % period == 0) {
            componentsApplied.add(component)
            entitySystem.addComponent(entity, component)
        }
    }

    fun isExpired() = remainingTicks <= 0

    fun onRemoved(entitySystem: EntitySystem, entity: AEntity) {
        componentsApplied.forEach {
            entitySystem.removeComponent(entity, it)
        }
    }
}

class PeriodicComponentSystem(
    entitySystem: EntitySystem,
    private val messageSystem: MessageSystem
) : AComponentSystem(entitySystem) {

    override fun onUpdate() {
        forEach(PeriodicApplicationComponent::class) { entity, component ->
            component.update(entitySystem, entity)

            if (component.isExpired()) {
                entitySystem.removeComponent(entity, component)
                component.onRemoved(entitySystem, entity)
            }
        }
    }
}

class TemporaryComponentSystem(
    entitySystem: EntitySystem,
    private val messageSystem: MessageSystem
) : AComponentSystem(entitySystem) {

    fun applyTemporaryComponent(entity: AEntity, temporaryComponent: TemporaryComponent) {
        entitySystem.addComponent(entity, temporaryComponent)

        temporaryComponent.components.forEach {
            entitySystem.addComponent(entity, it)
        }
    }

    override fun onUpdate() {
        // Get all components that have a temporary effect component and apply
        //  its behavior to that entity
        forEach(TemporaryComponent::class) { entity, component ->
            component.update()

            // todo: sample
            messageSystem.sendMessage("ENTITY_DAMAGED", EmptyMessageData(), this)

            if (component.isExpired()) {
                entitySystem.removeComponent(entity, component)
                component.components.forEach {
                    entitySystem.removeComponent(entity, it)
                }
            }
        }
    }
}
