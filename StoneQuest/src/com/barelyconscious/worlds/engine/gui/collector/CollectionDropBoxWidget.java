package com.barelyconscious.worlds.engine.gui.collector;

import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.engine.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.engine.gui.widgets.ItemSlotWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.item.tags.CollectibleItemTag;

public class CollectionDropBoxWidget extends Widget {

    private final TextFieldWidget itemName;
    private final Inventory dropslotInventory = new Inventory(1);
    private final ItemSlotWidget dropslot;
    private final TextFieldWidget collectionHintText;
    private final ButtonWidget sellButton;

    public CollectionDropBoxWidget() {
        super(LayoutData.builder()
            .anchor(0.5, 1, -28, -126)
            .size(0, 0, 40, 40)
            .build());

        dropslotInventory.delegateOnItemChanged.bindDelegate(this::onItemChanged);

        itemName = new TextFieldWidget("An Item Name Here", LayoutData.builder()
            .anchor(0, 0, 48, 20)
            .size(0, 0, 40, 40)
            .build());
        dropslot = new ItemSlotWidget(LayoutData.builder()
            .build(),
            dropslotInventory,
            null,
            0);
        dropslot.addRequiredItemTag(CollectibleItemTag.COLLECTIBLE);

        collectionHintText = new TextFieldWidget(
            "{COLOR=GRAY}{STYLE=ITALIC}Drag an item from your\n{COLOR=GRAY}{STYLE=ITALIC}inventory here to sell it",
            LayoutData.builder()
                .anchor(0.5, 1, -7, 8)
                .size(1, 1, 0, 0)
                .build());
        collectionHintText.setTextAlignment(FontContext.TextAlign.LEFT);
        collectionHintText.setShowShadow(false);

        sellButton = new ButtonWidget("Sell", LayoutData.builder()
            .anchor(1, 1, -7, 8)
            .size(0, 0, 80, 24)
            .build(),
            () -> {
                return null;
            });
        sellButton.setEnabled(false);

        addWidget(dropslot);
        addWidget(itemName);
        addWidget(collectionHintText);
        addWidget(sellButton);

    }

    private Void onItemChanged(Inventory.InventoryItemEvent inventoryItemEvent) {
        if (inventoryItemEvent.item != null && !inventoryItemEvent.item.hasTag(CollectibleItemTag.COLLECTIBLE)) {
            return null;
        }

        if (inventoryItemEvent.item == null) {
            dropslot.setItem(null);
            itemName.setText(null);
            collectionHintText.setEnabled(true);
            sellButton.setEnabled(false);
        } else {
            dropslot.setItem(inventoryItemEvent.item);
            itemName.setText(inventoryItemEvent.item.getName());
            collectionHintText.setEnabled(false);
            sellButton.setEnabled(true);
        }

        return null;
    }
}
