package com.barelyconscious.game.item.definitions

import com.barelyconscious.game.Game
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.OptionsKey
import com.barelyconscious.game.player.AttributeMod

/**
 * Creates a new Weapon with the following parameters
 *
 * @param name the displayName visible to the Player of the Weapon
 * @param sellV the value in gold that vendors will give to the player in exchange for the Weapon
 * @param min the minimum damage before multipliers that the Weapon can deal
 * @param max the maximum damage before multipliers that the Weapon can deal
 * @param weaponType [NYI] the type of weapon which determines how the weapon can be used as well as restrictions of
 * the weapon
 * @param effects any attribute mods present on the Weapon
 */
class Weapon(
    displayName: String,
    sellValue: Int,
    val minDamageBonus: Double,
    val maxDamageBonus: Double,
    tileId: Int,
    itemAffixes: ArrayList<AttributeMod>,
    itemLevel: Int
) : Item(
    displayName,
    itemLevel,
    -1,
    "Hold the pointy end away from you and you'll be fine. Swing wildly for best results.",
    itemAffixes,
    sellValue,
    1,
    tileId
) {

    enum class WeaponType { HEAVY, QUICK, RETALIATING }

    init {
        optionsDescriptions[OptionsKey.USE] = "equip"
    }

    val weaponType = WeaponType.HEAVY
    var isEquipped: Boolean = false
        set(value) {
            field = value
            optionsDescriptions[OptionsKey.USE] = if (value) {
                "unequip"
            } else {
                "equip"
            }
        }

    fun weaponTypeToString(weaponType: WeaponType): String = when (weaponType) {
        WeaponType.HEAVY -> "heavy"
        WeaponType.QUICK -> "quick"
        WeaponType.RETALIATING -> "retaliating"
    }

    override fun onUse() = if (isEquipped) {
        Game.player.unequipItem(this)
    } else {
        Game.player.equipItem(this)
    }
}
