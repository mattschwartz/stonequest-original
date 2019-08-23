/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        InventoryMenu.java
 * Author:           Matt Schwartz
 * Date created:     04.20.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove
 *                   credit from code that was not written fully by yourself.
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description:
 **************************************************************************** */
package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Armor;
import com.barelyconscious.game.item.Food;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Key;
import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.item.Projectile;
import com.barelyconscious.game.item.Scroll;
import com.barelyconscious.game.item.Weapon;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.game.player.activeeffects.PotionEffect;
import com.barelyconscious.gui.IRenderable;
import com.barelyconscious.gui.IWidget;

import java.awt.Color;

public class InventoryMenu extends Interactable
    implements IWidget, IRenderable {

    // Inventory bag selector
    private final int BAG_OFFS_X = 0;
    private final int BAG_OFFS_Y = 18;
    private final int BAG_SLOT_WIDTH = 20;
    private final int BAG_SLOT_HEIGHT = 20;
    private final int BAG_SELECT_NOTIFY_WIDTH = 76;

    private final int INVENTORY_FRAME_WIDTH = 202;
    private final int INVENTORY_FRAME_HEIGHT = 140;
    private final int INVENTORY_FRAME_OFFS_X = 20;
    private final int INVENTORY_FRAME_OFFS_Y = 0;
    private final int NUM_ITEM_SLOTS_ROW = 4;
    private final int NUM_ITEM_SLOTS_COL = 2;
    private final int MAX_NUM_BAG_SLOTS = 5;
    private final int ITEM_SLOT_OFFS_X = BAG_OFFS_X + BAG_SLOT_WIDTH + 9;
    private final int ITEM_SLOT_OFFS_Y = 26;
    private final int ITEM_SLOT_WIDTH = 44;
    private final int ITEM_SLOT_HEIGHT = 44;
    private final int GOLD_OFFS_X = INVENTORY_FRAME_OFFS_X + 58;
    private final int GOLD_OFFS_Y = INVENTORY_FRAME_OFFS_Y + 118;
    private final int GOLD_BAR_WIDTH = 59;
    private final int GOLD_BAR_HEIGHT = 22;

    // Item info frame top - used for the name of the item
    private final int ITEM_INFO_FRAME_WIDTH = 200;
    private final int ITEM_INFO_FRAME_HEIGHT = 32;
    private final int ITEM_INFO_TITLE_OFFS_X = INVENTORY_FRAME_OFFS_X - ITEM_INFO_FRAME_WIDTH - BAG_SLOT_WIDTH - 2;//INVENTORY_FRAME_OFFS_X + 2;
    private final int ITEM_INFO_TITLE_OFFS_Y = INVENTORY_FRAME_OFFS_Y;//INVENTORY_FRAME_OFFS_Y + INVENTORY_FRAME_HEIGHT - 8;
    private final int ITEM_DESCRIPTION_OFFS_X = ITEM_INFO_TITLE_OFFS_X;
    private final int ITEM_DESCRIPTION_OFFS_Y = ITEM_INFO_TITLE_OFFS_Y + ITEM_INFO_FRAME_HEIGHT - 2;
    private final int ITEM_DESCRIPTION_WIDTH = 177;

    // Item info frame bottom - used for the type of item
    private final int ITEM_TYPE_OFFS_X = 10;
    private final int ITEM_TYPE_OFFS_Y = 7;
    private final int ITEM_TYPE_FRAME_WIDTH = 180;
    private final int ITEM_TYPE_FRAME_HEIGHT = 22;

    private final int INVENTORY_BORDER_COLOR = new Color(183, 183, 183).getRGB();
    private final int INVENTORY_BACKGROUND_COLOR = new Color(33, 33, 33).getRGB();

    private int currentSelectedBag = 0;

    private boolean hasFocus = false;


    private int bagSlot = 0;

    private int xOffs;
    private int yOffs;
    private int inventoryWidth;
    private int inventoryHeight;
    /**
     * A value of -1 means the cursor is not within the Inventory frame, any value greater than -1 means the cursor is
     * hovering over an Item within the inventory.
     */
    private int selectedItem = -1;
    /**
     * Number of inventory slots in a row.
     */
    private final int SLOTS_WIDTH = 11;
    /**
     * Number of inventory slots in a column.
     */
    private final int SLOTS_HEIGHT = 5;
    /**
     * True if the cursor is hovering over the Inventory Tab UIElement.
     */
    private boolean inventoryTabHoverOver = false;
    private boolean showInventory = true;
    private final String SHOW_MESSAGE = "Show inventory";
    private final String HIDE_MESSAGE = "Hide inventory";
    private final Inventory playerInventory;
    private PopupMenu popUp = null;

    public InventoryMenu() {
        inventoryWidth = width = SLOTS_WIDTH * Common.TILE_SIZE + Common.TILE_SIZE;
        inventoryHeight = height = SLOTS_HEIGHT * Common.TILE_SIZE;
        this.playerInventory = Game.inventory;
    } // constructor

    /**
     * Reposition the InventoryMenu stays in the same relative location.
     *
     * @param width  the width of the Game
     * @param height the height of the Game
     */
    @Override
    public void resize(int width, int height) {
        xOffs = width - (INVENTORY_FRAME_WIDTH + BAG_SLOT_WIDTH) - 1;
//        yOffs = Game.textLog.getOffsY() - Game.textLog.getPixelHeight() - INVENTORY_FRAME_HEIGHT;//height - Game.textLog.getPixelHeight() - inventoryHeight - Font.CHAR_HEIGHT * 2 - Common.TILE_SIZE+10;
        yOffs = height - INVENTORY_FRAME_HEIGHT;

        defineMouseZone(xOffs, yOffs, INVENTORY_FRAME_WIDTH + BAG_SLOT_WIDTH, INVENTORY_FRAME_HEIGHT);
    } // resize

    /**
     * Tracks the location of the Mouse if it is within the Inventory and performs actions within the Inventory based on
     * its location.
     *
     * @param x the x-coordinate of the mouse cursor
     * @param y the y-coordinate of the mouse cursor
     */
    @Override
    public void mouseMoved(int x, int y) {
        int xPos = x - xOffs;
        int yPos = y - yOffs;

        selectedItem = 9;
        currentSelectedBag = bagSlot;
        hasFocus = true;

        // Check if cursor is hovering over a bag slot
        if ((xPos >= BAG_OFFS_X && xPos < BAG_OFFS_X + BAG_SLOT_WIDTH)
            && (yPos >= BAG_OFFS_Y && yPos <= BAG_OFFS_Y + MAX_NUM_BAG_SLOTS * (BAG_SLOT_HEIGHT + 1))) {
            currentSelectedBag = (yPos - BAG_OFFS_Y) / (BAG_SLOT_HEIGHT + 1);
        } // if

        // Check if cursor is hovering over an item slot
        if ((xPos >= ITEM_SLOT_OFFS_X && xPos < ITEM_SLOT_OFFS_X + (ITEM_SLOT_WIDTH + 3) * NUM_ITEM_SLOTS_ROW)
            && (yPos >= ITEM_SLOT_OFFS_Y && yPos <= ITEM_SLOT_OFFS_Y + (ITEM_SLOT_HEIGHT + 3) * NUM_ITEM_SLOTS_COL)) {
            selectedItem = (xPos - ITEM_SLOT_OFFS_X) / (ITEM_SLOT_WIDTH + 3);
            selectedItem += ((yPos - ITEM_SLOT_OFFS_Y) / (ITEM_SLOT_HEIGHT + 3)) * NUM_ITEM_SLOTS_ROW;
        } // if


//        int xLocation = x - xOffs;
//        int yLocation = y - yOffs;
//        int inventoryTabWidth = Common.TILE_SIZE;
//
//        int invTabMinX = 0;
//
//        if (inventoryTabHoverOver) {
//            invTabMinX = HIDE_MESSAGE.length() * Font.CHAR_WIDTH;
//        } // if
//
//        if (showInventory) {
//            // If cursor is hovering over inventory tab
//            if (xLocation <= inventoryTabWidth && xLocation >= -invTabMinX
//                    && yLocation <= Common.TILE_SIZE) {
//                inventoryTabHoverOver = true;
//                selectedItem = -1;
//            } // if
//            else if (xLocation > inventoryTabWidth) {
//                inventoryTabHoverOver = false;
//                selectedItem = (xLocation - Common.TILE_SIZE - 1)
//                        / Common.TILE_SIZE;
//                selectedItem += ((yLocation) / Common.TILE_SIZE) * SLOTS_WIDTH;
//            } // else
//            else {
//                selectedItem = -1;
//                inventoryTabHoverOver = false;
//            }
//        } // if
//        else {
//            xLocation = x - xStart;
//
//            if (xLocation >= 0) {
//                inventoryTabHoverOver = true;
//            } // if
//            else {
//                inventoryTabHoverOver = false;
//            } // else
//        } // else
//
//        redefineMouseZone();
//        super.mouseMoved(x, y);
    } // mouseMoved

    /**
     * Redefines the area in which the cursor will cause an action depending on the state of the Inventory frame.
     */
    private void redefineMouseZone() {
        if (showInventory) {
            if (inventoryTabHoverOver) {
                defineMouseZone(xOffs - Common.TILE_SIZE - HIDE_MESSAGE.length() * Font.CHAR_WIDTH, yOffs, inventoryWidth + Common.TILE_SIZE + HIDE_MESSAGE.length() * Font.CHAR_WIDTH, inventoryHeight);
            } // if
            else if (!inventoryTabHoverOver) {
                defineMouseZone(xOffs - Common.TILE_SIZE, yOffs, inventoryWidth + Common.TILE_SIZE, inventoryHeight);
            } // else if
        } // if
        else {
            if (inventoryTabHoverOver) {
                defineMouseZone(xOffs + inventoryWidth - Common.TILE_SIZE - SHOW_MESSAGE.length() * Font.CHAR_WIDTH, yOffs, Common.TILE_SIZE + SHOW_MESSAGE.length() * Font.CHAR_WIDTH + Font.CHAR_WIDTH, Common.TILE_SIZE);
            } // if
            else if (!inventoryTabHoverOver) {
                defineMouseZone(xOffs + inventoryWidth - Common.TILE_SIZE, yOffs, Common.TILE_SIZE + Font.CHAR_WIDTH, Common.TILE_SIZE);
            } // else if
        } // else
    } // redefineMouseZone

    /**
     * Called when the mouse is clicked within the Inventory and performs an action depending on where the mouse is
     * clicked.
     *
     * @param button left or right button on the mouse
     * @param x      the x-coordinate where the mouse is clicked
     * @param y      the y-coordinate where the mouse is clicked
     */
    @Override
    public void mouseClicked(int button, int x, int y) {
        if (bagSlot != currentSelectedBag) {
            bagSlot = currentSelectedBag;
        } // if

        if (selectedItem < 9) {
            parentCallback(0);
        } // if

//        Item item;
//
//        if (inventoryTabHoverOver) {
//            showInventory = !showInventory;
//
//            if (showInventory) {
//                expandInventory();
//            } // if
//            else if (!showInventory) {
//                collapseInventory();
//            } // else if
//        } // if
//
//        if (showInventory) {
//            if (button == MouseHandler.LEFT_CLICK) {
//                parentCallback(0);
//            } // else if
//            else if (button == MouseHandler.RIGHT_CLICK) {
//                if ((item = playerInventory.getItemAt(selectedItem)) != null) {
//                    popUp = new PopupMenu(mouseX, mouseY, this,
//                            item.getOptionText(0) + " " + item.getDisplayName(),
//                            item.getOptionText(1), item.getOptionText(2),
//                            item.getOptionText(3));
//                } // if
//                else {
//                    popUp = new PopupMenu(mouseX, mouseY, this);
//                } // else
//
//                Sound.RIGHT_BUTTON_CLICK.play();
//                disableMouse();
//            } // else if
//        } // if
//
//        redefineMouseZone();
//        mouseMoved(x, y);
    } // mouseClicked

    /**
     * Function is called when the mouse exits the Inventory menu.
     */
    @Override
    public void mouseExited() {
        hasFocus = false;
        selectedItem = 9;
        currentSelectedBag = bagSlot;
//        inventoryTabHoverOver = false;
//        selectedItem = -1;
    } // mouseExited

    /**
     * Removes the Inventory from consideration when the user moves or clicks the mouse.
     */
    @Override
    public void disableMouse() {
//        Game.mouseHandler.removeHoverableListener(this);
//        Game.mouseHandler.removeClickableListener(this);
    } // disableMouse

    /**
     * Adds the Inventory back into consideration when the user moves or clicks the mouse.
     */
    @Override
    public void enableMouse() {
//        Game.mouseHandler.registerHoverableListener(this);
//        Game.mouseHandler.registerClickableListener(this);
//        popUp = null;
    } // enableMouse

    /**
     * Allows the pop-up menu to let the super class know what option the user has clicked via
     *
     * @param ret the integer value of the option number returned from the pop- up menu
     * @ret.
     */
    @Override
    public void parentCallback(int ret) {
        switch (ret) {
            case 0:
                System.err.println("using item at: " + selectedItem);
                Game.world.tick();
                playerInventory.useItem(selectedItem + bagSlot * 8);
                break;
            case 1:
                playerInventory.examineItem(selectedItem);
                return;
            case 2:

                Game.world.tick();
                playerInventory.dropItem(selectedItem);
                break;
            case 3:

                Game.world.tick();
                playerInventory.salvageItem(selectedItem);
                break;
        } // switch
    } // parentCallback

    /**
     * Collapses the Inventory frame, hiding its contents from view so that the user can get a more full view of the
     * world.
     */
    private void collapseInventory() {
        // Redefine mouse zone for the minimized Inventory UIElement 
        defineMouseZone(xOffs + inventoryWidth - Common.TILE_SIZE, yOffs, Common.TILE_SIZE, Common.TILE_SIZE);
    } // collapseInventory

    /**
     * Expands the Inventory frame, displaying its contents for the user.
     */
    private void expandInventory() {
        // Defines mouse zone for full-sized Inventory frame
        defineMouseZone(xOffs, yOffs, inventoryWidth, inventoryHeight);
    } // expandInventory

    public void animation(Screen screen, long time) {
    } // animation

    /**
     * Draws the inventory frame to the screen
     *
     * @param screen
     */
    public void render(Screen screen) {
        UIElement.INVENTORY_FRAME.render(screen, xOffs + INVENTORY_FRAME_OFFS_X, yOffs + INVENTORY_FRAME_OFFS_Y);

        renderBagSlots(screen);
        renderText(screen);
        renderItems(screen);
        renderItemInfo(screen);
    } // render

    private void renderItems(Screen screen) {
        int x;
        int y;
        int slotOffset = 2;
        int itemSlot;
        String itemStack;
        Item item;

        for (int i = 0; i < 8; i++) {
            itemSlot = (i + bagSlot * 8);
            x = xOffs + ITEM_SLOT_OFFS_X + (itemSlot % NUM_ITEM_SLOTS_ROW) * (ITEM_SLOT_WIDTH + slotOffset);
            y = yOffs + ITEM_SLOT_OFFS_Y + (int) Math.ceil((i / NUM_ITEM_SLOTS_ROW)) * (ITEM_SLOT_HEIGHT + slotOffset);

            if (selectedItem == i) {
                UIElement.INVENTORY_ITEM_SELECT_HIGHLIGHT.renderHighlighted(screen, x, y);
            } // if

            if ((item = playerInventory.getItemAt((i + bagSlot * 8))) == null) {
                continue;
            } // if

            if (item instanceof Armor) {
                if (((Armor) item).isEquipped()) {
                    Font.drawOutlinedMessage(screen, "E", Common.FONT_WHITE_RGB, false, x + ITEM_SLOT_WIDTH - Font.CHAR_WIDTH - 5, y + 7);
                } // if
            } // if

            if (item instanceof Weapon) {
                if (((Weapon) item).isEquipped()) {
                    Font.drawOutlinedMessage(screen, "E", Common.FONT_WHITE_RGB, false, x + ITEM_SLOT_WIDTH - Font.CHAR_WIDTH - 5, y + 7);
                } // if
            } // if

            Tile.getTile(item.getTileId()).render(screen, x + (ITEM_SLOT_WIDTH - Common.TILE_SIZE) / 2, y + (ITEM_SLOT_HEIGHT - Common.TILE_SIZE) / 2);
            if (item.getStackSize() > 1) {
                itemStack = "" + item.getStackSize();
                Font.drawOutlinedMessage(screen, itemStack, Common.FONT_WHITE_RGB, false, x + ITEM_SLOT_WIDTH - itemStack.length() * Font.CHAR_WIDTH - 5, y + ITEM_SLOT_HEIGHT - Font.CHAR_HEIGHT - 5);
            } // if
//            Tile.getTile(item.getTileId()).renderScaled(screen, x, y, 2);
        }
    }

    /**
     * Render the bag slot icons that appear on the left side of the Inventory frame
     *
     * @param screen the screen to draw to
     */
    private void renderBagSlots(Screen screen) {
        int x;
        int y;
        int slotOffset = 1;

        for (int i = 0; i < MAX_NUM_BAG_SLOTS; i++) {
            x = xOffs + BAG_OFFS_X - slotOffset;
            y = yOffs + BAG_OFFS_Y + i * (BAG_SLOT_HEIGHT + slotOffset);

            if (i == currentSelectedBag || i == bagSlot) {
                UIElement.INVENTORY_BAG_SELECT_ELEMENT.renderHighlighted(screen, x, y);
                if (i != bagSlot) {
                    UIElement.INVENTORY_BAG_SELECT_NOTIFY.renderHighlighted(screen, x - BAG_SELECT_NOTIFY_WIDTH - 2, y);
                } // if
            } // if
            else {
                UIElement.INVENTORY_BAG_SELECT_ELEMENT.render(screen, x, y);
            } // else
        } // for


    } // renderBagSlots

    /**
     * Render elements that require text to be drawn on top of the inventory to @screen.
     *
     * @param screen the screen to draw the elements to
     */
    private void renderText(Screen screen) {
        int x;
        int y;
        int slotOffset = 1;
        String text;

        // Draw the player's amount of gold
        text = "$" + Common.formatNumber(playerInventory.getGold());
        x = xOffs + GOLD_OFFS_X + (GOLD_BAR_WIDTH - text.length() * Font.CHAR_WIDTH);
        y = yOffs + GOLD_OFFS_Y + (GOLD_BAR_HEIGHT - Font.CHAR_HEIGHT) / 2;

        Font.drawOutlinedMessage(screen, text, Common.GOLD_AMOUNT_TEXT_RGB, false, x, y);

        // Render bag identifying numbers
//        for (int i = 0; i < MAX_NUM_BAG_SLOTS; i++) {
//            text = "" + ( i + 1);
//            x = xOffs + BAG_OFFS_X + (BAG_SLOT_WIDTH - Font.CHAR_WIDTH) / 2 - slotOffset;
//            y = yOffs + BAG_OFFS_Y + i * (BAG_SLOT_HEIGHT + slotOffset) + (BAG_SLOT_HEIGHT - Font.CHAR_HEIGHT) / 2;
//            
//            if (i == currentSelectedBag || i == bagSlot) {
//                if (i != bagSlot) {
//                    Font.drawOutlinedMessage(screen, "Open Bag?", Common.FONT_DEFAULT_RGB, false, xOffs + BAG_OFFS_X - "Open Bag? ".length() * Font.CHAR_WIDTH, y);
//                } // if
//                Font.drawOutlinedMessage(screen, text, Common.FONT_WHITE_RGB, false, x, y);
//            }
//            else {
//                Font.drawOutlinedMessage(screen, text, Common.FONT_DEFAULT_RGB, false, x, y);
//            } // else
//        } // for
    } // renderText

    private void renderItemInfo(Screen screen) {
        int x;
        int y;
        String title;
        Item item;

        if (!hasFocus || selectedItem > 7) {
            return;
        } // if

        item = playerInventory.getItemAt(selectedItem + bagSlot * 8);

        if (item == null) {
            return;
        } // if

        x = xOffs + ITEM_INFO_TITLE_OFFS_X;
        y = yOffs + ITEM_INFO_TITLE_OFFS_Y;

        title = item.getDisplayName();

        if (title.length() <= 20) {
            UIElement.INVENTORY_ITEM_INFO_FRAME_TOP.render(screen, x, y);

            x += (ITEM_INFO_FRAME_WIDTH - title.length() * Font.CHAR_WIDTH) / 2;
            y += (ITEM_INFO_FRAME_HEIGHT - Font.CHAR_HEIGHT - 7) / 2;
            Font.drawMessage(screen, title, Common.themeForegroundColor, false, x, y);
        } // if

        else {
            title = title.substring(0, 20);
            UIElement.INVENTORY_ITEM_INFO_FRAME_TOP.render(screen, x, y);

            x += (ITEM_INFO_FRAME_WIDTH - title.length() * Font.CHAR_WIDTH) / 2;
            y += (ITEM_INFO_FRAME_HEIGHT - Font.CHAR_HEIGHT * 2 - 7) / 2;

            Font.drawMessage(screen, title, Common.themeForegroundColor, false, x, y);

            title = item.getDisplayName().substring(20);

            x = xOffs + ITEM_INFO_TITLE_OFFS_X + (ITEM_INFO_FRAME_WIDTH - title.length() * Font.CHAR_WIDTH) / 2;
            y += Font.CHAR_HEIGHT;

            Font.drawMessage(screen, title, Common.themeForegroundColor, false, x, y);
        } // else

        // Render item stats 
        renderItemStats(screen, item);
    } // renderItemInfo

    private void renderItemStats(Screen screen, Item item) {
        int x;
        int y;
        int itemDescriptionHeight;
        String msg;
        AttributeMod mod;

        x = xOffs + ITEM_DESCRIPTION_OFFS_X + (ITEM_INFO_FRAME_WIDTH - ITEM_DESCRIPTION_WIDTH) / 2;
        y = yOffs + ITEM_DESCRIPTION_OFFS_Y;
        itemDescriptionHeight = item.getNumAffixes() * (Font.CHAR_HEIGHT + 2) + 4;

        screen.fillRectangle(INVENTORY_BACKGROUND_COLOR, x, y, ITEM_DESCRIPTION_WIDTH, itemDescriptionHeight);
        screen.drawRectangle(INVENTORY_BORDER_COLOR, x, y, ITEM_DESCRIPTION_WIDTH, itemDescriptionHeight);

        y += 3;

        renderItemType(screen, item, itemDescriptionHeight);

        for (int i = 0; i < item.getNumAffixes(); i++) {
            mod = item.getAffixAt(i);
            msg = "" + (int) mod.getAttributeModifier();

            if (item instanceof Scroll && !Game.player.isScrollIdentified(((Scroll) item).getScrollId())) {
                Font.drawMessage(screen, "???", Common.FONT_DEFAULT_RGB, false, x + 2 + (ITEM_DESCRIPTION_WIDTH - "???".length() * Font.CHAR_WIDTH) / 2, y + i * (Font.CHAR_HEIGHT + 2));
            } // if
            else {
                Font.drawMessage(screen, "" + mod, Common.FONT_DEFAULT_RGB, false, x + 2, y + i * (Font.CHAR_HEIGHT + 2));

                if (mod.getAttributeModifier() < 0) {
                    Font.drawMessage(screen, "" + msg, Common.ENTITY_DAMAGED_HEALTH_RGB, false, x + 2 + (ITEM_DESCRIPTION_WIDTH - msg.length() * Font.CHAR_WIDTH) - 2, y + i * (Font.CHAR_HEIGHT + 2));
                } // if
                else {
                    Font.drawMessage(screen, "" + msg, Common.ENTITY_HEALTH_RGB, false, x + 2 + (ITEM_DESCRIPTION_WIDTH - msg.length() * Font.CHAR_WIDTH) - 2, y + i * (Font.CHAR_HEIGHT + 2));
                } // else
            }
        } // for

    } // renderItemStats

    private void renderItemType(Screen screen, Item item, int yStart) {
        int x = xOffs + ITEM_DESCRIPTION_OFFS_X;
        int y = yOffs + ITEM_DESCRIPTION_OFFS_Y + yStart - 1;
        String msg = "";

        UIElement.INVENTORY_ITEM_INFO_FRAME_BOTTOM.render(screen, x, y);

        // Render item type
        if (item instanceof Weapon) {
            msg = "weapon";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X + 2, y
                + ITEM_TYPE_OFFS_Y + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
            msg = ((Weapon) item).weaponTypeToString(((Weapon) item).getWeaponType());
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                + ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH, y + ITEM_TYPE_OFFS_Y
                + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
        } // if
        else if (item instanceof Armor) {
            msg = "armor";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X + 2, y
                + ITEM_TYPE_OFFS_Y + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
            msg = Armor.armorTypeToString(((Armor) item).getArmorType());
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                + ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH, y + ITEM_TYPE_OFFS_Y
                + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
        } // else if
        else if (item instanceof Potion) {
            if (((Potion) item).getEffects().getPotionType() == PotionEffect.STATBUFF) {
                msg = "Lasts for " + ((Potion) item).getEffects().getDurationInTicks() + " turns";
                Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                    + (ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH) / 2, y + ITEM_TYPE_OFFS_Y
                    + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
            } // if
            else if (((Potion) item).getEffects().getPotionType() == PotionEffect.ANTIMAGIC) {
                msg = "Cleanse afflictions";
                Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                    + (ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH) / 2, y + ITEM_TYPE_OFFS_Y
                    + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
            } // else if
            else if (((Potion) item).getEffects().getPotionType() == PotionEffect.ANTITOXIN) {
                msg = "Cure infections";
                Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                    + (ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH) / 2, y + ITEM_TYPE_OFFS_Y
                    + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
            } // else if
        } // else if
        else if (item instanceof Projectile) {
            msg = "projectile";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X + 2, y
                + ITEM_TYPE_OFFS_Y + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);

            msg = "" + item;
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                + ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH, y + ITEM_TYPE_OFFS_Y
                + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
        } // else if
        else if (item instanceof Food) {
            msg = "food";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                + ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH, y + ITEM_TYPE_OFFS_Y
                + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
        } // else if
        else if (item instanceof Scroll) {
            if (!Game.player.isScrollIdentified(((Scroll) item).getScrollId())) {
                msg = "unidentified ";
            }
            msg += "scroll";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X + 2, y
                + ITEM_TYPE_OFFS_Y + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
        } // else if
        else if (item instanceof Key) {
            msg += "key";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X + 2, y
                + ITEM_TYPE_OFFS_Y + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);

            msg = "unlock doors";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                + ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH, y + ITEM_TYPE_OFFS_Y
                + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
        } // else if
        else {
            msg = "junk";
            Font.drawMessage(screen, msg, Common.themeForegroundColor, false, x + ITEM_TYPE_OFFS_X - 2
                + ITEM_TYPE_FRAME_WIDTH - msg.length() * Font.CHAR_WIDTH, y + ITEM_TYPE_OFFS_Y
                + (ITEM_TYPE_FRAME_HEIGHT - Font.CHAR_HEIGHT) / 2 - 2);
        } // else

    } // renderItemType
} // InventoryMenu
