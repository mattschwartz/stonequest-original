package com.barelyconscious.systems.messaging

import com.barelyconscious.systems.MessageSystem
import com.barelyconscious.systems.messaging.data.IMessageData

abstract class AMessenger(
    private val messageSystem: MessageSystem
) {

    fun sendMessage(eventCode: String, data: IMessageData, sender: Any) =
        messageSystem.sendMessage(eventCode, data, sender)
}
