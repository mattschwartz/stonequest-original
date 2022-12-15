package com.barelyconscious.worlds.entity.gui.widgets;

import com.barelyconscious.worlds.entity.Inventory;
import com.barelyconscious.worlds.entity.gui.LayoutData;
import com.barelyconscious.worlds.entity.gui.VDim;
import com.barelyconscious.worlds.entity.gui.Widget;
import com.barelyconscious.worlds.entity.resources.GUISpriteSheet;
import com.barelyconscious.worlds.entity.resources.Resources;
import lombok.extern.log4j.Log4j2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * An inventory bag that can hold items.
 */
@Log4j2
public final class InventoryBagWidget extends Widget {

    private final List<ItemSlotWidget> itemSlotWidgets = new ArrayList<>();
    private final Inventory inventory;
    private final int numRows;
    private final int numCols;

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

        this.configureWidgets();
        inventory.delegateOnItemChanged.bindDelegate(this::onInventoryChanged);

        if (inventory.size != numRows * numCols) {
            log.error("Inventory size ({}) does not match specified rows ({}) and columns ({})!",
                inventory.size, numRows, numCols);
        }
    }

    private Void onInventoryChanged(Inventory.InventoryItemEvent inventoryItemEvent) {
        int index = 0;
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col, ++index) {
                Inventory.InventoryItem inventoryItem = inventory.getItem(index);
                final ItemSlotWidget itemSlotWidget = itemSlotWidgets.get(index);
                itemSlotWidget.setItem(inventoryItem == null ? null : inventoryItem.item);
            }
        }

        return null;
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

        int index = 0;
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col, ++index) {
                int xOffs = col * (itemSlotWidth + gutterSize);
                int yOffs = row * (itemSlotHeight + gutterSize);

                Inventory.InventoryItem inventoryItem = inventory.getItem(index);
                final ItemSlotWidget itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
                    .anchor(new VDim(0, 0, xOffs, yOffs))
                    .size(new VDim(0, 0, itemSlotWidth, itemSlotHeight))
                    .build(),
                    inventory,
                    inventoryItem == null ? null : inventoryItem.item,
                    index);
                itemSlotWidgets.add(itemSlotWidget);
                addWidget(itemSlotWidget);
            }
        }
    }
}
