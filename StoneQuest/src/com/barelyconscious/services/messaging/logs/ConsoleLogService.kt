package com.barelyconscious.services.messaging.logs

import com.barelyconscious.services.messaging.IMessageObserver
import com.barelyconscious.services.messaging.data.IMessageData
import com.barelyconscious.services.messaging.data.MessageResponse
import java.time.Clock

class ConsoleLogService(private val clock: Clock) : IMessageObserver {

    override fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse {
        val level: LogLevel
        val message: String

        if (data is LogMessageData) {
            message = data.message
            level = data.logLevel
        } else {
            level = LogLevel.INFO
            message = data.toString()
        }

        write("$sender says: $message", level)

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
