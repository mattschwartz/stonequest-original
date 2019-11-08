package com.barelyconscious.systems

import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.services.InventoryService
import com.barelyconscious.services.SoundService
import com.barelyconscious.services.ConsoleLogService
import com.barelyconscious.services.TextLogWriterService
import java.time.Clock

class SystemsComposer {

    val textLog = TextLog(45, 50, 100)

    fun compose(): MessageSystem {
        val localClock = Clock.systemDefaultZone()
        val messageSystem = MessageSystem()
        val entitySystem = EntitySystem()

        messageSystem.subscribe(MessageSystem.ANY_EVENT_CODE, ConsoleLogService(localClock))
        messageSystem.subscribe(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogWriterService(textLog))

        messageSystem.subscribe(
            SoundService.PLAY_SOUND,
            SoundService())

        subscribeInventoryService(
            messageSystem,
            InventoryService(messageSystem, entitySystem))

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
