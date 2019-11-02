package com.barelyconscious.services

import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.logs.ConsoleLogService
import com.barelyconscious.services.messaging.logs.TextLogWriterService
import com.barelyconscious.systems.EntityManager
import java.time.Clock

class SystemsComposer {

    val textLog = TextLog(45, 50, 100)

    fun compose(): MessageSystem {
        val localClock = Clock.systemDefaultZone()
        val messageSystem = MessageSystem()
        val entityManager = EntityManager()

        messageSystem.subscribe(MessageSystem.ANY_EVENT_CODE, ConsoleLogService(localClock))
        messageSystem.subscribe(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogWriterService(textLog))

        messageSystem.subscribe(
            SoundService.PLAY_SOUND,
            SoundService())

        subscribeInventoryService(
            messageSystem,
            InventoryService(entityManager))

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
