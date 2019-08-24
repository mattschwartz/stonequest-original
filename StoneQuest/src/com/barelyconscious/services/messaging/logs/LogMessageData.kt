package com.barelyconscious.services.messaging.logs

import com.barelyconscious.services.messaging.data.IMessageData

data class LogMessageData(
    val logLevel: LogLevel,
    val message: String
) : IMessageData
