package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.entity.resources.GUISpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;
import lombok.Getter;

import java.awt.*;
import java.util.Optional;

/**
 * An inventory bag that can hold items.
 */
public final class InventoryBagWidget extends Widget {

    private Inventory inventory;
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
    }

    private void configureWidgets() {
        addWidget(new BackgroundPanelWidget(LayoutData.DEFAULT,
            new Color(33, 33, 33, 204)));
        addWidget(new SpriteWidget(LayoutData.DEFAULT, Resources.instance()
            .getSprite(GUISpriteSheet.Resources.INV_ITEM_SLOT_BACKGROUND)));
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        int i = 0;
        for (int col = 0; col < numCols; ++col) {
            for (int row = 0; row < numRows; ++row) {
                final Optional<Item> item = inventory.getItem(++i);
                if (item.isPresent()) {
                    // render item
                }
            }
        }
    }
}
