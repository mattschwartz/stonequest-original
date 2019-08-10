package com.barelyconscious.services.messaging.logs

import com.barelyconscious.services.messaging.Message
import com.barelyconscious.services.messaging.MessageObserver
import com.barelyconscious.services.messaging.MessageResponse
import java.time.Clock


class ConsoleLogService(private val clock: Clock) : MessageObserver() {

    override fun alert(msg: Message): MessageResponse {
        val data: ConsoleMessageData? = msg.data as? ConsoleMessageData

        if (data != null) {
            write("[${msg.messenger}]: ${data.message}", data.level)
        }

        return MessageResponse.ok()
    }

    private fun write(message: String, level: LogLevel) {
        when (level) {
            LogLevel.ERROR ->
                System.err.println("[${clock.instant()}] [ERROR] $message")
            else ->
                println("[${clock.instant()}] [${level.name}] $message")
        }
    }
}
