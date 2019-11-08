package com.barelyconscious.systems.messaging.data

import com.barelyconscious.systems.logging.LogLevel

data class LogMessageData(
    val logLevel: LogLevel,
    val message: String
) : IMessageData
