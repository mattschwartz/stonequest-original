package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.Entity

class EntityManager {

    private val entities: MutableList<Entity> = mutableListOf()

    fun getEntityList(): List<Entity> {
        return entities.toList()
    }

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }

    /**
     * Adds the component to the entity. Will also add the entity to the list of
     * known entities if it does not already exist.
     *
     * @param entity the entity to which the component will be added
     * @param component the component to add
     */
    fun addComponent(entity: Entity, component: IComponent) {
        val match: Entity? = entities.find { it == entity }

        if (match == null) {
            entity.components.add(component)
            entities.add(entity)
        } else {
            match.components.add(component)
        }
    }

    fun removeEntity(entityToRemove: Entity) {
        entities.remove(entityToRemove)
    }

    fun removeComponent(entity: Entity, componentToRemove: IComponent) {
        val match: Entity = entities.find { it == entity }
            ?: return

        match.components.remove(componentToRemove)
    }
}
