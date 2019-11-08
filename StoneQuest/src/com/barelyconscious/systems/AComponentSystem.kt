package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.AEntity
import kotlin.reflect.KClass

abstract class AComponentSystem(protected val entitySystem: EntitySystem) {

    abstract fun onUpdate()

    @Suppress("UNCHECKED_CAST")
    protected fun <TComponent : IComponent> forEach(
        componentType: KClass<TComponent>,
        action: (AEntity, TComponent) -> Unit
    ) {
        entitySystem.getEntityList().forEach { entity ->
            entity.components
                .filter { it::class == componentType }
                .forEach { component ->
                    if (component as? TComponent != null) {
                        action(entity, component)
                    }
                }
        }
    }

    protected fun <TComponent : IComponent> forAll(
        componentType: KClass<TComponent>,
        action: (AEntity) -> Unit
    ) {
        entitySystem.getEntityList().forEach { entity ->
            val hasComponent = entity.components.any { it::class == componentType }
            if (hasComponent) {
                action(entity)
            }
        }
    }
}
