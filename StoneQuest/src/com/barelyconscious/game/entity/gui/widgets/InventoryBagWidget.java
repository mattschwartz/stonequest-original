package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.VDim;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.entity.resources.GUISpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An inventory bag that can hold items.
 */
public final class InventoryBagWidget extends Widget {

    private Inventory inventory;
    private final int numRows;
    private final int numCols;

    private final List<ItemSlot> itemSlots;

    /**
     * @param numRows the number of horizontal item slots
     * @param numCols the number of vertical item slots
     */
    public InventoryBagWidget(
        final LayoutData layout,
        final Inventory inventoryBag,
        final int numRows,
        final int numCols
    ) {
        super(layout);
        this.inventory = inventoryBag;
        this.numRows = numRows;
        this.numCols = numCols;
        this.itemSlots = new ArrayList<>(inventoryBag.size);

        this.configureWidgets();
    }

    private void configureWidgets() {
        addWidget(new BackgroundPanelWidget(LayoutData.DEFAULT,
            new Color(33, 33, 33, 204)));
        addWidget(new SpriteWidget(LayoutData.DEFAULT, Resources.instance()
            .getSprite(GUISpriteSheet.Resources.INV_ITEM_SLOT_BACKGROUND)));

        setupItemSlots();
    }

    private void setupItemSlots() {
        final int itemSlotWidth = 48;
        final int itemSlotHeight = 48;
        // spacing between rows/cols
        final int gutterSize = 2;

        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                int xOffs = col * (itemSlotWidth + gutterSize);
                int yOffs = row * (itemSlotHeight + gutterSize);

                addWidget(new ItemSlot(LayoutData.builder()
                    .anchor(new VDim(0, 0, xOffs, yOffs))
                    .size(new VDim(0, 0, itemSlotWidth, itemSlotHeight))
                    .build(),
                    null));
            }
        }
    }
}
