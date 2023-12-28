package com.barelyconscious.worlds.engine.gui.extractor;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.engine.gui.widgets.ItemSlotWidget;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.systems.ExtractorSystem;

import java.util.ArrayList;
import java.util.List;

public class ExtractorUpgradesWidget extends Widget {

    private final Inventory codecs = new Inventory(3);
    private final Inventory peripherals = new Inventory(3);
    private final List<ItemSlotWidget> codecItemSlots = new ArrayList<>(3);
    private final List<ItemSlotWidget> peripheralItemSlots = new ArrayList<>(3);

    public ExtractorUpgradesWidget() {
        super(LayoutData.builder()
            .build());

        setUpCodecs();
        setUpPeripherals();
    }

    @Override
    public void onReady() {
        super.onReady();

        GameInstance.instance().getSystem(ExtractorSystem.class)
            .delegateOnStateChanged.bindDelegate(this::updateItemSlots);

        updateItemSlots(GameInstance.instance()
            .getGameState()
            .getExtractorState());
    }

    private Void updateItemSlots(ExtractorSystem.ExtractorState state) {
        for (int i = 0; i < state.getCodecs().size(); i++) {
            codecs.setItem(i, new Inventory.InventoryItem(
                state.getCodecs().get(i), 1));
            codecItemSlots.get(i).setItem(state.getCodecs().get(i));
        }

        for (int i = 0; i < state.getPeripherals().size(); i++) {
            peripherals.setItem(i, new Inventory.InventoryItem(
                state.getPeripherals().get(i), 1));
            peripheralItemSlots.get(i).setItem(state.getPeripherals().get(i));
        }

        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        widgets.forEach((widget) -> widget.setEnabled(enabled));
    }

    private void setUpCodecs() {
        var itemSlot = new ItemSlotWidget(
            LayoutData.builder()
                .anchor(0, 0, 0, 0)
                .size(1, 1, 0, 0)
                .build(),
            codecs, null, 0);
        codecItemSlots.add(itemSlot);
        addWidget(itemSlot);

        itemSlot = new ItemSlotWidget(
            LayoutData.builder()
                .anchor(0, 0, 0, 0)
                .size(1, 1, 0, 0)
                .build(),
            codecs, null, 1);
        codecItemSlots.add(itemSlot);
        addWidget(itemSlot);

        itemSlot = new ItemSlotWidget(
            LayoutData.builder()
                .anchor(0, 0, 0, 0)
                .size(1, 1, 0, 0)
                .build(),
            codecs, null, 2);
        codecItemSlots.add(itemSlot);
        addWidget(itemSlot);
    }

    private void setUpPeripherals() {
        var itemSlot = new ItemSlotWidget(
            LayoutData.builder()
                .anchor(0, 0, 0, 0)
                .size(1, 1, 0, 0)
                .build(),
            peripherals, null, 0);
        peripheralItemSlots.add(itemSlot);
        addWidget(itemSlot);

        itemSlot = new ItemSlotWidget(
            LayoutData.builder()
                .anchor(0, 0, 0, 0)
                .size(1, 1, 0, 0)
                .build(),
            peripherals, null, 1);
        peripheralItemSlots.add(itemSlot);
        addWidget(itemSlot);

        itemSlot = new ItemSlotWidget(
            LayoutData.builder()
                .anchor(0, 0, 0, 0)
                .size(1, 1, 0, 0)
                .build(),
            peripherals, null, 2);
        peripheralItemSlots.add(itemSlot);
        addWidget(itemSlot);
    }
}
