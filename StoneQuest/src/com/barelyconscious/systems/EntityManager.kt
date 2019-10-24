package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.AEntity

class EntityManager {

    private val AEntities: MutableList<AEntity> = mutableListOf()

    fun getEntityList(): List<AEntity> {
        return AEntities.toList()
    }

    fun addEntity(AEntity: AEntity) {
        AEntities.add(AEntity)
    }

    /**
     * Adds the component to the entity. Will also add the entity to the list of
     * known entities if it does not already exist.
     *
     * @param AEntity the entity to which the component will be added
     * @param component the component to add
     */
    fun addComponent(AEntity: AEntity, component: IComponent) {
        val match: AEntity? = AEntities.find { it == AEntity }

        if (match == null) {
            AEntity.components.add(component)
            AEntities.add(AEntity)
        } else {
            match.components.add(component)
        }
    }

    fun removeEntity(AEntityToRemove: AEntity) {
        AEntities.remove(AEntityToRemove)
    }

    fun removeComponent(AEntity: AEntity, componentToRemove: IComponent) {
        val match: AEntity = AEntities.find { it == AEntity }
            ?: return

        match.components.remove(componentToRemove)
    }
}
