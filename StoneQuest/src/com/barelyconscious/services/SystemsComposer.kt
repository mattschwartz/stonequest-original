package com.barelyconscious.services

import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.services.messaging.logs.ConsoleLogService
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.logs.TextLogWriterService
import java.time.Clock

class SystemsComposer {

    val textLog = TextLog(45, 50, 100)

    fun compose(): MessageSystem {
        val localClock = Clock.systemDefaultZone()
        val messageSystem = MessageSystem()

        messageSystem.subscribe(MessageSystem.ANY_EVENT_CODE, ConsoleLogService(localClock))
        messageSystem.subscribe(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogWriterService(textLog)
        )
        messageSystem.subscribe(
            SoundService.PLAY_SOUND,
            SoundService())

        return messageSystem
    }
}
