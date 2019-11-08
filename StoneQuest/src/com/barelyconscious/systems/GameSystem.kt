package com.barelyconscious.systems

import com.barelyconscious.components.IComponent
import com.barelyconscious.entities.AEntity

class GameSystem(
    private val entitySystem: EntitySystem,
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
     * @param AEntity the entity to add
     */
    fun addEntity(AEntity: AEntity) {
        preUpdateCommands.add {
            entitySystem.addEntity(AEntity)
        }
    }

    /**
     * Adds the component to the entity during the pre-update process.
     *
     * @param AEntity the entity to which the component will be added
     * @param component the component to add
     */
    fun addComponent(AEntity: AEntity, component: IComponent) {
        preUpdateCommands.add {
            entitySystem.addComponent(AEntity, component)
        }
    }

    /**
     * Removes the entity from the game during the post-update process.
     *
     * @param AEntityToRemove the entity to remove
     */
    fun removeEntity(AEntityToRemove: AEntity) {
        postUpdateCommands.add {
            entitySystem.removeEntity(AEntityToRemove)
        }
    }

    /**
     * Removes the component from the entity during the post-update process.
     *
     * @param AEntity the entity from which the component will be removed
     * @param componentToRemove the component to be removed from the entity
     */
    fun removeComponent(AEntity: AEntity, componentToRemove: IComponent) {
        postUpdateCommands.add {
            entitySystem.removeComponent(AEntity, componentToRemove)
        }
    }
}
