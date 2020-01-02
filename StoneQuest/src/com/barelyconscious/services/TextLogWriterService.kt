package com.barelyconscious.services

import com.barelyconscious.game.graphics.LineElement
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.systems.messaging.IMessageObserver
import com.barelyconscious.systems.messaging.data.IMessageData
import com.barelyconscious.systems.messaging.data.MessageResponse

data class TextLogMessageData(
    val text: String,
    val lineElements: MutableList<LineElement> = mutableListOf()
) : IMessageData {

    constructor(text: String) : this(text, mutableListOf())

    fun with(vararg lineElements: LineElement): TextLogMessageData {
        this.lineElements.addAll(lineElements)
        return this
    }
}

data class TextLogItemData(
    val item: Item
) : IMessageData

/**
 * Observer for writing messages to the in-game text log.
 */
class TextLogWriterService(
    private val textLog: TextLog
) : IMessageObserver {

    companion object {
        const val LOG_WRITE_TEXT = "stonequest/TextLogWriter/LOG_WRITE_TEXT"
        const val LOG_SCROLL_UP = "stonequest/TextLogWriter/LOG_SCROLL_UP"
        const val LOG_SCROLL_DOWN = "stonequest/TextLogWriter/LOG_SCROLL_DOWN"
    }

    override fun alert(
        eventCode: String,
        data: IMessageData,
        sender: Any
    ): MessageResponse {
        when (data) {
            is TextLogMessageData ->
                textLog.writeFormattedString(data.text, -1, *data.lineElements.toTypedArray())

            is TextLogItemData ->
                textLog.writeLootMessage(data.item)
        }

        return MessageResponse.ok()
    }
}
