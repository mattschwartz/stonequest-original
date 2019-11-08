package com.barelyconscious.game.item.definitions

import com.barelyconscious.game.Common
import com.barelyconscious.game.Game
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.OptionsKey
import com.barelyconscious.game.player.AttributeMod
import com.barelyconscious.services.SoundService
import com.barelyconscious.systems.audio.PlayableSound
import com.barelyconscious.systems.MessageSystem
import com.barelyconscious.systems.messaging.data.SoundMessageData
import com.barelyconscious.services.TextLogMessageData
import com.barelyconscious.services.TextLogWriterService
import kotlin.math.abs

/**
 * Creates a new Scroll with the following parameters
 *
 * @param scrollName the displayName of the scroll which is only visible to the player if the Scroll has been read
 * previously
 * @param sellV the value in gold vendors will give in exchange for the Scroll
 * @param scrollid the internal id of the Scroll, which is unique to each Scroll. The player holds an array of
 * Scroll ids in order to keep track of Scrolls the player has previously read before to determine if the Scroll's
 * displayName and stats should be obfuscated or not
 * @param effects AttributeMod effects if any; most Scrolls do not provide attribute mods when consumed
 */
class Scroll(
    val scrollName: String,
    sellValue: Int,
    val scrollId: Int,
    private val messageSystem: MessageSystem,
    itemAffixes: ArrayList<AttributeMod>
) : Item(
    scrollName,
    1,
    1,
    "Read it for to gain some knowledges.",
    itemAffixes,
    sellValue,
    1,
    Tile.SCROLL_TILE_ID
) {

    private val isIdentified: Boolean = !Game.player.isScrollIdentified(scrollId)

    init {
        if (isIdentified) {
            super.displayName = obfuscateName()
        }

        optionsDescriptions[OptionsKey.USE] = "read"
    }

    override fun onUse() {
        displayName = scrollName
        Game.player.addScrollToIdentifieds(scrollId)

        messageSystem.sendMessage(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogMessageData("The scroll crumbles to dust..."),
            this
        )

        extraEffects()
        identifyScroll()
        messageSystem.sendMessage(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogMessageData("It was a $scrollName!"),
            this)
        messageSystem.sendMessage(
            SoundService.PLAY_SOUND,
            SoundMessageData(PlayableSound.READ_SCROLL),
            this)

        // todo handle elsewhere
        if (--stackSize <= 0) {
            Game.inventory.removeItem(this)
        }
    }

    fun identifyScroll() =
        Game.player.addScrollToIdentifieds(scrollId)

    fun extraEffects() {
        // ...
    }

    override var displayName: String
        get() = if (Game.player.isScrollIdentified(scrollId)) {
            "Scroll of $scrollName"
        } else {
            "Scroll entitled ${super.displayName}"
        }
        set(value) {
            super.displayName = value
        }

    override val itemDescription: String
        get() = if (Game.player.isScrollIdentified(scrollId)) {
            "Read to cast this scroll"
        } else {
            "You do not yet know what this scroll does. Read it or use a Scroll of Identify to learn its properties."
        }



    /* Picks 2-4 gibberish words based on the displayName of the scroll */
    private fun obfuscateName(): String {
        var str = ""
        val numOfWords: Int
        val hash = abs(scrollName.hashCode())

        // Get the displayName's hashcode, % last digit & positive % max 3 + 2
        numOfWords = (hash % 10 and 15) % 4 + 2
        // Create numOfWords number of unintelligible words
        for (i in 0 until numOfWords) {
            str += Common.GIBBERISH_WORD_LIST[(hash + i * 2586) % 1000].toString() + " "
        } // for

        str = str.trim { it <= ' ' }

        return str
    } // obfuscateName
}
