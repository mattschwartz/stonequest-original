package com.barelyconscious.systems

import com.barelyconscious.components.AComponent
import com.barelyconscious.entities.AEntity
import kotlin.reflect.KClass

class EntityManager {

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
     * @param AEntity the entity to which the component will be added
     * @param component the component to add
     */
    fun addComponent(AEntity: AEntity, component: AComponent) {
        val match: AEntity? = entities.find { it == AEntity }

        if (match == null) {
            AEntity.components.add(component)
            entities.add(AEntity)
        } else {
            match.components.add(component)
        }
    }

    fun removeEntity(AEntityToRemove: AEntity) {
        entities.remove(AEntityToRemove)
    }

    fun removeComponent(AEntity: AEntity, componentToRemove: AComponent) {
        val match: AEntity = entities.find { it == AEntity }
            ?: return

        match.components.remove(componentToRemove)
    }

    @Suppress("UNCHECKED_CAST")
    fun <TComponent : AComponent> getSingleComponent(
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
