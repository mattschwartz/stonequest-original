package com.barelyconscious.events

class EventHandler {

    private val eventListenersByIEvent: MutableMap<String, ArrayList<IEventListener>> = HashMap()

    /**
     * Add an event listener for the specified eventName.
     *
     * @param eventName the displayName of the event to start listening to
     * @param listener the [IEventListener] which will be notified of events for the eventName
     */
    fun attachEventListener(eventName: String, listener: IEventListener) {
        if (!eventListenersByIEvent.containsKey(eventName)) {
            eventListenersByIEvent[eventName] = ArrayList()
        }

        eventListenersByIEvent[eventName]!!.add(listener)
    }

    /**
     * Removes the specified listenerI from events under eventName
     */
    fun removeEventListener(eventName: String, listenerI: IEventListener) {
        eventListenersByIEvent[eventName]?.remove(listenerI)
    }

    /**
     * Sends a message to all listeners of the eventName
     */
    fun <T> sendMessage(eventRequest: EventRequest<T>): List<EventResponse> {
        val eventName = eventRequest.eventName
        val listeners = eventListenersByIEvent[eventName]
            ?: return listOf()

        return listeners.map {
            it.notify(eventRequest)
        }
    }
}
