package com.barelyconscious.engine

import com.barelyconscious.game.Screen
import com.barelyconscious.systems.WindowManager

class GameManager(
    private val screen: Screen,
    private val windowManager: WindowManager
) {

    fun update(tickArgs: TickArgs) {
        screen.clear()
        screen.render()
        windowManager.update()
    }
}
