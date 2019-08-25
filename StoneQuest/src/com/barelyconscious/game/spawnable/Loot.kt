package com.barelyconscious.game.spawnable

import com.barelyconscious.game.Game
import com.barelyconscious.game.Sound
import com.barelyconscious.game.graphics.LineElement
import com.barelyconscious.game.item.Item
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.logs.TextLogItemData
import com.barelyconscious.services.messaging.logs.TextLogMessageData
import com.barelyconscious.services.messaging.logs.TextLogWriterService

class Loot(
    val item: Item,
    val x: Int,
    val y: Int,
    private val messageSystem: MessageSystem
) : Sprite(item.displayName, item.tileId) {

    init {
        setPosition(x, y)
    }

    var removableOnWalkover: Boolean = false

    override fun onWalkOver() {
        val article = when (displayName[0]) {
            'a', 'e', 'i', 'o', 'u' -> "an"
            else -> "a"
        }

        messageSystem.sendMessage(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogMessageData("There is $article $displayName here.")
                .with(LineElement(displayName, true, item.rarityColorRgb)),
            this
        )
    }

    override fun interact() {
        Game.inventory.addItem(item)
        messageSystem.sendMessage(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogItemData(item),
            this
        )
        Sound.LOOT_COINS.play()
        remove()
    }

    override fun toString(): String = item.displayName
}