package com.barelyconscious.services

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.Entity
import com.barelyconscious.systems.AComponentSystem
import com.barelyconscious.systems.EntityManager

class GameManager(
    private val entityManager: EntityManager,
    private val componentSystems: MutableList<AComponentSystem>
) {

    private val preUpdateCommands = mutableListOf<() -> Unit>()
    private val postUpdateCommands = mutableListOf<() -> Unit>()

    /**
     * Expected to be called for each update in the game, which will in turn notify
     * every component system to update itself, applying component behavior to the
     * entities.
     */
    fun step() {
        preUpdateCommands.forEach {
            it()
            preUpdateCommands.remove(it)
        }

        componentSystems.forEach {
            it.onUpdate()
        }

        postUpdateCommands.forEach {
            it()
            postUpdateCommands.remove(it)
        }
    }

    /**
     * Adds the entity to the game during the pre-update process.
     *
     * @param entity the entity to add
     */
    fun addEntity(entity: Entity) {
        preUpdateCommands.add {
            entityManager.addEntity(entity)
        }
    }

    /**
     * Adds the component to the entity during the pre-update process.
     *
     * @param entity the entity to which the component will be added
     * @param component the component to add
     */
    fun addComponent(entity: Entity, component: IComponent) {
        preUpdateCommands.add {
            entityManager.addComponent(entity, component)
        }
    }

    /**
     * Removes the entity from the game during the post-update process.
     *
     * @param entityToRemove the entity to remove
     */
    fun removeEntity(entityToRemove: Entity) {
        postUpdateCommands.add {
            entityManager.removeEntity(entityToRemove)
        }
    }

    /**
     * Removes the component from the entity during the post-update process.
     *
     * @param entity the entity from which the component will be removed
     * @param componentToRemove the component to be removed from the entity
     */
    fun removeComponent(entity: Entity, componentToRemove: IComponent) {
        postUpdateCommands.add {
            entityManager.removeComponent(entity, componentToRemove)
        }
    }
}
