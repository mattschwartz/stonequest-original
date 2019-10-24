package com.barelyconscious.services.messaging

import com.barelyconscious.services.messaging.data.IMessageData
import com.barelyconscious.services.messaging.data.MessageResponse

interface IMessageObserver {

    fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse
}
