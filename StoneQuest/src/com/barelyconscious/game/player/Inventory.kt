package com.barelyconscious.game.player

import com.barelyconscious.game.Game
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.definitions.*
import com.barelyconscious.game.spawnable.Loot
import com.barelyconscious.services.SoundMessageData
import com.barelyconscious.services.SoundService
import com.barelyconscious.services.audio.PlayableSound
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.logs.TextLogMessageData
import com.barelyconscious.services.messaging.logs.TextLogWriterService

class Inventory(
//    private val textLog: TextLog,
    private val messageSystem: MessageSystem
) {

    companion object {
        private const val MAX_INVENTORY_SLOTS: Int = 43
    }

    private val inventorySlots: ArrayList<Item> = ArrayList()
    var gold: Int = 0
        private set

    fun containsLike(pred: (Item) -> Boolean): Boolean {
        return inventorySlots.any(pred)
    }

    /**
     * Adds item to the player's Inventory; if the item being added is gold, it
     * is added to the variable representing the amount of gold that a player
     * has; if the Inventory is full, the Item is not picked up; if the Item
     * exists in the Inventory already, its stack size is increased, otherwise
     * the Item is added to the bottom of the Inventory
     *
     * @param item the Item to attempt to be added to the Inventory
     * @return true if the Item was successfully added to the Inventory, false
     * if it was unable to add it
     */
    fun addItem(item: Item): Boolean {
        if (item.displayName == "gold") {
            gold += item.stackSize

            messageSystem.sendMessage(
                SoundService.PLAY_SOUND,
                SoundMessageData(PlayableSound.LOOT_COINS),
                this)

            return true
        }

        if (inventorySlots.size >= MAX_INVENTORY_SLOTS) {
            return false
        }

        if (inventorySlots.contains(item)) {
            inventorySlots.findLast { it == item }!!
                .adjustStackBy(item.stackSize)
            return true
        }

        inventorySlots.add(item)

        return true
    }

    /**
     * Uses an Item at location
     *
     * @index in the Player's Inventory.
     * @param index the index location of the Item within the Player's inventory
     */
    fun useItem(index: Int) {
        val item: Item? = getItemAt(index)

        if (item == null) {
            System.err.println("[ERR:useItem()] No item to use here $index. Num items is ${inventorySlots.size}")
        } else {
            item.onUse()
        }
    }

    /**
     * Drops the Item located at
     *
     * @index in the Player's inventory.
     * @param index the index location of the Item within the Player's inventory
     */
    fun dropItem(index: Int) {
        val item: Item = getItemAt(index)
            ?: return

        item.adjustStackBy(-1)

        if (item.stackSize <= 0) {
            removeItemAt(index)
        }

        val droppedItem: Item = when (item) {
//            is Projectile -> removeItemAt(index) todo what
            is Armor -> Armor(
                item.displayName,
                item.sellValue,
                item.bonusArmor,
                item.slotId,
                item.tileId,
                item.getItemAffixes().toMutableList() as ArrayList<AttributeMod>,
                item.itemLevel
            )
            is Weapon -> Weapon(
                item.displayName,
                item.sellValue,
                item.minDamageBonus,
                item.maxDamageBonus,
                item.tileId,
                item.getItemAffixes().toMutableList() as ArrayList<AttributeMod>,
                item.itemLevel
            )
            is Food -> Food(
                item.displayName,
                item.sellValue,
                1,
                item.changeInHealth
            )
            is Potion -> Potion(
                item.displayName,
                item.sellValue,
                1,
                item.effects
            )
            is Projectile -> {
                removeItemAt(index)

                Projectile(
                    item.displayName,
                    item.sellValue,
                    item.stackSize + 1, // todo wat
                    item.tileId,
                    item.requireBow,
                    item.metal
                )
            }
            is Scroll -> Scroll(
                item.scrollName,
                item.sellValue,
                item.scrollId,
                messageSystem,
                item.getItemAffixes().toMutableList() as ArrayList<AttributeMod>
            )
            else -> Item(item)
        }

        droppedItem.rarityColorRgb = item.rarityColorRgb
        val droppedLoot = Loot(droppedItem, Game.world.playerX, Game.world.playerY, messageSystem)
        Game.world.addLoot(droppedLoot)
    }

    fun examineItem(index: Int) {
        val item: Item? = getItemAt(index)
        if (item == null) {
            System.err.println(
                "[ERR:examineItem()] Attempting to access item at [$index]. Max is $MAX_INVENTORY_SLOTS"
            )
        } else {
            messageSystem.sendMessage(
                TextLogWriterService.LOG_EVENT_CODE,
                TextLogMessageData(item.itemDescription),
                this)
        }
    }

    /**
     * Salvages the Item located at @index within the Player's Inventory.
     * Salvaging an Item turns it into craftable components which can then be
     * used to improve other Items.
     * @param index the index location of the Item within the Player's inventory
     */
    fun salvageItem(index: Int) {
        messageSystem.sendMessage(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogMessageData("Salvaging NYI"),
            this)
    }

    /**
     * Returns the Item at index and null if the index is attempting to access
     * an out of bounds Item slot
     *
     * @param index the index of the Item to get
     * @return the item, if one exists, at index
     */
    fun getItemAt(index: Int): Item? =
        if (index < 0 || index >= inventorySlots.size) {
            null
        } else {
            inventorySlots[index]
        }

    fun removeItem(item: Item) =
        inventorySlots.remove(item)

    /**
     * Removes an Item at index from the Inventory
     *
     * @param index the index of the Item to be removed from the Inventory
     * @return the Item removed, if successful
     */
    private fun removeItemAt(index: Int): Item? {
        val removed: Item = getItemAt(index)
            ?: return null

        if ((removed is Armor && removed.isEquipped) || (removed is Weapon && removed.isEquipped)) {
            Game.player.unequipItem(removed)
        }

        return inventorySlots.removeAt(index)
    }

    fun getNumItems(): Int =
        inventorySlots.size
}
