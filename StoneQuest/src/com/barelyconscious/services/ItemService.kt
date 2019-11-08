package com.barelyconscious.services

import com.barelyconscious.systems.messaging.IMessageObserver
import com.barelyconscious.systems.messaging.data.IMessageData
import com.barelyconscious.systems.messaging.data.MessageResponse

class ItemService : IMessageObserver {

    override fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse {
        return MessageResponse.ok()
    }
}
