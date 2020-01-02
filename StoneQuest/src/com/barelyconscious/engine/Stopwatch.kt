package com.barelyconscious.engine

import java.time.Clock

class Stopwatch(private val clock: Clock) {

    private var isRunning = false
    private var startTime = 0L
    private var endTime = 0L

    fun start() {
        isRunning = true

        startTime = clock.millis()
        endTime = 0L
    }

    fun stop() {
        isRunning = false
        endTime = clock.millis()
    }

    fun elapsed(): Long = if (isRunning) {
        clock.millis() - startTime
    } else {
        endTime - startTime
    }
}