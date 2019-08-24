package com.barelyconscious.services

import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.services.messaging.logs.ConsoleLogService
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.logs.TextLogWriterService
import java.time.Clock

class SystemsComposer {

    fun compose(): MessageSystem {
        val localClock = Clock.systemDefaultZone()
        val messageSystem = MessageSystem()

        val textLog = TextLog(-1, -1, -1)

        messageSystem.subscribe(MessageSystem.ANY_EVENT_CODE, ConsoleLogService(localClock))
        messageSystem.subscribe(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogWriterService(textLog)
        )

        return messageSystem
    }
}
