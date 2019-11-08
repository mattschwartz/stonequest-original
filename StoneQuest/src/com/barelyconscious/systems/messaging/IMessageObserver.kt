package com.barelyconscious.systems.messaging

import com.barelyconscious.systems.messaging.data.IMessageData
import com.barelyconscious.systems.messaging.data.MessageResponse

interface IMessageObserver {

    fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse
}
