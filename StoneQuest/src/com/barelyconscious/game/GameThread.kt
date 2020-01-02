package com.barelyconscious.game

import com.barelyconscious.engine.GameManager
import com.barelyconscious.engine.PerformanceData
import com.barelyconscious.engine.Stopwatch
import com.barelyconscious.engine.TickArgs
import java.time.Clock

class GameThread(
    private val gameManager: GameManager,
    private val clock: Clock
) : Runnable {

    private var isRunning: Boolean = false
    private var thread: Thread? = null
    private val fps = Stopwatch(clock)

    override fun run() {
        var currentFrames = 0
        var lastFrames = 0
        var lastTime = clock.millis()

        var lastTicks = clock.millis()

        while (isRunning) {
            try {
                Thread.sleep(20)
            } catch (e: InterruptedException) {
                return
            }

            ++currentFrames

            val currTicks = clock.millis()
            val tickArgs = TickArgs(
                deltaTime = (currTicks - lastTicks).toDouble(),
                performanceData = PerformanceData(lastFrames)
            )
            lastTicks = currTicks

            fps.start()
            gameManager.update(tickArgs)
            fps.stop()

            // calculate FPS
            if (clock.millis() - lastTime > 1000) {
                lastTime += 1000
                lastFrames = currentFrames
                currentFrames = 0
            }
        }
    }

    fun start(): Thread {
        if (thread != null && thread!!.isAlive) {
            return thread!!
        }

        thread = Thread(this)
        thread!!.start()

        isRunning = true
        return thread!!
    }

    fun stop() {
        if (!isRunning || thread == null || !thread!!.isAlive) return

        isRunning = false

        thread!!.interrupt()
        thread!!.join()
    }
}