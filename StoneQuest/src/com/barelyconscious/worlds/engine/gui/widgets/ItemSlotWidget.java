package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.VDim;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.gui.MouseInputWidget;
import com.barelyconscious.worlds.engine.input.InputLayer;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemTag;
import com.barelyconscious.worlds.common.shape.Box;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class ItemSlotWidget extends MouseInputWidget {

    @Getter
    @Nullable
    // todo: should item slot just get the item directly from inventory instead?
    private Item item;
    private final int inventorySlotId;
    private final SpriteWidget itemSpriteWidget;
    private final Widget itemHighlightWidget;

    private final Inventory inventory;

    public final Delegate<ItemSlotEvent> delegateOnItemChanged = new Delegate<>();

    @AllArgsConstructor
    public static final class ItemSlotEvent {
        public final Item prevItem;
        public final Item newItem;
        public final int inventorySlotId;
    }

    private boolean shouldShowTooltip() {
        return isEnabled() && isMouseOver();
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
        this.itemSpriteWidget = new SpriteWidget(LayoutData.DEFAULT, item == null ? null : item.getSprite().load());
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
        return new Widget(LayoutData.builder()
            .anchor(LayoutData.ANCHOR_TOP_LEFT)
            .size(new VDim(1, 1, -1, -1))
            .build()) {
            @Override
            protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
                Inventory.InventoryItem inventoryItemOnCursor = ItemFollowCursorWidget.getInventoryItemOnCursor();
                if (inventoryItemOnCursor != null) {
                    if (acceptsItem(inventoryItemOnCursor.item)) {
                        renderContext.renderRect(new Color(69, 182, 69), false, screenBounds, RenderLayer.GUI);
                    } else {
                        renderContext.renderRect(new Color(172, 50, 50), false, screenBounds, RenderLayer.GUI);
                    }
                } else if (item != null) {
                    renderContext.renderRect(Color.WHITE, false, screenBounds, RenderLayer.GUI);
                }
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
            for (var prop : inventoryItem.item.getProperties()) {
                sb.append("{COLOR=255,255,255,255}{STYLE=NONE}").append(prop.getPropertyDescription()).append("\n");
            }
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
        itemHighlightWidget.setEnabled(true);
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
            itemSpriteWidget.setSprite(item.getSprite().load());
            itemSpriteWidget.setEnabled(true);
        } else {
            return null;
        }

        this.item = item;

        delegateOnItemChanged.call(new ItemSlotEvent(prevItem, item, inventorySlotId));

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

    // uses OR
    private final Set<ItemTag> requiredTags = new HashSet<>();

    public void addRequiredItemTag(final ItemTag requiredTag) {
        requiredTags.add(requiredTag);
    }

    /**
     * Returns true if this item slot can hold this item.
     * default=true
     */
    public boolean acceptsItem(final Item item) {
        if (requiredTags.isEmpty()) {
            return true;
        }

        // at least 1 overlapping tag
        return Sets.intersection(item.getTags(), requiredTags).size() >= 1;
    }

    /**
     * Use (right click) or pick up (left click) an item
     */
    @Override
    public boolean onMouseClicked(MouseEvent e) {
        if (isMouseOver()) {
            final Inventory.InventoryItem itemOnCursor = ItemFollowCursorWidget.getInventoryItemOnCursor();
            if (itemOnCursor != null) {
                if (item != null && itemOnCursor.item.getItemId() == item.getItemId()) {
                    // same item, stack
                    var inventoryItem = inventory.getItem(inventorySlotId);
                    if (inventoryItem != null) {
                        inventoryItem.stackSize = itemOnCursor.stackSize + inventoryItem.stackSize;
                        ItemFollowCursorWidget.setInventoryItemOnCursor(null);
                    }
                } else
                if (acceptsItem(itemOnCursor.item)) {
                    final Inventory.InventoryItem prevItem = inventory.setItem(inventorySlotId, itemOnCursor);
                    ItemFollowCursorWidget.setInventoryItemOnCursor(prevItem);
                }
            } else if (item != null) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    ItemFollowCursorWidget.setInventoryItemOnCursor(inventory.removeStackAt(inventorySlotId));
                } else if (e.getButton() == MouseEvent.BUTTON3) {

                    System.out.println("Calling item on use");

                    if (item.meetsRequirements(GameInstance.instance().getHeroSelected())) {
                        System.out.println("Item meets requirements");

                        item.applyProperties(GameInstance.instance().getHeroSelected());

                        item.onUse.call(new Item.ItemContext());
                        if (item.isConsumable()) {
                            inventory.consumeOrRemoveItem(inventorySlotId);
                        }
                    } else {
                        System.out.println("Item does not meet requirements");
                    }
                }
            }

            return true;
        }
        return false;
    }
}
