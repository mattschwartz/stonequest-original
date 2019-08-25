/* *****************************************************************************
 * File Name:         Projectile.java
 * Author:            Matt Schwartz
 * Date Created:      12.17.2012
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove
 *                    credit from code that was not written fully by yourself.
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  Projectiles are a subclass of Item and superclass to all
 *                    types of projectiles found in the game.  A projectile is
 *                    such as an arrow, which require Bows to use, bolts, which
 *                    require crossbows to use, and darts, which can be thrown
 *                    free hand.  Different types of projectile tips provide the
 *                    arrow with different types of attack bonuses when used.
 *                    Projectiles add a ranged combat mechanic to the game and
 *                    can be enchanted or augmented to provide additional effects
 *                    which cannot be reused when the Projectile is picked up again
 *                    by the player.
 *                    Some features NYI but that I would like implemented:
 *                   -Projectile augmentations such as flame, paralysis, freezing,
 *                     envenomed, returning (projectiles automatically return to
 *                     the player when fired), confusion (causes monsters to attack
 *                     other monsters for some amount of time), explosive
 ************************************************************************** */
package com.barelyconscious.game.item.definitions

import com.barelyconscious.game.item.Item

/**
 * Create a new Projectile Item type with the following parameters
 *
 * @param name the displayName of the Projectile visible to the player
 * @param sellValue the value in gold vendors are willing to give the player in exchange for the item
 * @param stack the stack size of the Projectile
 * @param tileId each type of Projectile has corresponding artwork that is drawn to the Screen when the Item is in
 * the world
 * @param doesRequireBow true if the Projectile requires a bow or crossbow to fire
 * @param metal the type of metal of the Projectile; stronger metals have better attack bonuses
 */
class Projectile(
    displayName: String,
    sellValue: Int,
    stackSize: Int,
    tileId: Int,
    val requireBow: Boolean,
    val metal: ProjectileTip
) : Item(
    displayName,
    1,
    1,
    "Fire at an enemy from a distance, dealing damage based on type of metal.",
    arrayListOf(),
    sellValue,
    stackSize,
    tileId
) {

    class ProjectileTip(
        val minDamage: Double,
        val maxDamage: Double,
        val critChance: Double
    )

    companion object{
        val BRONZE_TIP = ProjectileTip(1.0, 5.0, 5.0)
        val IRON_TIP = ProjectileTip(5.0, 15.0, 5.0)
        val STEEL_TIP = ProjectileTip(15.0, 35.0, 12.0)
        val TITANIUM_TIP = ProjectileTip(255.0, 515.0, 35.0)
    }

    override fun onUse() {
        System.err.println("[NOTIFY] No use yet.")
    }

    override fun toString(): String = when (metal) {
        BRONZE_TIP -> "bronze"
        IRON_TIP -> "iron"
        STEEL_TIP -> "steel"
        TITANIUM_TIP -> "titanium"
        else -> "WTFium"
    }
}
