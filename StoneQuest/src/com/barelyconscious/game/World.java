/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        World.java
 * Author:           Matt Schwartz
 * Date created:     07.19.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: All the game logic is maintained here, including updates to 
 *                   entities and the player through the tick() function.
 *      -        int playerX: the X location of the player, kept for convenience
 *      -        int playarY: the Y location of the player, kept for convenience
 *      - EntityList entityList: a list of entities in the world 
 *      -   LootList lootList: a list of loot objects currently in the world
 *      -     Player PLAYER: the player 
 *      -    TextLog log: the text log which routes all game information for the 
 *                    player to see.
 *      -        Map map: a link to the class which draws the world to the screen
 **************************************************************************** */
package com.barelyconscious.game;

import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.Map;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyMap;
import com.barelyconscious.game.input.MouseHandler;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.menu.PopupMenu;
import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.portrait.Portrait;
import com.barelyconscious.game.spawnable.Container;
import com.barelyconscious.game.spawnable.Doodad;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.Loot;
import com.barelyconscious.game.spawnable.EntityList;
import com.barelyconscious.game.spawnable.LootList;
import com.barelyconscious.game.spawnable.entities.SewerRatEntity;
import java.util.ArrayList;

public class World extends Interactable {

    private int playerX;
    private int playerY;
    private boolean displayInfo = false;
    private EntityList entityList;
    private LootList lootList;
    private ArrayList<Doodad> doodadList;
    private final Player PLAYER;
    private final TextLog log;
    private final Map map;
    private PopupMenu popUp = null;

    public World() {
        // initialize lists to keep track of sprites
        entityList = new EntityList();
        lootList = new LootList();
        doodadList = new ArrayList();

        // initialize local pointers to Game-wide variables that this class will use
        PLAYER = Game.player;
        map = Game.map;
        log = Game.textLog;

        // debug stuff - will be removed soon
        addEntity(new SewerRatEntity(this, 1, 40, 20));
        addEntity(new SewerRatEntity(this, 1, 80, 20));
        addEntity(new SewerRatEntity(this, 1, 60, 20));
        Container chest = new Container("Chest", Tile.CONTAINER_CHEST_CLOSED_TILE_ID, Tile.CONTAINER_CHEST_OPEN_TILE_ID, 40, 0);
        ArrayList<Item> itemlist = new ArrayList();
        Item i1 = new Item("some arrows", 259, Tile.ARROW_TILE_ID);
        Item i2 = new Item("bootsies", 259, Tile.BOOTS_IRON_TILE_ID);
        Item i3 = new Item("potions 1", 259, Tile.POTION_TILE_ID);
        Item i4 = new Item("potion2 ", 259, Tile.POTION_TILE_ID);
        Item i5 = new Item("poooootion", 259, Tile.POTION_TILE_ID);
        itemlist.add(i1);
        itemlist.add(i2);
        itemlist.add(i3);
        itemlist.add(i4);
        itemlist.add(i5);

        chest.setContents(itemlist);

        addDoodad(chest);
        generateMap(154879);
        // end debug
    } // constructor

    /**
     * When the player enters a new level of the game, a new map is needed.
     *
     * @param depth
     */
    public void generateMap(int depth) {
        map.generateAreaMap(depth, 25, "Kud Aradji Steppes");
    } // generateMap

    /**
     * Performs all necessary operations when the game is resized.
     */
    public void resize(int width, int height) {
        playerX = width / 2;
        playerY = (Game.textLog.getOffsY() - Game.textLog.getPixelHeight()) / 2;

        // Make sure the player is positioned correctly
        if ((playerY % Common.TILE_SIZE) > (Common.TILE_SIZE / 2)) {
            playerY += Common.TILE_SIZE - (playerY % Common.TILE_SIZE);
        } else {
            playerY -= playerY % 20;
        } // if-else

        if ((playerX % Common.TILE_SIZE) > (Common.TILE_SIZE / 2)) {
            playerX += Common.TILE_SIZE - (playerX % Common.TILE_SIZE);
        } else {
            playerX -= playerX % 20;
        } // if-else

        /* Redefine the mouse zone because a resize might change the size of
         the world */
        defineMouseZone(0, 0, Game.toolTipMenu.getOffsX(), Game.textLog.getOffsY() - Game.textLog.getPixelHeight());
    } // resize

