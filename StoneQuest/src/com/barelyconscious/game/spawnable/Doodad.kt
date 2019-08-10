package com.barelyconscious.game.spawnable

abstract class Doodad(
    displayName: String,
    initialTileId: Int,
    private val spentTileId: Int,
    x: Int,
    y: Int
) : Sprite(displayName, initialTileId) {

    private var isOpen: Boolean = false

    /**
     * if true, the Doodad will require a key to open
     */
    private val requiresKey: Boolean = true

    init {
        super.setPosition(x, y)
        super.setCollision(true)
    }

    override fun interact() {
        if (!isOpen) {
            isOpen = true
            super.setTileId(spentTileId)
        }
    }
}
