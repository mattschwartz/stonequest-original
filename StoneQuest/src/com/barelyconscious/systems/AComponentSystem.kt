package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.Entity

abstract class AComponentSystem(protected val entityManager: EntityManager) {

    abstract fun onUpdate()

    @Suppress("UNCHECKED_CAST")
    protected fun <TComponent : IComponent> forEach(
        componentType: Class<TComponent>,
        action: (Entity, TComponent) -> Unit
    ) {
        entityManager.getEntityList().forEach { entity ->
            entity.components
                .filter { it::class == componentType }
                .forEach { component ->
                    if (component as? TComponent != null) {
                        action(entity, component)
                    }
                }
        }
    }
}
