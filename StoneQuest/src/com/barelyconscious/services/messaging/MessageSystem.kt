package com.barelyconscious.services.messaging

class MessageSystem {

    companion object {
        /**
         * A special eventCode which is interpreted as "any"
         */
        public const val ANY_EVENT_CODE: String = "*"
    }

    /**
     * key: string - the event code to which one or more observers has subscribed
     * value: List<MessageObserver> - all observers for this event code
     */
    private val observers: MutableMap<String, MutableList<MessageObserver>> = mutableMapOf()

    fun subscribe(eventCode: String, receiver: MessageObserver) {
        if (!observers.containsKey(eventCode)) {
            observers[eventCode] = arrayListOf(receiver)
        } else {
            observers[eventCode]!!.add(receiver)
        }
    }

    // Send the message asynchronously
    fun sendMessage(msg: Message) {
        observers[msg.eventCode]!!.forEach { it.alert(msg) }

        observers[ANY_EVENT_CODE]!!.forEach { it.alert(msg) }
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
    fun sendMessage(msg: Message, exceptForType: Class<MessageObserver>) {
        observers[msg.eventCode]
            ?.filter { it::class == exceptForType }
            ?.forEach { it.alert(msg) }
        observers[ANY_EVENT_CODE]
            ?.filter { it::class == exceptForType }
            ?.forEach { it.alert(msg) }


        // todo rethink this whatsense
//        throw NotImplementedError()
        // todo let's determine how often this method is used. if it's often enough,
        //  consider changing underlying data type to list, instead of event-code based
        //  then the observers would each need to be able to understand how to accept
        //  their messages. so doing it in this means decouples them when they don't care
        //  hmm well
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
        msg: Message
    ): List<MessageResponse> {
        val receivers: MutableList<MessageObserver> = observers[msg.eventCode]
            ?: return mutableListOf()
        receivers.addAll(observers[ANY_EVENT_CODE] ?: listOf())

        return receivers
            .map { it.alert(msg) }
            .toList()
    }
}
