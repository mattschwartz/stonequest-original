package com.barelyconscious.game.graphics

import com.barelyconscious.game.item.Item

/**
 * Create a new LineElement with uniform color and formatting
 * @param sElement the String to be elementized
 * @param isBold if true, the String will be bolded
 * @param messageColor the color of the String
 */
class LineElement(
    private val sElement: String,
    private var isBold: Boolean,
    private var messageColor: Int
) {
    /**
     * Creates a new LineElement based on an Item's data, for convenience when
     * writing an Item to the screen.
     * @param item
     */
    constructor(item: Item) : this(
        "[${item.displayName}]",
        true,
        item.rarityColor
    )

    /**
     *
     * @return the message of the LineElement as a String
     */
    public fun getElementMessage(): String =
        sElement

    /**
     *
     * @return the uniform color of the message as an integer RGB value
     */
    public fun getColorAsRGB(): Int =
        messageColor

    /**
     * Changes the uniform color of the message
     * @param color the new color of the message as an integer RGB value
     */
    public fun setColor(color: Int) {
        messageColor = color
    }

    /**
     *
     * @return true if the message is to be drawn bolded
     */
    public fun getBold(): Boolean = isBold

    /**
     * Changes if the message should be drawn bolded
     * @param bold the new value for bold
     */
    public fun setBold(isBold: Boolean) {
        this.isBold = isBold
    }

    override fun toString(): String {
        return sElement
    }
}
