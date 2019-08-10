package com.barelyconscious.events

class EventRequest<TData>(
    val eventName: String,
    val data: TData
)