    @Override
    public void mouseMoved(int x, int y) {
        if (Game.lootWindow.isActive()) {
            Game.lootWindow.mouseMoved(x, y);
            clearFocus();
            return;
        } // if

        setActive();
        super.mouseMoved(x - x % Common.TILE_SIZE, y - y % Common.TILE_SIZE);
    } // mouseMoved

    @Override
    public void mouseClicked(int button, int x, int y) {
//        if (Game.lootWindow.isActive()) {
//            Game.lootWindow.mouseClicked(button, x, y);
//            clearFocus();
//            return;
//        } // if

//        if ((map.tileLightStatus(mouseX, mouseY) & Map.IS_VISIBLE) >> 8 == 1
//                || (map.tileLightStatus(mouseX, mouseY) & Map.RECENTLY_SEEN) >> 9 == 1) {

        if (map.isTileVisibleAt(mouseX, mouseY)
                || map.isTileRecentlySeenAt(mouseX, mouseY)) {

            if (button == MouseHandler.RIGHT_CLICK) {
                popUp = new PopupMenu(mouseX, mouseY, this, "Walk here");
                disableMouse();
                return;
            } // if

//            pathFind((mouseX - playerX) / Common.TILE_SIZE, (mouseY - playerY) / Common.TILE_SIZE);
        } // if
    } // mouseClicked

    @Override
    public void mouseExited() {
        clearFocus();
    } // mouseExited

    @Override
    public void disableMouse() {
        Game.mouseHandler.removeHoverableListener(this);
        Game.mouseHandler.removeClickableListener(this);
    } // disableMouse

    @Override
    public void enableMouse() {
        Game.mouseHandler.registerHoverableListener(this);
        Game.mouseHandler.registerClickableListener(this);
        popUp = null;
    } // enableMouse

    @Override
    public void parentCallback(int ret) {
        switch (ret) {
            case 0:
                mouseClicked(MouseHandler.LEFT_CLICK, mouseX, mouseY);
                break;
            case 1:
                break;
            default:
        } // switch
    } // parentCallback

