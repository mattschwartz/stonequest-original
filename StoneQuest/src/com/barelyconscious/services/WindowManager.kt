package com.barelyconscious.services

import com.barelyconscious.game.Common
import com.barelyconscious.game.Screen
import com.barelyconscious.game.graphics.LineElement
import com.barelyconscious.game.input.Interactable
import com.barelyconscious.game.input.KeyHandler
import com.barelyconscious.game.input.MouseHandler
import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.gui.IWidget
import java.awt.Dimension
import javax.swing.JFrame

const val GAME_TITLE: String = "StoneQuest"
const val GAME_VERSION: String = "0.6.9"

class WindowManager(
    private val keyHandler: KeyHandler,
    private val mouseHandler: MouseHandler,
    private val window: JFrame
) {

    private val widgets: MutableList<IWidget> = mutableListOf()

    init {
        window.title = GAME_TITLE
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        window.isResizable = false
        window.isVisible = true
        window.focusTraversalKeysEnabled = false
//        window.setUndecorated(true); // Makes the frame go bye-bye

        window.addKeyListener(keyHandler)
    }

    fun addWidget(widget: IWidget) = widgets.add(widget)

    fun removeWidget(widget: IWidget) = widgets.remove(widget)

    fun resize(width: Int, height: Int) {
        window.minimumSize = Dimension(width, height)
//        screen.resizeScreen(width, height)

        widgets.forEach { it.resize(width, height) }

        window.pack()
        window.setLocationRelativeTo(null)
    }

    fun setView(screen: Screen) {
//        window.remove(screen) todo
        window.add(screen)
    }

    fun update() {
        if (!window.hasFocus()) {
            window.requestFocus()
        }
    }

    // todo turn into observer or pubsub
    fun writeWelcomeMessage() {
        val textLog = widgets.first { it is TextLog } as TextLog

        textLog.writeFormattedString("Welcome to " + GAME_TITLE + " v"
            + GAME_VERSION + "!", Common.FONT_NULL_RGB, LineElement(
            "$GAME_TITLE v$GAME_VERSION", true,
            Common.themeForegroundColor))

        textLog.writeFormattedString("Press ? for help and instructions.",
            Common.FONT_NULL_RGB)
    }

    // todo probably move these elsewhere?
    fun registerClickableAreas() {
        widgets.forEach {
            if (it is Interactable) {
                mouseHandler.registerClickableListener(it)
            }
        }
    }

    fun registerHoverableAreas() {
        widgets.forEach {
            if (it is Interactable) {
                mouseHandler.registerHoverableListener(it)
            }
        }
    }

    fun registerKeyListeners() {
        widgets.forEach {
            if (it is Interactable) {
                keyHandler.registerKeyInputListener(it)
            }
        }
    }
}
