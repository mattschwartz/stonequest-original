/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ItemSlotArea.java
 * Author:           Matt Schwartz
 * Date created:     09.03.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Component;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyHandler;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.util.ColorHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class InventorySlotArea extends Interactable implements Component {

    protected static final UIElement ITEM_SLOT_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/itemSlot/itemSlot.png");
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int inventorySlotNumber;
    private boolean destroy = false;
    private Inventory playerInventory;

    public InventorySlotArea() {
    }

    /**
     * Create a new ItemSlotArea which represents one of the player's inventory
     * slots.
     *
     * @param delegate the InterfaceDelegate which allows communication with
     * other GUI elements, such as Tooltips
     * @param inventory the player's inventory which contains the items carried
     * by the player
     * @param slotNumber which slot within the player inventory that this
     * ItemSlotArea represents
     * @param startX the x coordinate of where to draw the slot
     * @param startY the y coordinate of where to draw the slot
     */
    public InventorySlotArea(Inventory inventory, int slotNumber, int startX, int startY) {
        inventorySlotNumber = slotNumber;
        x = startX;
        y = startY;
        width = ITEM_SLOT_BACKGROUND.getWidth();
        height = ITEM_SLOT_BACKGROUND.getHeight();

        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_TEXT_AREA);
    }

    public void resize(int startX, int startY) {
        x = startX;
        y = startY;

        super.setRegion(x, y, width, height);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int newX) {
        x = newX;
    }

    @Override
    public void setY(int newY) {
        y = newY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public boolean itemGoesHere(Item item) {
        return true;
    }

    public Item getItem() {
        return playerInventory.getItem(inventorySlotNumber);
    }

    public Item setItem(Item item) {
        Item oldItem;
        oldItem = playerInventory.setItem(inventorySlotNumber, item);

        return oldItem;
    }

    public Item removeItem() {
        Item item;
        item = playerInventory.removeItem(inventorySlotNumber);

        return item;
    }

    public void useItem() {
        Item item = getItem();

        if (item != null) {
            item.onUse();
        }
    }

    @Override
    public void dispose() {
        destroy = true;
    }

    @Override
    public boolean shouldRemove() {
        return destroy;
    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        Item item, cursorItem;
//
//        if (delegate.isSalvageActive()) {
//            TextLog.INSTANCE.append(getItem() + " salvaged.");
//            delegate.setSalvageActive(false);
//            return;
//        }
//
//        if ((e.getButton() == Interactable.MOUSE_LEFT_CLICK) && KeyHandler.isShiftKeyDown) {
//            cursorItem = delegate.getItemOnCursor();
//            item = getItem();
//
//            if (item == null) {
//                return;
//            }
//
//            if (cursorItem == null) {
//                int stackSize = item.getStackSize();
//                int split = (int) Math.ceil(stackSize * 1.0 / 2);
//
//                cursorItem = item.clone();
//                if (cursorItem != null) {
//                    cursorItem.setStackSize(stackSize - split);
//                    if (cursorItem.getStackSize() <= 0) {
//                        cursorItem = null;
//                    }
//                    item.setStackSize(split);
//                    setItem(item);
//                }
//
//                delegate.putItemOnCursor(cursorItem);
//            }
//        }
//        else if (e.getButton() == Interactable.MOUSE_LEFT_CLICK) {
//            if (stackItem()) {
//                return;
//            }
//
//            item = removeItem();
//
//            if (item != null) {
//                cursorItem = delegate.putItemOnCursor(item);
//
//                // swap items if there was already an item on the cursor
//                if (cursorItem != null) {
//                    setItem(cursorItem);
//                }
//            }
//            else {
//                item = delegate.putItemOnCursor(null);
//                playerInventory.setItem(inventorySlotNumber, item);
//            }
//        }
//        else {
//            useItem();
//        }
//    }

//    public boolean stackItem() {
//        Item item, cursorItem;
//        
//        cursorItem = delegate.getItemOnCursor();
//        item = getItem();
//
//        if (cursorItem != null && item != null && item.compareTo(cursorItem) == 0) {
//            item = getItem();
//            item.adjustStackBy(cursorItem.getStackSize());
//            delegate.putItemOnCursor(null);
//            setItem(item);
//            return true;
//        }
//        
//        return false;
//    }

    @Override
    public void mouseEntered() {
//        Item item;
//
//        super.mouseEntered();
//        item = getItem();
//
//        if (item == null) {
//            return;
//        }
//
//        delegate.setTooltipText(item + "\n" + item.getType());
    }

//    @Override
//    public void mouseExited() {
//        super.mouseExited();
//
//        delegate.clearTooltipText();
//    }

    @Override
    public void render() {
//        String stack;
//        Item item = getItem();
//
//        if (item != null) {
//            item.render(screen, x, y);
//
//            if (item.getStackSize() > 1) {
//                stack = "" + item.getStackSize();
//                screen.fillTransluscentRectangle(x + width - Font.getStringWidth(screen, stack) - 2, y + height - Font.CHAR_HEIGHT + 3, Font.getStringWidth(screen, stack), Font.CHAR_HEIGHT - 5);
//                Font.drawFont(screen, stack, Color.white, true, x + width - Font.getStringWidth(screen, stack) - 3, y + height - 3);
//            }
//
//            if (isMouseInFocus()) {
//                screen.drawRectangle(ColorHelper.TILE_SELECT_CAN_MOVE, x, y, width, height);
//            }
//        }
//        else {
//            ITEM_SLOT_BACKGROUND.render(screen, x, y);
//        }
//
//        item = delegate.getItemOnCursor();
//
//        if (item != null) {
//            if (isMouseInFocus()) {
//                if (itemGoesHere(item)) {
//                    screen.drawRectangle(ColorHelper.TILE_SELECT_CAN_MOVE, x, y, width, height);
//                }
//                else {
//                    screen.drawRectangle(ColorHelper.TILE_SELECT_CANNOT_MOVE, x, y, width, height);
//                }
//            }
//        }
    }
}
