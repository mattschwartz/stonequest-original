package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.shape.Box;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class ItemSlot extends Widget {

    @Getter
    @Nullable
    // todo: should item slot just get the item directly from inventory instead?
    private Item item;
    private final int inventorySlotId;

    private Inventory inventory;

    private boolean shouldShowTooltip() {
        return isMouseOver();
    }

    public ItemSlot(
        LayoutData layout,
        final @NonNull Inventory inventory,
        @Nullable final Item item,
        final int inventorySlotId
    ) {
        super(layout);
        this.item = item;
        this.inventory = inventory;
        this.inventorySlotId = inventorySlotId;

        addWidget(new TooltipWidget());

        MouseInputHandler.instance().registerInteractable(this, InputLayer.GUI);
    }

    private class TooltipWidget extends Widget {
        public TooltipWidget() {
            super(LayoutData.DEFAULT);
        }

        @Override
        protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
            Inventory.InventoryItem inventoryItem = inventory.getItem(inventorySlotId);
            if (inventoryItem == null || inventoryItem.item == null || !shouldShowTooltip()) {
                return;
            }

            final String name = inventoryItem.item.getName();
            final String description = inventoryItem.item.getDescription();

            final StringBuilder sb = new StringBuilder();
            sb.append(name);
            if (inventoryItem.stackSize > 1) {
                sb.append(" (").append(inventoryItem.stackSize).append(") ");
            }
            sb.append("\n").append(description);
            sb.append("\n").append("Click to use");

            final String tooltipText = sb.toString();

            FontContext fontContext = renderContext.getFontContext();

            final int ttWidth = fontContext.getStringWidth(tooltipText);
            final int ttHeight = fontContext.getStringHeight(tooltipText);

            final Box textBounds = new Box(
                screenBounds.left - ttWidth,
                screenBounds.left,
                screenBounds.top + 8,
                screenBounds.top);

            final Box bb = new Box(
                textBounds.left - 4,
                screenBounds.left + 4,
                screenBounds.top - ttHeight - 8,
                screenBounds.top);

            renderContext.renderRect(
                new Color(33, 33, 33, 175),
                true,
                bb,
                RenderLayer.GUI);

            fontContext.setColor(Color.yellow);
            fontContext.setRenderLayer(RenderLayer.GUI);

            fontContext.drawString(
                tooltipText,
                FontContext.TextAlign.LEFT, textBounds.left, textBounds.top - ttHeight);
        }
    }

    /**
     * @return the previous item if any, else null
     */
    @Nullable
    @CanIgnoreReturnValue
    public Item setItem(final Item item) {
        if (acceptsItem(item)) {
            Item prevItem = this.item;
            this.item = item;
            return prevItem;
        } else {
            return null;
        }
    }

    public Item removeItem(final Item item) {
        final Item prevItem = this.item;
        this.item = item;
        return prevItem;
    }

    /**
     * Returns true if this item slot can hold this item.
     * default=true
     */
    public boolean acceptsItem(final Item item) {
        return true;
    }

    @Override
    public void onRender(
        EventArgs eventArgs,
        RenderContext renderContext
    ) {
        final Inventory.InventoryItem inventoryItem = inventory.getItem(inventorySlotId);
        if (inventoryItem != null && inventoryItem.item != null) {
            renderContext.renderGui(
                inventoryItem.item.getSprite().getTexture(),
                screenBounds);
            if (inventoryItem.stackSize > 1) {
                final String stackSizeStr = Integer.toString(99);
//                final String stackSizeStr = Integer.toString(inventoryItem.stackSize);
                final FontContext fc = renderContext.getFontContext();

                fc.setColor(Color.black);
                fc.drawString(stackSizeStr, FontContext.TextAlign.RIGHT,
                    screenBounds.right+1,
                    screenBounds.bottom - 2);

                fc.setColor(Color.yellow);
                fc.drawString(stackSizeStr, FontContext.TextAlign.RIGHT,
                    screenBounds.right,
                    screenBounds.bottom - 3);
            }
        }
    }

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        if (isMouseOver()) {
            if (item != null && e.getButton() == MouseEvent.BUTTON1) {
                System.out.println("Using " + item);
                item.onUse.call(new Item.ItemContext());
                if (item.isConsumable()) {
                    inventory.consumeOrRemoveItem(inventorySlotId);
                }
            }
            return true;
        }
        return false;
    }
}