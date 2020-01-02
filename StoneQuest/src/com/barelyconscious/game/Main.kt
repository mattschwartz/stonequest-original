package com.barelyconscious.game

import com.barelyconscious.engine.GameManager
import com.barelyconscious.systems.SystemsComposer
import java.time.Clock

fun main() {
    val composer = SystemsComposer.INSTANCE

    val gameThread = GameThread(
        composer.getDependency(GameManager::class),
        composer.getDependency(Clock::class))

    gameThread.start()

    println("Stopping.")
}
