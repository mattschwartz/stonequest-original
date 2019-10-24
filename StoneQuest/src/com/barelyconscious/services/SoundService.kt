package com.barelyconscious.services

import com.barelyconscious.services.messaging.IMessageObserver
import com.barelyconscious.services.messaging.data.IMessageData
import com.barelyconscious.services.messaging.data.MessageResponse
import com.barelyconscious.services.messaging.data.SoundMessageData

class SoundService : IMessageObserver {

    companion object {
        const val PLAY_SOUND = "stonequest/SoundService/PLAY_SOUND"
    }

    override fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse {
        return if (data is SoundMessageData) {
            data.sound.play()
            MessageResponse.ok("Playing sound ${data.sound}")
        } else {
            MessageResponse.failed(
                "Data is of type ${data::class.simpleName}, but expected ${SoundMessageData::class.simpleName}"
            )
        }
    }
}
