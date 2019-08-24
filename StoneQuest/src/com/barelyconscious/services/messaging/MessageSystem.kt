package com.barelyconscious.services.messaging

import com.barelyconscious.services.messaging.data.IMessageData
import com.barelyconscious.services.messaging.data.MessageResponse

class MessageSystem {

    companion object {
        /**
         * A special eventCode which is interpreted as "any"
         */
        public const val ANY_EVENT_CODE: String = "*"
    }

    /**
     * key: string - the event code to which one or more observers has subscribed
     * value: List<IMessageObserver> - all observers for this event code
     */
    private val observers: MutableMap<String, MutableList<IMessageObserver>> = mutableMapOf()

    fun subscribe(eventCode: String, receiver: IMessageObserver) {
        if (!observers.containsKey(eventCode)) {
            observers[eventCode] = arrayListOf(receiver)
        } else {
            observers[eventCode]!!.add(receiver)
        }
    }

    // Send the message asynchronously
    fun sendMessage(eventCode: String, data: IMessageData, sender: Any) {
        observers[eventCode]
            ?.forEach { it.alert(eventCode, data, sender) }
    }

    /**
     * Sends a message to the addressee, but only if they are of type exceptForType. This is a way
     * to blacklist recipients who are listening in on that eventCode at opportune moments. For instance,
     * if you would like a normally operating SoundPlayer observer to appear "muted" to the player.
     *
     * @param msg the message to send to the recipients
     * @param exceptForType the type of observers normally listening into the eventCode which
     * will be deaf to the message sent
     */
    fun sendMessage(
        eventCode: String,
        data: IMessageData,
        sender: Any,
        exceptForType: Class<IMessageObserver>
    ) {
        observers[eventCode]
            ?.filter { it::class != exceptForType }
            ?.forEach { it.alert(eventCode, data, sender) }
    }

    /**
     * Send the message immediately to the specified type of observer, whose
     * responses will be collected and returned.
     *
     * @param msg the message to send
     * @param receiverType the type of observer
     * @return the list of responses from each observer of the specified type
     */
    fun sendMessageImmediate(
        eventCode: String,
        data: IMessageData,
        sender: Any
    ): List<MessageResponse> =
        observers[eventCode]
            ?.map { it.alert(eventCode, data, sender) }
            ?.toList()
            ?: listOf()
}
