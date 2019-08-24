package com.barelyconscious.services.messaging.logs

import com.barelyconscious.game.graphics.LineElement
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.services.messaging.IMessageObserver
import com.barelyconscious.services.messaging.data.IMessageData
import com.barelyconscious.services.messaging.data.MessageResponse

data class TextLogMessageData(
    val text: String,
    val lineElements: MutableList<LineElement> = mutableListOf()
) : IMessageData {

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
) : IMessageObserver() {

    companion object {
        const val LOG_EVENT_CODE = "stonequest/TextLogWriter/LOG_EVENT_CODE"
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