    @Override
    public void keyPressed(int key) {
        int moveLeft = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_LEFT);
        int moveLeftAlt = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_LEFT_ALT);
        int moveRight = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_RIGHT);
        int moveRightAlt = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_RIGHT_ALT);
        int moveUp = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_UP);
        int moveUpAlt = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_UP_ALT);
        int moveDown = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_DOWN);
        int moveDownAlt = KeyMap.getKeyCode(KeyMap.Key.PLAYER_MOVE_DOWN_ALT);
        int pickupItem = KeyMap.getKeyCode(KeyMap.Key.PICKUP_ITEM);
        int waitTurn = KeyMap.getKeyCode(KeyMap.Key.PLAYER_SKIP_TURN);

        if (key == 'T') {
            Game.player.levelUp();
            Game.player.addPointToAttribute(Player.DEFENSE);
        }

        if (Game.lootWindow.isActive()) {
            Game.lootWindow.keyPressed(key);
            clearFocus();
            return;
        } // if

        if (key == moveLeft || key == moveLeftAlt) {
            move(false, -1, 0);
        } // if
        else if (key == moveRight || key == moveRightAlt) {
            move(false, 1, 0);
        } // else if
        else if (key == moveUp || key == moveUpAlt) {
            move(false, 0, -1);
        } // else if
        else if (key == moveDown || key == moveDownAlt) {
            move(false, 0, 1);
        } // else if
        else if (key == pickupItem) {
            System.err.println("pickin up shit");
            pickUpItem();
        } // else if
        else if (key == waitTurn) {
            waitTurn();
        } // else if
        else {
            System.err.println("wtf r u doing here??!: '" + (char) key + "'");
        }
    } // keyPressed

    /**
     * might be taken out
     */
    public void displayZoneInfo() {
        displayInfo = !displayInfo;
    } // displayZoneInfo

    /**
     * Introduce an Entity e into the world.
     *
     * @param e
     */
    public void addEntity(Entity e) {
        entityList.add(e);
    } // addLoot

    /**
     * Remove an Entity e from the list of Entities to be drawn.
     *
     * @param e
     */
    public void removeEntity(Entity e) {
        entityList.remove(e);
    } // removeEntity

    /**
     * Add loot to the world at
     *
     * @x,
     * @y.
     * @param item the item to be added
     * @param x the x coordinate of the item
     * @param y the y coordinate of the item
     */
    public void addLoot(Item item, int x, int y) {
        addLoot(new Loot(item, x, y));
    } // addLoot

    /**
     * Add a loot object to the world
     *
     * @param l the loot object to be added
     */
    public void addLoot(Loot l) {
        lootList.add(l);
    } // addLoot

    /**
     * Remove a loot object from the world, such as when the player picks up the
     * object.
     *
     * @param l the loot to be removed
     */
    public void removeLoot(Loot l) {
        lootList.remove(l);
    } // removeLoot

    /**
     * Add a Doodad to the list of Doodads in the world
     *
     * @param doodad
     */
    public void addDoodad(Doodad doodad) {
        doodadList.add(doodad);
    } // addDoodad

    /**
     * Remove doodad from the list of Doodads
     *
     * @param doodad
     */
    public void removeDoodad(Doodad doodad) {
        doodadList.remove(doodad);
    } // removeDoodad

    /**
     * Kept for convenience. This is the X coordinate of the player's location
     *
     * @return the x coordinate of the player
     */
    public int getPlayerX() {
        return playerX;
    } // getPlayerX

    /**
     * Kept for convenience. This is the Y coordinate of the player's location
     *
     * @return the y coordinate of the player
     */
    public int getPlayerY() {
        return playerY;
    } // getPlayerY

    /**
     * Used in Screen.java to draw the Entities to the screen
     *
     * @return
     */
    public EntityList getEntities() {
        return entityList;
    } // getEntities

    /**
     *
     * @return the list of loot in the world
     */
    public LootList getLootList() {
        return lootList;
    } // getLootList

    /**
     *
     * @return the list of Doodads in the world
     */
    public ArrayList<Doodad> getDoodadList() {
        return doodadList;
    } // getDoodadList

    /**
     *
     * @param x the x coordinate of the Entity, with respect to the pixel
     * coordinate of the Screen
     * @param y the y coordinate of the Entity, with respect to the pixel
     * coordinate of the Screen
     * @return the Entity at the locationi defined by
     * @x,
     * @y
     */
    public Entity getEntityAt(int x, int y) {
        return entityList.get(x, y);
    } // getEntityAt

    /**
     * Returns the top loot object at x, y if one exists.
     *
     * @param x
     * @param y
     * @return
     */
    public Loot getLootAt(int x, int y) {
        ArrayList<Loot> list = lootList.getList(x, y);
        return list.isEmpty() ? null : list.get(list.size() - 1);
    } // getLootAt

    /**
     * Attempts to pick up loot directly beneath the player. If one loot object
     * is found, it is picked up: i.e., added to the player's inventory and
     * removed from the world. If there is more than one loot object, the Loot
     * window appears and the player must choose which item(s) to pick up. If no
     * loot object is found, a message is printed to the screen informing the
     * player that no object exists.
     */
    public void pickUpItem() {
        ArrayList<Loot> lootpile = lootList.getList(playerX, playerY);

        if (lootpile != null && lootpile.size() > 0) {
            if (lootpile.size() > 1) {
                Game.lootWindow.setPosition(mouseX, mouseY);
                Game.lootWindow.setItemList(lootList.getListAsItem(playerX, playerY));
                Game.lootWindow.setActive();
            } // if
            else {
                PLAYER.interactWith(lootpile.get(0));
            } // else
        } // if
        else {
            log.writeFormattedString("There is nothing to pick up.", Common.FONT_NULL_RGB);
        } // else

        tick();
    } // pickUpItem

    /**
     * Causes the world to tick without the player having to perform some
     * action.
     */
    public void waitTurn() {
        log.writeFormattedString("You wait a turn.", Common.FONT_NULL_RGB);
        tick();
    } // waitTurn

    /**
     * Supply a change in x and y for the player to move. If isRanged is true
     * the player will not move and instead will fire a projectile in the
     * direction supplied.
     *
     * @param isRanged unused
     * @param deltaX the change in x of the player's location
     * @param deltaY the change in y of the player's location
     */
    public void move(boolean isRanged, int deltaX, int deltaY) {
        // Hopefully this will be taken care of in a better fashion
        Entity entity;
        ArrayList<Loot> loot;

        // debug
        if (isRanged) {
            deltaY *= 20;
            deltaX *= 20;
        }

        deltaX *= Common.TILE_SIZE;
        deltaY *= Common.TILE_SIZE;

        // Move the player if possible
        entity = entityList.get(playerX + deltaX, playerY + deltaY);

        // interact with any entities in the next tile space
        if (entity != null) {
            PLAYER.interactWith(entity);
        } // if
        else if (canMove(playerX + deltaX, playerY + deltaY)) {
            map.shiftWorldBy(-deltaX, -deltaY);
            shiftSpritesBy(-deltaX, -deltaY);
            loot = lootList.getList(playerX, playerY);

            if (loot != null) {
                // Print all the loot on this tile to the log for the player to see
                for (int i = 0; i < loot.size(); i++) {
                    loot.get(i).onWalkOver();
                } // for
            } // if
        } // else if
        else {
            for (Doodad doodad : doodadList) {
                if (doodad.getXPos() == (playerX + deltaX)
                        && doodad.getYPos() == (playerY + deltaY)) {
                    PLAYER.interactWith(doodad);
                    tick();
                } // if
            } // for
            return;
        } // else

        tick();
    } // move

    /**
     * Move all sprites [entities and loot objects] by deltaX, deltaY to give
     * the appearance of a moved worldview.
     *
     * @param deltaX direction in x to shift
     * @param deltaY direction in y to shift
     */
    private void shiftSpritesBy(int deltaX, int deltaY) {
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).changePositionBy(deltaX, deltaY);
        } // for

        for (int i = 0; i < lootList.size(); i++) {
            lootList.get(i).changePositionBy(deltaX, deltaY);
        } // for

        for (Doodad doodad : doodadList) {
            doodad.changePositionBy(deltaX, deltaY);
        } // for
    } // shiftSpritesBy

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
    public boolean canMove(int x, int y) {
        boolean doodadBlocking = true;

        // Entity blocking path?
        if (entityList.get(x, y) != null) {
            return false;
        } // if

        // Path-blocking doodad?
        for (Doodad doodad : doodadList) {
            if (doodad.getXPos() == x && doodad.getYPos() == y) {
                doodadBlocking = !doodad.hasCollision();
                break;
            } // if
        } // for

        // Environment blocking path?
        return map.canMove(x / Common.TILE_SIZE, y / Common.TILE_SIZE) && doodadBlocking;
    } // canMove

    /**
     * Contains everything that needs to update when the player performs an
     * action, such as moving entities, decreasing buff timers, etc. Each Sprite
     * implements a tick() function which is called to perform whatever updates
     * it needs to.
     */
    public void tick() {
        PLAYER.tick();

        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).tick();
        } // for


        for (int i = 0; i < lootList.size(); i++) {
            if (lootList.get(i).getRemoveOnTick()) {
                removeLoot(lootList.get(i));
                continue;
            } // if

            lootList.get(i).tick();
        } // for
        mouseLastMoved = System.currentTimeMillis();

    } // tick

    /**
     * Renders the World and other information to the Game's Screen,
     *
     * @screen.
     * @param screen the screen to render to
     */
    public void render(Screen screen) {
//        renderZoneInfo(screen);
        renderSprites(screen);
        renderMouseLocation(screen);

//        // Render portraits!
//        renderPortraits(screen);

        if (popUp != null) {
            renderPopup(screen);
        } // if

    } // render

    /**
     * Displays the information about the zone for the player on
     *
     * @screen.
     * @param screen the screen that the zone information is drawn to
     */
    public void renderZoneInfo(Screen screen) {
        if (!displayInfo) {
            return;
        } // if

        int r, g, b;
        int zoneLevel = map.getZoneLevel();

        int infoStartX = 0;
        int infoStartY = Font.CHAR_HEIGHT * 3;
        int levelDiffRGB;
        double levelDifference = ((PLAYER.getLevel() * 1.0) / zoneLevel);

        r = 255;
        g = (int) (75 + ((255 - 75) * levelDifference));
        b = 75;

        if (g >= 255) {
            r = (int) (255 - (255 * (levelDifference - 1)));
            g = 255;
            b = 75;
        } // if

        levelDiffRGB = (r << 16) + (g << 8) + b;

        if (PLAYER.getLevel() >= (zoneLevel + 3)) {
            levelDiffRGB = Common.FONT_DEFAULT_RGB;
        } // if
        Font.drawOutlinedMessage(screen, "Zone: " + map.getZoneName(), Common.FONT_WHITE_RGB, false, infoStartX, infoStartY);
        Font.drawOutlinedMessage(screen, "Level: " + map.getZoneLevel(), levelDiffRGB, false, infoStartX, infoStartY + Font.CHAR_HEIGHT);
        Font.drawOutlinedMessage(screen, "Elites: " + map.getRemainingElites(), Common.FONT_WHITE_RGB, false, infoStartX, infoStartY + Font.CHAR_HEIGHT * 2);
    } // renderZoneInfo

    /**
     * Draw all entities (including the player) and the loot to the screen.
     *
     * @param screen
     */
    public void renderSprites(Screen screen) {
        // Draw doodads to the screen
        renderDoodads(screen);

        // Draw the loot to the screen
        renderLoot(screen);

        // Draw the entities to the screen
        renderEntities(screen);

        // Lastly, render the player to the screen on top of all other Tiles
        PLAYER.getTile().render(screen, playerX, playerY);

        Game.lootWindow.render(screen);
    } // renderSprites

    /**
     * Draws all visible Loot objects to the screen at their positions with
     * respect to the Map.
     *
     * @param screen the screen to draw to
     */
    private void renderLoot(Screen screen) {
        int x;
        int y;

        for (int i = lootList.size() - 1; i >= 0; i--) {
            x = lootList.get(i).getXPos();
            y = lootList.get(i).getYPos();

            // Continue if the loot is off screen
            if (x < 0 || y < 0 || (x + Common.TILE_SIZE) >= Game.getGameWidth()
                    || (y + Common.TILE_SIZE) >= Game.getGameHeight()) {
                continue;
            } // if

            // Render loot that is visible to the player
            if ((map.tileLightStatus(x, y) & Map.IS_VISIBLE) >> 8 == 1) {
                lootList.get(i).getTile().render(screen, x, y);
                lootList.get(i).setVisible(true);
            } // if
            /* Else, render loot that the player has seen before but is not 
             currently in sight.  Such loot is rendered slightly darker. */ else if ((map.tileLightStatus(x, y) & Map.RECENTLY_SEEN) >> 9 == 1) {
                lootList.get(i).getTile().renderShadedTile(screen, x, y);
                lootList.get(i).setRecentlySeen(true);
            } // else if
        } // for
    } // renderLoot

    /**
     * Draws all visible Entities to the screen at their positions with respect
     * to the Map.
     *
     * @param screen the screen to draw to
     */
    private void renderEntities(Screen screen) {
        int x;
        int y;
        int healthBarLength;

        for (int i = 0; i < entityList.size(); i++) {
            x = entityList.get(i).getXPos();
            y = entityList.get(i).getYPos();

            // Continue if the entity is off screen
            if (x < 0 || y < 0 || (x + Common.TILE_SIZE) >= Game.getGameWidth()
                    || (y + Common.TILE_SIZE) >= Game.getGameHeight()) {
                continue;
            } // if

            // Render entities that are visible to the player
            if ((map.tileLightStatus(x, y) & Map.IS_VISIBLE) >> 8 == 1) {
                entityList.get(i).getTile().render(screen, x, y);

                // Draw health bar underneath entities if they are damaged
                if (entityList.get(i).getHealthPoints() != entityList.get(i).getMaxHealth()) {
                    healthBarLength = (int) (entityList.get(i).getHealthPoints() / entityList.get(i).getMaxHealth() * Common.TILE_SIZE);

                    screen.drawRectangle(Common.THEME_BG_COLOR_RGB, x - 1, y + Common.TILE_SIZE - 2, Common.TILE_SIZE + 1, 3);
                    screen.drawRectangle(Common.ENTITY_DAMAGED_HEALTH_RGB, x, y + Common.TILE_SIZE - 1, Common.TILE_SIZE - 1, 1);
                    screen.drawRectangle(Common.ENTITY_HEALTH_RGB, x, y + Common.TILE_SIZE - 1, healthBarLength, 1);
                } // if

                entityList.get(i).setVisible(true);
                entityList.get(i).setRecentlySeen(true);
            } // if
            /* Else, render entities that the player has seen before but are not
             currently in view.  Such entities are rendered slightly darker and
             their positions are not tracked until the entity comes back into
             view */ else if ((map.tileLightStatus(x, y) & Map.RECENTLY_SEEN) >> 9 == 1) {

                // Don't render before-seen entities off screen
                if (entityList.get(i).getLastKnownXPos() < 0 || entityList.get(i).getLastKnownYPos() < 0
                        || entityList.get(i).getLastKnownXPos() >= Game.getGameWidth() || entityList.get(i).getLastKnownYPos() >= Game.getGameHeight()) {
                    continue;
                } // if

                /* If the entity is still not visible, draw its last known position 
                 otherwise, if it's last seen location is uncovered and the
                 entity has moved, the entity's last location is no longer
                 known to the player.  */
                if ((map.tileLightStatus(entityList.get(i).getLastKnownXPos(), entityList.get(i).getLastKnownYPos()) & Map.IS_VISIBLE) >> 8 != 1) {
                    entityList.get(i).getTile().renderShadedTile(screen, entityList.get(i).getLastKnownXPos(), entityList.get(i).getLastKnownYPos());
                } // if

                entityList.get(i).setVisible(false);
                entityList.get(i).setRecentlySeen(true);
            } // else if
            else {
                entityList.get(i).setVisible(false);
                entityList.get(i).setRecentlySeen(false);
            } // else
        } // for
    } // renderEntities

    /**
     * Draws all visible Doodads to the screen at their positions with respect
     * to the Map.
     *
     * @param screen the screen to draw to
     */
    private void renderDoodads(Screen screen) {
        int x;
        int y;

        for (Doodad doodad : doodadList) {
            x = doodad.getXPos();
            y = doodad.getYPos();

            if (x < 0 || y < 0 || (x + Common.TILE_SIZE) >= Game.getGameWidth()
                    || (y + Common.TILE_SIZE) >= Game.getGameHeight()) {
                continue;
            } // if

            if ((map.tileLightStatus(x, y) & Map.IS_VISIBLE) >> 8 == 1) {
                doodad.getTile().render(screen, x, y);
                doodad.setVisible(true);
            } // if
            else if ((map.tileLightStatus(x, y) & Map.RECENTLY_SEEN) >> 9 == 1) {
                doodad.getTile().renderShadedTile(screen, x, y);
                doodad.setRecentlySeen(true);
            } // else if
        } // for
    } // renderDoodads

    /**
     * Draw Entity portraits to the screen, such as the Player's and Entities
     * that the user's mouse is hovering over.
     *
     * @param screen the screen to draw to
     */
    public void renderPortraits(Screen screen) {
        int playerPortraitOffsX = 1;
        int playerPortraitOffsY = 1;
        int entityPortraitOffsX = playerPortraitOffsX;
        int entityPortraitOffsY;
        int entityOffset = 0;

        Portrait playerPortrait = new Portrait(Tile.PLAYER_TILE_ID, PLAYER, Portrait.PLAYER);
        playerPortrait.render(screen, playerPortraitOffsX, playerPortraitOffsY);

        entityPortraitOffsY = playerPortrait.getHeight() + 2;

        for (int i = 0; i < entityList.size(); i++) {
            if (!entityList.get(i).isVisible()) {
                continue;
            }
            Portrait p2 = new Portrait(Tile.PLAYER_TILE_ID, entityList.get(i), Portrait.NON_ELITE);
            p2.render(screen, entityPortraitOffsX, entityPortraitOffsY + (p2.getHeight() + 2) * (entityOffset++));
        } // for
    } // renderPortraits

    /**
     * Displays where the World sees the user's cursor.
     *
     * @param screen the screen to draw to
     */
    private void renderMouseLocation(Screen screen) {
        if (!isActive() || Game.lootWindow.isActive()) {
            clearFocus();
            return;
        } // if

        String msg;
        int color = Common.WORLD_TILE_SELECT_GOOD;
        int msgX = mouseX;
        int msgY = mouseY - Common.TILE_SIZE;

        // Mouse is hovering over some loot
        if (lootList.get(mouseX, mouseY) != null && lootList.get(mouseX, mouseY).isVisible()) {
            msg = lootList.get(mouseX, mouseY).getDisplayName();
        } // if
        // Mouse is hovering over an entity
        else if (entityList.get(mouseX, mouseY) != null && entityList.get(mouseX, mouseY).isVisible()) {
            msgY -= Font.CHAR_HEIGHT;
            msg = entityList.get(mouseX, mouseY).getDisplayName() + "\nlevel:" + entityList.get(mouseX, mouseY).getLevel();
        } // if
        // Mouse is hovering over the player
        else if (mouseX == playerX && mouseY == playerY) {
            msgY -= Font.CHAR_HEIGHT;
            msg = "player\nlookin' good";
        } // if
        // If the tile is visible to the player
        else if ((map.tileLightStatus(mouseX, mouseY) & Map.IS_VISIBLE) >> 8 == 1) {
            msg = Tile.getTile(map.getTileIdAt(mouseX / Common.TILE_SIZE + map.getXStart(), mouseY / Common.TILE_SIZE + map.getYStart()) & 0xFF).toString();
            // Mouse is hovering over a path-blocking object
            if (Tile.getTile(map.getTileIdAt(mouseX / Common.TILE_SIZE + map.getXStart(), mouseY / Common.TILE_SIZE + map.getYStart()) & 0xFF).hasCollision()) {
                color = Common.WORLD_TILE_SELECT_BAD;
            } // if
        } // if
        else if ((map.tileLightStatus(mouseX, mouseY) & Map.RECENTLY_SEEN) >> 9 == 1) {
            msg = "unseen terrain";
            color = Common.WORLD_TILE_SELECT_BAD;
        } // else if
        // Tile is not visible to the player
        else {
            msg = "cannot move here";
            color = Common.WORLD_TILE_SELECT_BAD;
        } // else

        // Mouse is hovering over a doodad
        for (Doodad doodad : doodadList) {
            if (doodad.getXPos() == mouseX && doodad.getYPos() == mouseY && doodad.isVisible()) {
                if (doodad instanceof Container) {
                    msg = "open";
                    color = Common.WORLD_TILE_SELECT_GOOD;
                } // if
            } // if
        } // for

        // If mouse is left hovering for at least 1 second
        if (System.currentTimeMillis() - mouseLastMoved > 1000) {
            msg = "right click for more info";
//            msgY -= Font.CHAR_HEIGHT;
        } // if

        if (msgY < 0) {
            msgY = mouseY + Common.TILE_SIZE;
        } // if

        if (mouseY >= (Game.screen.getScreenHeight() - Common.TILE_SIZE)) {
            mouseY = mouseY - Common.TILE_SIZE;
        } // if

        Font.drawOutlinedMessage(screen, msg, Common.FONT_WHITE_RGB, false, msgX, msgY);
        screen.drawRectangle(color, mouseX, mouseY, Common.TILE_SIZE, Common.TILE_SIZE);
    } // renderMouseLocation

    private void renderPopup(Screen screen) {
        popUp.init();
        popUp.render(screen);
    } // renderPopup
} // World

