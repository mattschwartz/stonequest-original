package com.barelyconscious.game

import com.barelyconscious.game.graphics.Font
import com.barelyconscious.game.graphics.GameMap
import com.barelyconscious.game.graphics.tiles.Tile
import com.barelyconscious.game.input.Interactable
import com.barelyconscious.game.input.KeyMap
import com.barelyconscious.game.input.MouseHandler
import com.barelyconscious.game.item.Item
import com.barelyconscious.game.menu.LootPickupMenu
import com.barelyconscious.game.menu.PopupMenu
import com.barelyconscious.game.menu.TextLog
import com.barelyconscious.game.menu.ToolTipMenu
import com.barelyconscious.game.player.Player
import com.barelyconscious.game.portrait.Portrait
import com.barelyconscious.game.spawnable.*
import com.barelyconscious.game.spawnable.entities.SewerRatEntity
import com.barelyconscious.services.messaging.MessageSystem
import com.barelyconscious.services.messaging.logs.TextLogMessageData
import com.barelyconscious.services.messaging.logs.TextLogWriterService
import java.time.Clock

class World(
    private val player: Player,
    private val textLog: TextLog,
    private val gameMap: GameMap,
    private val toolTipMenu: ToolTipMenu,
    private val lootWindow: LootPickupMenu,
    private val mouseHandler: MouseHandler,

    private val messageSystem: MessageSystem,
    private val clock: Clock
) : Interactable() {

    var playerX: Int = 0
    var playerY: Int = 0
    private var displayInfo: Boolean = false
    val entities: EntityList = EntityList()
    val lootItems: LootList = LootList()
    val doodads: ArrayList<Doodad> = arrayListOf()
    private var popup: PopupMenu? = null

    init {
        testInitializeEntities()
    }

    private fun generateMap(depth: Int) {
        gameMap.generateAreaMap(depth, 25, "Kud Aradji Steppes")
    }

    fun resize(width: Int, height: Int) {
        playerX = width / 2
        playerY = (textLog.offsY - textLog.pixelHeight) / 2

        if ((playerY % Common.TILE_SIZE) > (Common.TILE_SIZE / 2)) {
            playerY += Common.TILE_SIZE - (playerY % Common.TILE_SIZE)
        } else {
            playerY -= playerY % Common.TILE_SIZE
        }

        if ((playerX % Common.TILE_SIZE) > (Common.TILE_SIZE / 2)) {
            playerX += Common.TILE_SIZE - (playerX % Common.TILE_SIZE)
        } else {
            playerX -= playerX % Common.TILE_SIZE
        }

        defineMouseZone(0,
            0,
            toolTipMenu.offsX,
            textLog.offsY - textLog.pixelHeight
        )
    }

    override fun mouseMoved(x: Int, y: Int) {
        if (lootWindow.isActive) {
            lootWindow.mouseMoved(x, y)
            clearFocus()
        } else {
            setActive()
            super.mouseMoved(x - x % Common.TILE_SIZE, y - y % Common.TILE_SIZE)
        }
    }

    override fun mouseClicked(button: Int, x: Int, y: Int) {
//        if (lootWindow.isActive()) {
//            lootWindow.mouseClicked(button, x, y);
//            clearFocus();
//            return;
//        }

//        if ((gameMap.tileLightStatus(mouseX, mouseY) & GameMap.IS_VISIBLE) >> 8 == 1
//                || (gameMap.tileLightStatus(mouseX, mouseY) & GameMap.RECENTLY_SEEN) >> 9 == 1) {

        if (gameMap.isTileVisibleAt(mouseX, mouseY) || gameMap.isTileRecentlySeenAt(mouseX, mouseY)) {

            if (button == MouseHandler.RIGHT_CLICK.toInt()) {
                popup = PopupMenu(mouseX, mouseY, this, "Walk here")
                disableMouse()
                return
            }

//            pathFind((mouseX - playerX) / Common.TILE_SIZE, (mouseY - playerY) / Common.TILE_SIZE);
        }
    }

    override fun mouseExited() {
        clearFocus()
    }

    override fun disableMouse() {
        mouseHandler.removeHoverableListener(this)
        mouseHandler.removeClickableListener(this)
    }

    override fun enableMouse() {
        mouseHandler.registerHoverableListener(this)
        mouseHandler.registerClickableListener(this)
        popup = null
    }

    override fun parentCallback(ret: Int) {
        when (ret) {
            // todo what
            0 -> mouseClicked(MouseHandler.LEFT_CLICK.toInt(), mouseX, mouseY)
        }
    }

    override fun keyPressed(key: Int) {
        val moveLeft: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_LEFT)
        val moveLeftAlt: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_LEFT_ALT)
        val moveRight: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_RIGHT)
        val moveRightAlt: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_RIGHT_ALT)
        val moveUp: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_UP)
        val moveUpAlt: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_UP_ALT)
        val moveDown: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_DOWN)
        val moveDownAlt: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_DOWN_ALT)
        val pickupItem: Int = KeyMap.getKeyCode(KeyMap.Key.PICKUP_ITEM)
        val waitTurnKey: Int = KeyMap.getKeyCode(KeyMap.Key.PLAYER_SKIP_TURN)

        if (lootWindow.isActive) {
            lootWindow.keyPressed(key)
            clearFocus()
            return
        }

        when (key) {
            'T'.toInt() -> {
                player.levelUp()
                player.addPointToAttribute(Player.DEFENSE)
            }
            moveLeft, moveLeftAlt -> move(false, -1, 0)
            moveRight, moveRightAlt -> move(false, 1, 0)
            moveUp, moveUpAlt -> move(false, 0, -1)
            moveDown, moveDownAlt -> move(false, 0, 1)
            pickupItem -> {
                println("picking up shit")
                pickUpItem()
            }
            waitTurnKey -> waitTurn()
            else -> println("Unexpected key pressed: '${key.toChar()}'")
        }
    }

    /**
     * todo might be taken out
     */
    fun displayZoneInfo() {
        displayInfo = !displayInfo
    }

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }

    fun removeEntity(entity: Entity) {
        entities.remove(entity)
    }

    fun addLoot(item: Item, x: Int, y: Int) {
        addLoot(Loot(item, x, y, messageSystem))
    }

    fun addLoot(loot: Loot) =
        lootItems.add(loot)

    fun removeLoot(loot: Loot): Loot? =
        lootItems.remove(loot)

    fun addDoodad(doodad: Doodad) =
        doodads.add(doodad)

    fun removeDoodad(doodad: Doodad) =
        doodads.remove(doodad)

    fun getEntityAt(x: Int, y: Int): Entity? =
        entities.get(x, y)

    fun getLootAt(x: Int, y: Int): Loot? {
        val list: List<Loot> = lootItems.getList(x, y)

        return if (list.isEmpty()) {
            null
        } else {
            list.last()
        }
    }

    fun pickUpItem() {
        val lootPile = lootItems.getList(playerX, playerY) ?: arrayListOf()

        if (lootPile.isEmpty()) {
            writeLog("There is nothing to pick up.")
        } else {
            if (lootPile.size > 1) {
                lootWindow.setPosition(mouseX, mouseY)
                lootWindow.setItemList(lootItems.getListAsItem(playerX, playerY))
                lootWindow.setActive()
            } else {
                player.interactWith(lootPile[0])
            }
        }

        tick()
    }


    fun waitTurn() {
        writeLog("You twiddle your thumbs.")
        tick()
    }

    /**
     * Supply a change in x and y for the player to move. If isRanged is true
     * the player will not move and instead will fire a projectile in the
     * direction supplied.
     *
     * @param isRanged unused
     * @param dx the change in x of the player's location
     * @param dy the change in y of the player's location
     */
    fun move(isRanged: Boolean, dx: Int, dy: Int) {
        // Hopefully this will be taken care of in a better fashion
        val entity: Entity?
        val loot: java.util.ArrayList<Loot>?

        // debug
        if (isRanged) {
            System.err.println("Yo. Ranged dont mean nothin do it??")
//            deltaY *= 20
//            deltaX *= 20
        }

        val deltaX = dx * Common.TILE_SIZE
        val deltaY = dy * Common.TILE_SIZE

        // Move the player if possible
        entity = getEntityAt(playerX + deltaX, playerY + deltaY)

        // interact with any entities in the next tile space
        if (entity != null) {
            player.interactWith(entity)
        } else if (canMove(playerX + deltaX, playerY + deltaY)) {
            gameMap.shiftWorldBy(-deltaX, -deltaY)
            shiftSpritesBy(-deltaX, -deltaY)
            loot = lootItems.getList(playerX, playerY)

            if (loot != null) {
                // Print all the loot on this tile to the textLog for the player to see
                for (i in loot.indices) {
                    loot[i].onWalkOver()
                }
            }
        } else {
            for (doodad in doodads) {
                if (doodad.xPos == playerX + deltaX && doodad.yPos == playerY + deltaY) {
                    player.interactWith(doodad)
                    tick()
                }
            }
            return
        }

        tick()
    }

    /**
     * Move all sprites [entities and loot objects] by deltaX, deltaY to give
     * the appearance of a moved worldview.
     *
     * @param deltaX direction in x to shift
     * @param deltaY direction in y to shift
     */
    private fun shiftSpritesBy(deltaX: Int, deltaY: Int) {
        for (i in 0 until entities.size()) {
            entities.get(i).changePositionBy(deltaX, deltaY)
        }

        for (i in 0 until lootItems.size()) {
            lootItems.get(i).changePositionBy(deltaX, deltaY)
        }

        for (doodad in doodads) {
            doodad.changePositionBy(deltaX, deltaY)
        }
    }

    /**
     * Returns whether or not the calling entity is able to move to the
     * requested position
     *
     * @x,
     * @y. Performs a check with the environment as well as other entities.
     * @param x
     * @param y
     * @return
     */
    fun canMove(x: Int, y: Int): Boolean {
        var doodadBlocking = true

        // Entity blocking path?
        if (getEntityAt(x, y) != null) {
            return false
        }

        // Path-blocking doodad?
        for (doodad in doodads) {
            if (doodad.xPos == x && doodad.yPos == y) {
                doodadBlocking = !doodad.hasCollision()
                break
            }
        }

        // Environment blocking path?
        return gameMap.canMove(x / Common.TILE_SIZE, y / Common.TILE_SIZE) && doodadBlocking
    }

    /**
     * Contains everything that needs to onUpdate when the player performs an
     * action, such as moving entities, decreasing buff timers, etc. Each Sprite
     * implements a tick() function which is called to perform whatever updates
     * it needs to.
     */
    fun tick() {
        player.tick()

        for (i in 0 until entities.size()) {
            entities.get(i).tick()
        }

        for (i in 0 until lootItems.size()) {
            if (lootItems.get(i)?.removeOnTick == true) {
                removeLoot(lootItems.get(i))
                continue
            }

            lootItems.get(i).tick()
        }
        mouseLastMoved = clock.millis()
    }

    /**
     * Renders the World and other information to the Game's Screen,
     *
     * @screen.
     * @param screen the screen to render to
     */
    fun render(screen: Screen) {
//        renderZoneInfo(screen);
        renderSprites(screen)
        renderMouseLocation(screen)

//        // Render portraits!
//        renderPortraits(screen)

        if (popup != null) {
            renderPopup(screen)
        }
    }

    /**
     * Displays the information about the zone for the player on
     *
     * @screen.
     * @param screen the screen that the zone information is drawn to
     */
    fun renderZoneInfo(screen: Screen) {
        if (!displayInfo) {
            return
        }

        var r: Int
        var g: Int
        var b: Int
        val zoneLevel = gameMap.zoneLevel

        val infoStartX = 0
        val infoStartY = Font.CHAR_HEIGHT * 3
        var levelDiffRGB: Int
        val levelDifference = player.level * 1.0 / zoneLevel

        r = 255
        g = (75 + (255 - 75) * levelDifference).toInt()
        b = 75

        if (g >= 255) {
            r = (255 - 255 * (levelDifference - 1)).toInt()
            g = 255
            b = 75
        }

        levelDiffRGB = (r shl 16) + (g shl 8) + b

        if (player.level >= zoneLevel + 3) {
            levelDiffRGB = Common.FONT_DEFAULT_RGB
        }
        Font.drawOutlinedMessage(screen, "Zone: " + gameMap.zoneName, Common.FONT_WHITE_RGB, false, infoStartX, infoStartY)
        Font.drawOutlinedMessage(screen, "Level: " + gameMap.zoneLevel, levelDiffRGB, false, infoStartX, infoStartY + Font.CHAR_HEIGHT)
        Font.drawOutlinedMessage(screen, "Elites: " + gameMap.remainingElites, Common.FONT_WHITE_RGB, false, infoStartX, infoStartY + Font.CHAR_HEIGHT * 2)
    }

    /**
     * Draw all entities (including the player) and the loot to the screen.
     *
     * @param screen
     */
    fun renderSprites(screen: Screen) {
        // Draw doodads to the screen
        renderDoodads(screen)

        // Draw the loot to the screen
        renderLoot(screen)

        // Draw the entities to the screen
        renderEntities(screen)

        // Lastly, render the player to the screen on top of all other Tiles
        player.tile.render(screen, playerX, playerY)

        lootWindow.render(screen)
    }

    /**
     * Draws all visible Loot objects to the screen at their positions with
     * respect to the GameMap.
     *
     * @param screen the screen to draw to
     */
    private fun renderLoot(screen: Screen) {
        var x: Int
        var y: Int

        for (i in lootItems.size() - 1 downTo 0) {
            x = lootItems.get(i).xPos
            y = lootItems.get(i).yPos

            // Continue if the loot is off screen
            if (x < 0 || y < 0 || x + Common.TILE_SIZE >= Game.getGameWidth()
                || y + Common.TILE_SIZE >= Game.getGameHeight()) {
                continue
            }

            // Render loot that is visible to the player
            if (gameMap.tileLightStatus(x, y) and GameMap.IS_VISIBLE shr 8 == 1) {
                lootItems.get(i).tile.render(screen, x, y)
                lootItems.get(i).isVisible = true
            } else if (gameMap.tileLightStatus(x, y) and GameMap.RECENTLY_SEEN shr 9 == 1) {
                lootItems.get(i).tile.renderShadedTile(screen, x, y)
                lootItems.get(i).setRecentlySeen(true)
            }
            /* Else, render loot that the player has seen before but is not
             currently in sight.  Such loot is rendered slightly darker. */
        }
    }

    /**
     * Draws all visible Entities to the screen at their positions with respect
     * to the GameMap.
     *
     * @param screen the screen to draw to
     */
    private fun renderEntities(screen: Screen) {
        var x: Int
        var y: Int
        var healthBarLength: Int

        for (i in 0 until entities.size()) {
            x = entities.get(i).xPos
            y = entities.get(i).yPos

            // Continue if the entity is off screen
            if (x < 0 || y < 0 || x + Common.TILE_SIZE >= Game.getGameWidth()
                || y + Common.TILE_SIZE >= Game.getGameHeight()) {
                continue
            }

            // Render entities that are visible to the player
            if (gameMap.tileLightStatus(x, y) and GameMap.IS_VISIBLE shr 8 == 1) {
                entities.get(i).tile.render(screen, x, y)

                // Draw health bar underneath entities if they are damaged
                if (entities.get(i).currentHealth != entities.get(i).maxHealth) {
                    healthBarLength = (entities.get(i).currentHealth / entities.get(i).maxHealth * Common.TILE_SIZE).toInt()

                    screen.drawRectangle(Common.THEME_BG_COLOR_RGB, x - 1, y + Common.TILE_SIZE - 2, Common.TILE_SIZE + 1, 3)
                    screen.drawRectangle(Common.ENTITY_DAMAGED_HEALTH_RGB, x, y + Common.TILE_SIZE - 1, Common.TILE_SIZE - 1, 1)
                    screen.drawRectangle(Common.ENTITY_HEALTH_RGB, x, y + Common.TILE_SIZE - 1, healthBarLength, 1)
                }

                entities.get(i).isVisible = true
                entities.get(i).setRecentlySeen(true)
            } else if (gameMap.tileLightStatus(x, y) and GameMap.RECENTLY_SEEN shr 9 == 1) {

                // Don't render before-seen entities off screen
                if (entities.get(i).lastKnownXPos < 0 || entities.get(i).lastKnownYPos < 0
                    || entities.get(i).lastKnownXPos >= Game.getGameWidth() || entities.get(i).lastKnownYPos >= Game.getGameHeight()) {
                    continue
                }

                /* If the entity is still not visible, draw its last known position
                 otherwise, if it's last seen location is uncovered and the
                 entity has moved, the entity's last location is no longer
                 known to the player.  */
                if (gameMap.tileLightStatus(entities.get(i).lastKnownXPos, entities.get(i).lastKnownYPos) and GameMap.IS_VISIBLE shr 8 != 1) {
                    entities.get(i).tile.renderShadedTile(screen, entities.get(i).lastKnownXPos, entities.get(i).lastKnownYPos)
                }

                entities.get(i).isVisible = false
                entities.get(i).setRecentlySeen(true)
            } else {
                /* Else, render entities that the player has seen before but are not
                     currently in view.  Such entities are rendered slightly darker and
                     their positions are not tracked until the entity comes back into
                     view */
                entities.get(i).isVisible = false
                entities.get(i).setRecentlySeen(false)
            }
        }
    }

    /**
     * Draws all visible Doodads to the screen at their positions with respect
     * to the GameMap.
     *
     * @param screen the screen to draw to
     */
    private fun renderDoodads(screen: Screen) {
        var x: Int
        var y: Int

        for (doodad in doodads) {
            x = doodad.xPos
            y = doodad.yPos

            if (x < 0 || y < 0 || x + Common.TILE_SIZE >= Game.getGameWidth()
                || y + Common.TILE_SIZE >= Game.getGameHeight()) {
                continue
            }

            if (gameMap.tileLightStatus(x, y) and GameMap.IS_VISIBLE shr 8 == 1) {
                doodad.tile.render(screen, x, y)
                doodad.isVisible = true
            } else if (gameMap.tileLightStatus(x, y) and GameMap.RECENTLY_SEEN shr 9 == 1) {
                doodad.tile.renderShadedTile(screen, x, y)
                doodad.setRecentlySeen(true)
            }
        }
    }

    /**
     * Draw Entity portraits to the screen, such as the Player's and Entities
     * that the user's mouse is hovering over.
     *
     * @param screen the screen to draw to
     */
    fun renderPortraits(screen: Screen) {
        val playerPortraitOffsX = 1
        val playerPortraitOffsY = 1
        val entityPortraitOffsY: Int
        var entityOffset = 0

        val playerPortrait = Portrait(Tile.PLAYER_TILE_ID, player, Portrait.PLAYER)
        playerPortrait.render(screen, playerPortraitOffsX, playerPortraitOffsY)

        entityPortraitOffsY = playerPortrait.height + 2

        for (i in 0 until entities.size()) {
            if (!entities.get(i).isVisible) {
                continue
            }
            val p2 = Portrait(Tile.PLAYER_TILE_ID, entities.get(i), Portrait.NON_ELITE)
            p2.render(screen, playerPortraitOffsX, entityPortraitOffsY + (p2.height + 2) * entityOffset++)
        }
    }

    /**
     * Displays where the World sees the user's cursor.
     *
     * @param screen the screen to draw to
     */
    private fun renderMouseLocation(screen: Screen) {
        if (!isActive || lootWindow.isActive) {
            clearFocus()
            return
        }

        var msg: String
        var color = Common.WORLD_TILE_SELECT_GOOD
        val msgX = mouseX
        var msgY = mouseY - Common.TILE_SIZE

        // Mouse is hovering over some loot
        if (lootItems.get(mouseX, mouseY) != null && lootItems.get(mouseX, mouseY)!!.isVisible) {
            msg = lootItems.get(mouseX, mouseY)!!.displayName
        } else if (entities.get(mouseX, mouseY) != null && entities.get(mouseX, mouseY)!!.isVisible) {
            msgY -= Font.CHAR_HEIGHT
            msg = entities.get(mouseX, mouseY)!!.displayName + "\nlevel:" + entities.get(mouseX, mouseY)!!.level
        } else if (mouseX == playerX && mouseY == playerY) {
            msgY -= Font.CHAR_HEIGHT
            msg = "player\nlookin' good"
        } else if (gameMap.tileLightStatus(mouseX, mouseY) and GameMap.IS_VISIBLE shr 8 == 1) {
            msg = Tile.getTile(gameMap.getTileIdAt(mouseX / Common.TILE_SIZE + gameMap.xStart, mouseY / Common.TILE_SIZE + gameMap.yStart) and 0xFF)!!.toString()
            // Mouse is hovering over a path-blocking object
            if (Tile.getTile(gameMap.getTileIdAt(mouseX / Common.TILE_SIZE + gameMap.xStart, mouseY / Common.TILE_SIZE + gameMap.yStart) and 0xFF)!!.hasCollision()) {
                color = Common.WORLD_TILE_SELECT_BAD
            }
        } else if (gameMap.tileLightStatus(mouseX, mouseY) and GameMap.RECENTLY_SEEN shr 9 == 1) {
            msg = "unseen terrain"
            color = Common.WORLD_TILE_SELECT_BAD
        } else {
            msg = "cannot move here"
            color = Common.WORLD_TILE_SELECT_BAD
        }// Tile is not visible to the player
        // If the tile is visible to the player
        // Mouse is hovering over the player
        // Mouse is hovering over an entity
        // else

        // Mouse is hovering over a doodad
        for (doodad in doodads) {
            if (doodad.xPos == mouseX && doodad.yPos == mouseY && doodad.isVisible) {
                if (doodad is Container) {
                    msg = "open"
                    color = Common.WORLD_TILE_SELECT_GOOD
                }
            }
        }

        // If mouse is left hovering for at least 1 second
        if (clock.millis() - mouseLastMoved > 1000) {
            msg = "right click for more info"
            //            msgY -= Font.CHAR_HEIGHT;
        }

        if (msgY < 0) {
            msgY = mouseY + Common.TILE_SIZE
        }

        if (mouseY >= screen.screenHeight - Common.TILE_SIZE) {
            mouseY -= Common.TILE_SIZE
        }

        Font.drawOutlinedMessage(screen, msg, Common.FONT_WHITE_RGB, false, msgX, msgY)
        screen.drawRectangle(color, mouseX, mouseY, Common.TILE_SIZE, Common.TILE_SIZE)
    }

    private fun renderPopup(screen: Screen) {
        popup?.init()
        popup?.render(screen)
    }

    private fun testInitializeEntities() {
        addEntity(SewerRatEntity(1, 64, 32, messageSystem))
        addEntity(SewerRatEntity(1, 96, 32, messageSystem))
        addEntity(SewerRatEntity(1, 32, 32, messageSystem))

        val chest = Container(
            "Chest",
            Tile.CONTAINER_CHEST_CLOSED_TILE_ID,
            Tile.CONTAINER_CHEST_OPEN_TILE_ID,
            40,
            0,
            messageSystem,
            lootWindow
        )
        val items: ArrayList<Item> = arrayListOf(
            Item(
                displayName = "some arrows",
                itemLevel = 259,
                rarityColorRgb = -1,
                itemDescription = "Vendor fodder",
                itemAffixes = arrayListOf(),
                sellValue = 99,
                stackSize = 1,
                tileId = Tile.ARROW_TILE_ID
            ),
            Item(
                displayName = "bootsies",
                itemLevel = 259,
                rarityColorRgb = -1,
                itemDescription = "Vendor fodder",
                itemAffixes = arrayListOf(),
                sellValue = 99,
                stackSize = 1,
                tileId = Tile.BOOTS_IRON_TILE_ID
            ),
            Item(
                displayName = "potions 1",
                itemLevel = 259,
                rarityColorRgb = -1,
                itemDescription = "Vendor fodder",
                itemAffixes = arrayListOf(),
                sellValue = 99,
                stackSize = 1,
                tileId = Tile.POTION_TILE_ID
            ),
            Item(
                displayName = "potion2 ",
                itemLevel = 259,
                rarityColorRgb = -1,
                itemDescription = "Vendor fodder",
                itemAffixes = arrayListOf(),
                sellValue = 99,
                stackSize = 1,
                tileId = Tile.POTION_TILE_ID
            ),
            Item(
                displayName = "poooootion",
                itemLevel = 259,
                rarityColorRgb = -1,
                itemDescription = "Vendor fodder",
                itemAffixes = arrayListOf(),
                sellValue = 99,
                stackSize = 1,
                tileId = Tile.POTION_TILE_ID
            )
        )

        chest.setContents(items)
        addDoodad(chest)
        generateMap(154879)
    }

    private fun writeLog(message: String) =
        messageSystem.sendMessage(
            TextLogWriterService.LOG_EVENT_CODE,
            TextLogMessageData(message),
            this)
}
