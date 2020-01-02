package com.barelyconscious.game.spawnable.entities

import com.barelyconscious.game.Common
import com.barelyconscious.game.Game
import com.barelyconscious.game.Game.world
import com.barelyconscious.game.graphics.LineElement
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.spawnable.Entity
import com.barelyconscious.game.spawnable.Loot
import com.barelyconscious.services.SoundService
import com.barelyconscious.systems.audio.PlayableSound
import com.barelyconscious.systems.MessageSystem
import com.barelyconscious.systems.messaging.data.SoundMessageData
import com.barelyconscious.services.TextLogMessageData
import com.barelyconscious.services.TextLogWriterService
import java.time.Clock
import java.util.*
import kotlin.math.abs
import kotlin.math.ceil

class SewerRatEntity(
    level: Int,
    x: Int,
    y: Int,
    messageSystem: MessageSystem
) : Entity("Sewer Rat", Tile.SEWER_RAT_TILE_ID, messageSystem) {

    private val minimumDamage: Double = 0.75 * (1 + (level * 1.05))
    private val maximumDamage: Double = 1.99 * (1 + (level * 1.55))
    private val criticalStrikeChance: Double = 0.05
    private val criticalDamageMultiplier: Double = 25.0

    init {
        super.level = level
        super.maxHealth = 10.0 * (level * 0.76)

        super.setPosition(x, y)
        super.setLastKnownPosition(Integer.MIN_VALUE, Int.MIN_VALUE)
    }

    override fun tick() {
        val reach = 1 // todo this was never really used

        val playerX: Int = world.playerX
        val playerY: Int = world.playerY
        var xDir: Int = (playerX - xPos) shr 31
        var yDir: Int = (playerY - yPos) shr 31
        val r: Int = reach * Common.TILE_SIZE

        if ((abs(playerX - xPos) <= r && (playerY - yPos) == 0)
            || (abs(playerY - yPos) <= r && (playerX - xPos) == 0)) {
            interact()
            return
        }

        if (playerX > xPos) {
            xDir = 1
        }
        if (playerY > yPos) {
            yDir = 1
        }

        xDir *= Common.TILE_SIZE
        yDir *= Common.TILE_SIZE

        val shouldMove = world.canMove(xPos + xDir, yPos + yDir)
            && !((xPos + xDir) == playerX && (yPos + yDir) == playerY)
        if (shouldMove) {
            setPosition(xPos + xDir, yPos + yDir)
        }

        if (isVisible) {
            setLastKnownPosition(xPos, yPos)
        }
    }

    override fun remove() {
        val drop: Loot
        val rand = Random(Clock.systemUTC().millis())
        val amount = (rand.nextInt(1400) + 1) * level

        messageSystem.sendMessage(
            TextLogWriterService.LOG_WRITE_TEXT,
            TextLogMessageData("$displayName has been murdered brutally. Its family mourns.")
                .with(LineElement(displayName, true, Common.FONT_ENTITY_LABEL_RGB)),
            this)

        drop = Loot(Item.createGoldItem(amount), xPos, yPos, messageSystem)
        drop.isVisible = true
        drop.removableOnWalkover = true

        world.addLoot(drop)

        super.remove()
    }

    override fun interact() {
        val hit: Double = calculateHit()

        // non-entity specific stuff that should be dealt with another way but who's keepin score?
        Game.player.changeHealthBy(-hit)
        messageSystem.sendMessage(
            TextLogWriterService.LOG_WRITE_TEXT,
            TextLogMessageData("$displayName hits you for ${ceil(hit).toInt()} physical")
                .with(LineElement(displayName, true, Common.FONT_ENTITY_LABEL_RGB)),
            this
        )

        messageSystem.sendMessage(
            SoundService.PLAY_SOUND,
            SoundMessageData(PlayableSound.CHICKEN_CLUCK),
            this)
    }

    private fun calculateHit(): Double {
        val rand = Random()
        val hit: Double = minimumDamage + rand.nextFloat() * (maximumDamage - minimumDamage)

        if ((rand.nextDouble() * 100) <= criticalStrikeChance) {
            return hit * (1 + criticalDamageMultiplier / 100)
        }

        return hit
    }

}
