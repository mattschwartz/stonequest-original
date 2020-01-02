package com.barelyconscious.systems

import com.barelyconscious.engine.GameManager
import com.barelyconscious.game.Screen
import com.barelyconscious.game.graphics.GameMap
import com.barelyconscious.game.input.KeyHandler
import com.barelyconscious.game.input.MouseHandler
import com.barelyconscious.game.menu.BuffBar
import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.game.player.Player
import com.barelyconscious.services.ConsoleLogService
import com.barelyconscious.services.InventoryService
import com.barelyconscious.services.SoundService
import com.barelyconscious.services.TextLogWriterService
import java.time.Clock
import javax.swing.JFrame
import kotlin.reflect.KClass

class SystemsComposer private constructor() {

    companion object {
        val INSTANCE = SystemsComposer()
    }

    private val dependencies: MutableMap<KClass<*>, Any> = mutableMapOf()
    private val dependencyTree: MutableMap<KClass<*>, () -> Any> = mutableMapOf()

    init {
        newDependency { Clock.systemDefaultZone() }
        newDependency { Screen(1280, 700) }
        newDependency { KeyHandler() }
        newDependency { MouseHandler(getDependency(MessageSystem::class)) }
        newDependency { providesWindowManager() }
        newDependency { GameManager(getDependency(Screen::class), getDependency(WindowManager::class)) }
        newDependency { SoundService() }
        newDependency { EntitySystem() }
        newDependency { providesMessageSystem() }
        newDependency { ConsoleLogService(getDependency(Clock::class)) }
        newDependency { TextLog(45, 50, 100) }
        newDependency { TextLogWriterService(getDependency(TextLog::class)) }
        newDependency { InventoryService(getDependency(MessageSystem::class), getDependency(EntitySystem::class)) }

        newDependency { Player(getDependency(MessageSystem::class)) }
        newDependency { GameMap(1024, 1024, 1280, 700) }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getDependency(type: KClass<*>): T where T : Any {
        require(dependencyTree.containsKey(type)) {
            "No such dependency found for type $type"
        }

        if (!dependencies.containsKey(type)) {
            dependencies[type] = dependencyTree[type]!!()
        }

        return dependencies[type] as T
    }

    private inline fun <reified T> newDependency(
        noinline providesDependency: () -> T
    ) where T : Any {
        dependencyTree[T::class] = providesDependency
    }

    private fun providesWindowManager(): WindowManager {
        val result = WindowManager(
            getDependency(KeyHandler::class),
            getDependency(MouseHandler::class),
            JFrame(),
            getDependency(MessageSystem::class))

        result.setView(getDependency(Screen::class))

        return result
    }

    private fun providesMessageSystem(): MessageSystem {
        val messageSystem = MessageSystem()
        val soundService: SoundService = getDependency(SoundService::class)
        val consoleLogService: ConsoleLogService = getDependency(ConsoleLogService::class)
        val textLogWriterService: TextLogWriterService = getDependency(TextLogWriterService::class)
        val inventoryService: InventoryService = getDependency(InventoryService::class)

        messageSystem.subscribe(
            MessageSystem.ANY_EVENT_CODE,
            consoleLogService)

        messageSystem.subscribe(
            TextLogWriterService.LOG_WRITE_TEXT,
            textLogWriterService)

        messageSystem.subscribe(
            SoundService.PLAY_SOUND,
            soundService)

        subscribeInventoryService(
            messageSystem,
            inventoryService)

        return messageSystem
    }

    private fun subscribeInventoryService(
        messageSystem: MessageSystem,
        inventoryService: InventoryService
    ) {
        messageSystem.subscribe(
            InventoryService.ADD_ITEM,
            inventoryService)
        messageSystem.subscribe(
            InventoryService.REMOVE_ITEM,
            inventoryService)
        messageSystem.subscribe(
            InventoryService.USE_ITEM,
            inventoryService)
        messageSystem.subscribe(
            InventoryService.GET_ITEM_DETAILS,
            inventoryService)
    }
}
