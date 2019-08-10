package com.barelyconscious.services.messaging

data class Message(
    val eventCode: String,
    val data: Any?,
    val messenger: Any
)
