package com.barelyconscious.worlds.engine.gui.collector;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.engine.gui.widgets.ItemSlotWidget;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.systems.CollectorSystem;

import java.util.ArrayList;
import java.util.List;

public class CollectionInventory extends Widget {

    private final int numRows = 4;
    private final int numColumns = 5;
    private final Inventory collectionInventory = new Inventory(numRows * numColumns);
    private final List<ItemSlotWidget> itemWidgets = new ArrayList<>(numRows * numColumns);

    public CollectionInventory() {
        super(LayoutData.builder()
            .build());

        int xOffs = -28;
        int yOffs = 52;
        int gutter = 2;
        for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < numColumns; column++) {
                var itemSlotIndex = row * numColumns + column;
                var itemWidget = new ItemSlotWidget(LayoutData.builder()
                    .anchor(0.5, 0, xOffs + column * (40 + gutter), yOffs + row * (40 + gutter))
                    .size(0, 0, 40, 40)
                    .build(),
                    collectionInventory,
                    null,
                    itemSlotIndex);
                itemWidget.setInteractable(false);
                itemWidgets.add(itemWidget);
                addWidget(itemWidget);
            }
        }

        collectionInventory.delegateOnItemChanged.bindDelegate(this::onItemChanged);
    }

    @Override
    public void onReady() {
        GameInstance.instance().getSystem(CollectorSystem.class).delegateOnStateChanged
            .bindDelegate(this::updateCollectionInventory);
        updateCollectionInventory(null);
    }

    private Void updateCollectionInventory(CollectorSystem.CollectorState state) {
        var collection = GameInstance.instance().getGameState()
            .getCollectorState()
            .getCollection();

        for (int i = 0; i < collection.size(); i++) {
            collectionInventory.setItem(i, new Inventory.InventoryItem(
                collection.get(i),
                1
            ));
        }

        return null;
    }

    private Void onItemChanged(Inventory.InventoryItemEvent inventoryItemEvent) {
        int itemIndex = inventoryItemEvent.index;

        if (itemIndex < 0 || itemIndex >= itemWidgets.size()) {
            return null;
        }

        itemWidgets.get(itemIndex).setItem(inventoryItemEvent.item);

        return null;
    }
}
