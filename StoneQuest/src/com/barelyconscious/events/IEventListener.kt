package com.barelyconscious.events

interface IEventListener {

    fun <T> notify(eventRequest: EventRequest<T>): EventResponse
}
