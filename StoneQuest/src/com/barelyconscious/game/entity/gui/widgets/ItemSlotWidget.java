package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.MouseInputWidget;
import com.barelyconscious.game.entity.gui.VDim;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.entity.item.ItemClassType;
import com.barelyconscious.game.shape.Box;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ItemSlotWidget extends MouseInputWidget {

    @Getter
    @Nullable
    // todo: should item slot just get the item directly from inventory instead?
    private Item item;
    private final int inventorySlotId;
    private final SpriteWidget itemSpriteWidget;
    private final Widget itemHighlightWidget;

    private final Inventory inventory;

    private boolean shouldShowTooltip() {
        return isMouseOver();
    }

    public ItemSlotWidget(
        LayoutData layout,
        final @NonNull Inventory inventory,
        @Nullable final Item item,
        final int inventorySlotId,
        final InputLayer inputLayer
    ) {
        super(layout, inputLayer);
        this.item = item;
        this.inventory = inventory;
        this.inventorySlotId = inventorySlotId;
        this.itemSpriteWidget = new SpriteWidget(LayoutData.DEFAULT, item == null ? null : item.getSprite());
        this.itemHighlightWidget = createItemHighlightWidget();

        addWidget(itemSpriteWidget);
        addWidget(new TooltipWidget());
        addWidget(createItemStackWidget());
        addWidget(itemHighlightWidget);

        itemHighlightWidget.setEnabled(false);
    }

    public ItemSlotWidget(
        LayoutData layout,
        final @NonNull Inventory inventory,
        @Nullable final Item item,
        final int inventorySlotId
    ) {
        this(layout, inventory, item, inventorySlotId, InputLayer.GUI);
    }

    private Widget createItemHighlightWidget() {
        return new Widget(LayoutData.DEFAULT) {
            @Override
            protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
                renderContext.renderRect(Color.WHITE, false, screenBounds, RenderLayer.GUI);
            }
        };
    }

    // todo(p0): should re-use the common TooltipWidget instead
    private class TooltipWidget extends Widget {
        public TooltipWidget() {
            super(LayoutData.builder()
                .anchor(new VDim(1, 0, -4, -4))
                .build());
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
            sb.append("{COLOR=255,255,0,255}{STYLE=BOLD}").append(name);
            if (inventoryItem.stackSize > 1) {
                sb.append(" (").append(inventoryItem.stackSize).append(") ");
            }
            sb.append("\n");
            sb.append("{COLOR=200,200,200,200}{STYLE=ITALIC}").append(description).append("\n");
            if (inventoryItem.item.isConsumable()) {
                sb.append("\n").append("{COLOR=0,255,0,255}{STYLE=NONE}Click to use");
            }

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
                new Color(33, 33, 33),
                true,
                bb,
                RenderLayer.GUI);

            renderContext.renderRect(
                new Color(155, 155, 155),
                false,
                bb,
                RenderLayer.GUI);

            fontContext.setColor(Color.white);
            fontContext.setRenderLayer(RenderLayer.GUI);

            fontContext.drawString(
                tooltipText,
                FontContext.TextAlign.LEFT, textBounds.left, textBounds.top - ttHeight);
        }
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        itemHighlightWidget.setEnabled(item != null);
        return super.onMouseEntered(e);
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        itemHighlightWidget.setEnabled(false);
        return super.onMouseExited(e);
    }

    /**
     * @return the previous item if any, else null
     */
    @Nullable
    @CanIgnoreReturnValue
    public Item setItem(final Item item) {
        final Item prevItem = this.item;
        if (item == null) {
            itemSpriteWidget.setEnabled(false);
            itemSpriteWidget.setSprite(null);
            itemHighlightWidget.setEnabled(false);
        } else if (acceptsItem(item)) {
            this.item = item;
            itemSpriteWidget.setSprite(item.getSprite());
            itemSpriteWidget.setEnabled(true);
        } else {
            return null;
        }

        this.item = item;
        return prevItem;
    }

    public Widget createItemStackWidget() {
        return new Widget(LayoutData.DEFAULT) {
            @Override
            protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
                final Inventory.InventoryItem inventoryItem = inventory.getItem(inventorySlotId);
                if (inventoryItem != null && inventoryItem.item != null) {
                    if (inventoryItem.stackSize > 1) {
                        final String stackSizeStr = Integer.toString(inventoryItem.stackSize);
                        final FontContext fc = renderContext.getFontContext();

                        fc.setColor(Color.black);
                        fc.drawString(stackSizeStr, FontContext.TextAlign.RIGHT,
                            screenBounds.right + 1,
                            screenBounds.bottom - 2);

                        fc.setColor(Color.yellow);
                        fc.drawString(stackSizeStr, FontContext.TextAlign.RIGHT,
                            screenBounds.right,
                            screenBounds.bottom - 3);
                    }
                }
            }
        };
    }

    private final List<ItemClassType> acceptableItemClassTypes = new ArrayList<>();

    public void addAcceptableItem(final ItemClassType acceptableItemClassType) {
        acceptableItemClassTypes.add(acceptableItemClassType);
    }

    /**
     * Returns true if this item slot can hold this item.
     * default=true
     */
    public boolean acceptsItem(final Item item) {
        return acceptableItemClassTypes.isEmpty() || acceptableItemClassTypes.contains(item.getItemClassType());
    }

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        if (isMouseOver()) {
            if (item != null && e.getButton() == MouseEvent.BUTTON1) {
                System.out.println("Calling item on use");
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
