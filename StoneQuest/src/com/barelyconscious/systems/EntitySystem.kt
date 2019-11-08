package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.AEntity
import kotlin.reflect.KClass

class EntitySystem {

    private val entities: MutableList<AEntity> = mutableListOf()

    fun getEntityList(): List<AEntity> {
        return entities.toList()
    }

    fun addEntity(AEntity: AEntity) {
        entities.add(AEntity)
    }

    /**
     * Adds the component to the entity. Will also add the entity to the list of
     * known entities if it does not already exist.
     *
     * @param entity the entity to which the component will be added
     * @param component the component to add
     */
    fun addComponent(entity: AEntity, component: IComponent) {
        val match: AEntity? = entities.find { it == entity }

        if (match == null) {
            entity.components.add(component)
            entities.add(entity)
        } else {
            match.components.add(component)
        }
    }

    fun removeEntity(entity: AEntity) {
        entities.remove(entity)
    }

    fun removeComponent(entity: AEntity, componentToRemove: IComponent) {
        val match: AEntity = entities.find { it == entity }
            ?: return

        match.components.remove(componentToRemove)
    }

    @Suppress("UNCHECKED_CAST")
    fun <TComponent : IComponent> getSingleComponent(
        entity: AEntity,
        componentType: KClass<TComponent>
    ): TComponent {
        val matchedComponents = entity.components.filter { it::class == componentType }

        if (matchedComponents.size != 1) {
            // todo get better exception
            throw Exception()
        }

        return matchedComponents.single() as TComponent
    }
}
