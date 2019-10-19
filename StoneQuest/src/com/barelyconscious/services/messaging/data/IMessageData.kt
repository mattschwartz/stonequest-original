package com.barelyconscious.services.messaging.data

interface IMessageData

class EmptyMessageData : IMessageData {

    companion object {
        val instance = EmptyMessageData()
    }
}
