package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.components.combat.EquipmentComponent;
import com.barelyconscious.game.entity.gui.widgets.ItemSlotWidget;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.item.ItemClassType;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class EquipmentDollWidget extends Widget {

    private final EquipmentComponent equipmentComponent;
    private final Map<Integer, ItemSlotWidget> itemSlotsByClassType;

    public EquipmentDollWidget(LayoutData layout, EquipmentComponent equipmentComponent) {
        super(layout);
        this.equipmentComponent = equipmentComponent;
        this.itemSlotsByClassType = new HashMap<>();

        setupEquipmentSlots();
        equipmentComponent.getEquipmentInventory().delegateOnItemChanged.bindDelegate(this::onEquipmentChanged);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        widgets.forEach(t -> t.setEnabled(enabled));
    }

    private Void onEquipmentChanged(Inventory.InventoryItemEvent itemChanged) {
        for (int i = 0; i < equipmentComponent.getEquipmentInventory().size; ++i) {
            final ItemSlotWidget itemSlotWidget = itemSlotsByClassType.get(i);

            if(itemSlotWidget == null) {
                log.error("Could not find item slot widget for " + i);
                continue;
            }

            final Inventory.InventoryItem item = equipmentComponent.getEquipmentInventory().getItem(i);
            if (item == null || item.item == null) {
                log.error("Item was null for index=" + i);
                itemSlotWidget.setItem(null);
                continue;
            }

            log.error("Setting {} to {}", i, item);
            itemSlotWidget.setItem(item.item);
        }
        return null;
    }

    private void setupEquipmentSlots() {
        ItemSlotWidget itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 19, 42))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_HEAD),
            ItemClassType.EQUIPMENT_HEAD.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_HEAD.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 19, 78))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_NECK),
            ItemClassType.EQUIPMENT_NECK.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_NECK.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 19, 114))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_CHEST),
            ItemClassType.EQUIPMENT_CHEST.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_CHEST.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 127, 42))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_GLOVES),
            ItemClassType.EQUIPMENT_GLOVES.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_GLOVES.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 127, 78))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_LEGS),
            ItemClassType.EQUIPMENT_LEGS.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_LEGS.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 127, 114))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_FEET),
            ItemClassType.EQUIPMENT_FEET.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_FEET.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 55, 150))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_RIGHT_HAND),
            ItemClassType.EQUIPMENT_RIGHT_HAND.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_RIGHT_HAND.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 91, 150))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(ItemClassType.EQUIPMENT_LEFT_HAND),
            ItemClassType.EQUIPMENT_LEFT_HAND.ordinal(), InputLayer.USER_INPUT);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(ItemClassType.EQUIPMENT_LEFT_HAND.ordinal(), itemSlotWidget);
    }
}
