package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.entity.components.EquipmentComponent;
import com.barelyconscious.worlds.engine.gui.widgets.ItemSlotWidget;
import com.barelyconscious.worlds.engine.input.InputLayer;
import com.barelyconscious.worlds.game.item.tags.EquipmentItemTag;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class EquipmentDollWidget extends MouseInputWidget {

    private final EquipmentComponent equipmentComponent;
    private final Map<Integer, ItemSlotWidget> itemSlotsByClassType;

    public EquipmentDollWidget(LayoutData layout, EquipmentComponent equipmentComponent) {
        super(layout, InputLayer.GUI);
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

            if (itemSlotWidget == null) {
                continue;
            }

            final Inventory.InventoryItem item = equipmentComponent.getEquipmentInventory().getItem(i);
            if (item == null || item.item == null) {
                itemSlotWidget.setItem(null);
                continue;
            }

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
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_HEAD),
            EquipmentItemTag.EQUIPMENT_HEAD.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_HEAD);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_HEAD.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 19, 78))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_NECK),
            EquipmentItemTag.EQUIPMENT_NECK.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_NECK);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_NECK.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 19, 114))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_CHEST),
            EquipmentItemTag.EQUIPMENT_CHEST.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_CHEST);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_CHEST.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 127, 42))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_GLOVES),
            EquipmentItemTag.EQUIPMENT_GLOVES.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_GLOVES);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_GLOVES.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 127, 78))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_LEGS),
            EquipmentItemTag.EQUIPMENT_LEGS.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_LEGS);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_LEGS.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 127, 114))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_FEET),
            EquipmentItemTag.EQUIPMENT_FEET.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_FEET);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_FEET.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 55, 150))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_RIGHT_HAND),
            EquipmentItemTag.EQUIPMENT_RIGHT_HAND.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_RIGHT_HAND);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_RIGHT_HAND.ordinal(), itemSlotWidget);

        itemSlotWidget = new ItemSlotWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 91, 150))
            .size(new VDim(0, 0, 34, 34))
            .build(),
            equipmentComponent.getEquipmentInventory(),
            equipmentComponent.getEquippedItem(EquipmentItemTag.EQUIPMENT_LEFT_HAND),
            EquipmentItemTag.EQUIPMENT_LEFT_HAND.ordinal(), InputLayer.USER_INPUT);
        itemSlotWidget.addRequiredItemTag(EquipmentItemTag.EQUIPMENT_LEFT_HAND);
        addWidget(itemSlotWidget);
        itemSlotsByClassType.put(EquipmentItemTag.EQUIPMENT_LEFT_HAND.ordinal(), itemSlotWidget);
    }
}
