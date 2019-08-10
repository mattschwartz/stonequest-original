package com.barelyconscious.services

import com.barelyconscious.services.messaging.logs.ConsoleLogService
import com.barelyconscious.services.messaging.MessageSystem
import java.time.Clock

class SystemsComposer {

    fun compose(): MessageSystem {
        val localClock = Clock.systemDefaultZone()
        val messageSystem = MessageSystem()

        messageSystem.subscribe(MessageSystem.ANY_EVENT_CODE, ConsoleLogService(localClock))

        return messageSystem
    }
}
