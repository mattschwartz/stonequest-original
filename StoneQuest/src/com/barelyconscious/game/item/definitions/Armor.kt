package com.barelyconscious.game.item.definitions

import com.barelyconscious.game.Game
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.OptionsKey
import com.barelyconscious.game.player.AttributeMod
import com.barelyconscious.game.player.Player

/**
 * Create a new piece of armor wearable by the player with the following parameters:
 *
 * @param name the displayName of the armor displayable to the player
 * @param sellV the value vendors place on the armor. When a player sells Items to the shop, this is the amount of
 * gold credited to them in return
 * @param armor any amount of bonus armor afforded by the Item
 * @param slotId where on the player this piece of armor fits
 * @param tileId the id corresponding to a Tile that will be drawn if the item is on the ground in the world
 * @param affixes if this is not null, is an array of StatBonuses which are bonuses to the player's attributes when
 * the piece of armor is worn
 */
class Armor(
    displayName: String,
    sellValue: Int,
    val bonusArmor: Int,
    val slotId: Int,
    tileId: Int,
    itemAffixes: ArrayList<AttributeMod>,
    itemLevel: Int
) : Item(
    displayName,
    itemLevel,
    -1,
    "Place ${armorIdToString(slotId)} for best results.",
    itemAffixes,
    sellValue,
    1,
    tileId
) {

    var isEquipped: Boolean = false
    set(value) {
        field = value
        optionsDescriptions[OptionsKey.USE] = if (isEquipped) {
            "unequip"
        } else {
            "equip"
        }
    }

    companion object {
        fun armorIdToString(armorSlotId: Int) = when (armorSlotId) {
            Player.NECK_SLOT_ID -> "around neck"
            Player.HELM_SLOT_ID -> "on head"
            Player.EARRING_SLOT_ID -> "in ear"
            Player.CHEST_SLOT_ID -> "on chest"
            Player.OFF_HAND_SLOT_ID -> "in off hand"
            Player.BELT_SLOT_ID -> "around waist"
            Player.GREAVES_SLOT_ID -> "on legs"
            Player.RING_SLOT_ID -> "on finger"
            Player.BOOTS_SLOT_ID -> "on feet"
            else -> "back on the ground"
        }

        fun armorTypeToString(armorSlotId: Int) = when (armorSlotId) {
            Player.NECK_SLOT_ID -> "necklace"
            Player.HELM_SLOT_ID -> "helmet"
            Player.EARRING_SLOT_ID -> "earring"
            Player.CHEST_SLOT_ID -> "chest"
            Player.OFF_HAND_SLOT_ID -> "off-hand"
            Player.BELT_SLOT_ID -> "belt"
            Player.GREAVES_SLOT_ID -> "greaves"
            Player.RING_SLOT_ID -> "ring"
            Player.BOOTS_SLOT_ID -> "boots"
            else -> "ground fodder"
        }
    }

    fun equip() {
        Game.player.equip(slotId, this)
        isEquipped = true

        getItemAffixes().forEach {
            Game.player.setTemporaryAttribute(
                it.attributeId,
                it.attributeModifier)
        }
    }

    fun unequip() {
        Game.player.unequip(slotId)
        isEquipped = false
        getItemAffixes().forEach {
            Game.player.setTemporaryAttribute(
                it.attributeId,
                it.attributeModifier)
        }
    }

    override fun onUse() {
        if (isEquipped) {
            unequip()
        } else {
            val player = Game.player
            if (player.isArmorSlotEquipped(slotId)) {
                player.unequip(slotId)
            }
            equip()
        }
    }

    override fun toString(): String = when (slotId) {
        Player.NECK_SLOT_ID,
        Player.HELM_SLOT_ID,
        Player.EARRING_SLOT_ID,
        Player.CHEST_SLOT_ID,
        Player.OFF_HAND_SLOT_ID,
        Player.BELT_SLOT_ID,
        Player.RING_SLOT_ID ->
            "a " + super.displayName

        Player.GREAVES_SLOT_ID,
        Player.BOOTS_SLOT_ID ->
            "some " + super.displayName

        else -> "Papaya fruitsauce"
    }
}
