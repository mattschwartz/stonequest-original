package com.barelyconscious.game.menu

import com.barelyconscious.game.Common
import com.barelyconscious.game.Game
import com.barelyconscious.game.Screen
import com.barelyconscious.game.graphics.Font
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.item.definitions.*
import com.barelyconscious.gui.IRenderable

class Tooltip(
    private val miniMap: MiniMap
) : IRenderable {

    fun render(screen: Screen, listItems: List<String>) {
        val xStart = Game.getGameWidth() - Font.CHAR_WIDTH * 2 - miniMap.pixelHeight + 1
        val yStart = miniMap.pixelHeight + Font.CHAR_HEIGHT * 2 + 1
        render(screen, xStart, yStart, false, listItems)
    }

    fun renderItem(screen: Screen, item: Item) {
        val xStart = Game.getGameWidth() - Font.CHAR_WIDTH * 2 - miniMap.pixelHeight + 1
        val yStart = miniMap.pixelHeight + Font.CHAR_HEIGHT * 2 + 1
        renderItem(screen, item, xStart, yStart, false)
    }

    fun render(
        screen: Screen,
        xStart: Int,
        yStart: Int,
        isCentered: Boolean,
        listItems: List<String>
    ) {
        val height = listItems.size * Font.CHAR_HEIGHT
        val width = (listItems.map { it.length }.max() ?: 0) * Font.CHAR_WIDTH

        val xx = if (isCentered) {
            xStart - width
        } else {
            xStart
        }

        screen.fillTransluscentRectangle(
            xx - 1,
            yStart - 1,
            width + 2,
            height + 2)
        screen.fillRectangle(
            Common.themeForegroundColor,
            xx,
            yStart,
            width,
            Font.CHAR_HEIGHT + 1)
        Font.drawMessage(
            screen,
            listItems[0],
            Common.FONT_WHITE_RGB,
            false,
            xx + (width - listItems[0].length * Font.CHAR_WIDTH) / 2,
            yStart)

        for (i in 1 until listItems.size) {
            Font.drawMessage(
                screen,
                listItems[i],
                Common.FONT_WHITE_RGB,
                false,
                xStart + (width - listItems[i].length * Font.CHAR_WIDTH) / 2,
                yStart + i * Font.CHAR_HEIGHT)
        }
    }

    fun renderItem(
        screen: Screen,
        item: Item,
        xStart: Int,
        yStart: Int,
        isCentered: Boolean
    ) {
        val listItems = mutableListOf<String>()

        listItems.add(item.displayName)
        listItems.add(when (item) {
            is Weapon -> item.weaponTypeToString(item.weaponType)
            is Armor -> Armor.armorTypeToString(item.slotId)
            is Potion -> "Lasts for ${item.effects.durationInTicks} turns"
            is Projectile -> "projectile"
            is Food -> "food"
            is Scroll -> "scroll"
            else -> "junk"
        })

        item.getItemAffixes().forEach {
            if (item is Scroll) {
                listItems.add("???")
            } else {
                val modifierSign = if (it.attributeModifier >= 0) {
                    "+"
                } else {
                    "-"
                }
                listItems.add("$modifierSign${it.attributeModifier} $it")
            }
        }

        val height = item.getItemAffixes().size * (Font.CHAR_HEIGHT + 1)
        val width = (listItems.map { it.length }.max() ?: 0) * Font.CHAR_WIDTH
        var xx = if (isCentered) {
            xStart - width / 2
        } else {
            xStart
        }
        val yy = yStart + Common.TILE_SIZE + 1

        screen.fillTransluscentRectangle(xx, yy, width, height)
        screen.fillRectangle(
            Common.themeForegroundColor,
            xx,
            yy,
            width,
            (Font.CHAR_HEIGHT + 1) * 2)
        Font.drawMessage(
            screen,
            listItems[0],
            item.rarityColorRgb,
            true,
            xx + (width - listItems[0].length * Font.CHAR_WIDTH) / 2,
            yy)

        for (i in 1 until listItems.size) {
            Font.drawMessage(
                screen,
                listItems[i],
                Common.FONT_WHITE_RGB,
                false,
                xStart + (width - listItems[i].length * Font.CHAR_WIDTH) / 2,
                yStart + i * (Font.CHAR_HEIGHT + 1))
        } // for
    }

    override fun render(screen: Screen) {
    }
}
