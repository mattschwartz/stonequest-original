package com.barelyconscious.game.item

import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.player.AttributeMod

enum class OptionsKey {
    USE,
    EXAMINE,
    DROP,
    SALVAGE,
}

open class Item(
    displayName: String,
    val itemLevel: Int,
    var rarityColorRgb: Int,
    open val itemDescription: String,

    protected open val itemAffixes: ArrayList<AttributeMod>,

    // todo these should be managed elsewise:
    val sellValue: Int,
    var stackSize: Int,
    val tileId: Int
) {

    constructor(other: Item) : this(
        other.displayName,
        other.itemLevel,
        other.rarityColorRgb,
        other.itemDescription,
        other.itemAffixes,
        other.sellValue,
        other.stackSize,
        other.tileId
    )

    open var displayName: String = displayName
        protected set

    companion object {
        fun createGoldItem(goldAmount: Int) = Item(
            "gold",
            0,
            -1,
            "Glittery golden gold!",
            arrayListOf(),
            0,
            goldAmount,
            if (goldAmount == 1) {
                Tile.GOLD_LOOT_SINGLE_TILE_ID
            } else {
                Tile.GOLD_LOOT_STACK_TILE_ID
            }
        )
    }

    protected val optionsDescriptions: MutableMap<OptionsKey, String> = mutableMapOf(
        Pair(OptionsKey.USE, "use"),
        Pair(OptionsKey.EXAMINE, "examine"),
        Pair(OptionsKey.DROP, "drop"),
        Pair(OptionsKey.SALVAGE, "salvage")
    )

    fun getItemAffixes(): List<AttributeMod> =
        itemAffixes.toList()

    fun adjustStackBy(amount: Int) {
        stackSize += amount
    }

    /**
     * Returns the list of option strings for interacting with this item.
     */
    fun getOptions() = listOf(
        optionsDescriptions[OptionsKey.USE],
        optionsDescriptions[OptionsKey.EXAMINE],
        optionsDescriptions[OptionsKey.DROP],
        optionsDescriptions[OptionsKey.SALVAGE]
    )

    open fun onUse() {
        System.err.println("Ain't no use, chief.")
    }
}


