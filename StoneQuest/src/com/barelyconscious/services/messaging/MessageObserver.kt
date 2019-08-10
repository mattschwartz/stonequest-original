package com.barelyconscious.services.messaging

abstract class MessageObserver {

    abstract fun alert(msg: Message): MessageResponse

    // can't imagine this actually ever being used, right? but maybe??
    // idea is message may fail to be processed for SOME reason
    //  and when that happens, we alert those who care that
    //  a message failed using this method
    fun alertFailed(msg: Message): MessageResponse = MessageResponse.ok()
}
