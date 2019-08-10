package com.barelyconscious.services.messaging

abstract class AMessenger(
    private val messageSystem: MessageSystem
) {

    fun sendMessage(msg: Message) =
        messageSystem.sendMessage(msg)
}
