package com.barelyconscious.game.player

/**
 * Create a AttributeMod with the following parameters
 * @param id the id of the attribute of the Player which will be affected
 * by the modification
 * @param mod the amount to adjust the attribute by
 */
class AttributeMod(

    /**
     * Returns the affected attribute plus 10 because it is current attributes which
     * are affected and not the Player's skill level
     * @return
     */
    val attributeId: Int,

    /**
     *
     * @return the amount by which the attribute is affected
     */
    val attributeModifier: Double
) {

    /**
     * Translates an attribute id into a String
     * @return
     */
    override fun toString(): String {
        return Player.idToString(attributeId)
    }
}
