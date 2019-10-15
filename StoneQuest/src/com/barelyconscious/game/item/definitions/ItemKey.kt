package com.barelyconscious.game.item.definitions

import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.item.Item

class ItemKey(
    displayName: String,
    val lockId: Int
) : Item(
    displayName,
    1,
    1,
    "A key.",
    arrayListOf(),
    1,
    1,
    Tile.KEY_TILE_ID
)
