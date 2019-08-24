package com.barelyconscious.services.messaging

import com.barelyconscious.services.messaging.data.IMessageData

abstract class AMessenger(
    private val messageSystem: MessageSystem
) {

    fun sendMessage(eventCode: String, data: IMessageData, sender: Any) =
        messageSystem.sendMessage(eventCode, data, sender)
}