//class PathFinder implements Runnable {
//
//    private boolean running = false;
//    private int deltaX;
//    private int deltaY;
//
//    public void stop() {
//        running = false;
//    }
//
//    public void start() {
//        running = true;
//
//        deltaX = Game.world.xx;
//        deltaY = Game.world.yy;
//    }
//
//    @Override
//    public void run() {
//        while (running) {
//
//            for (int x = 0; x < Math.abs(deltaX); x++) {
//                try {
//                    if (deltaX > 0) {
//                        Game.world.move(false, 1, 0);
//                    } // if
//                    else {
//                        Game.world.move(false, -1, 0);
//                    } // else
//
//                    Thread.sleep(20);
//                } // for
//                catch (InterruptedException ex) {
//                }
//            } // for
//
//
//            for (int y = 0; y < Math.abs(deltaY); y++) {
//                try {
//                    if (deltaY > 0) {
//                        Game.world.move(false, 0, 1);
//                    } else {
//                        Game.world.move(false, 0, -1);
//                    }
//
//                    Thread.sleep(20);
//                } // for
//                catch (InterruptedException ex) {
//                }
//            } // for
//
//            running = false;
//        }
//    }
//
//    // maybe move this function later
//    private void pathFind(int deltaX, int deltaY) {
//
//        for (int x = 0; x < Math.abs(deltaX) && running; x++) {
//            try {
//                if (deltaX > 0) {
//                    Game.world.move(false, 1, 0);
//                } // if
//                else {
//                    Game.world.move(false, -1, 0);
//                } // else
//
//                Thread.sleep(200);
//            } // for
//            catch (InterruptedException ex) {
//            }
//        } // for
//
//
//        for (int y = 0; y < Math.abs(deltaY) && running; y++) {
//            try {
//                if (deltaY > 0) {
//                    Game.world.move(false, 0, 1);
//                } else {
//                    Game.world.move(false, 0, -1);
//                }
//
//                Thread.sleep(200);
//            } // for
//            catch (InterruptedException ex) {
//            }
//        } // for
//
//        System.err.println("finished!!");
//        running = false;
//    } // pathFind
//}
