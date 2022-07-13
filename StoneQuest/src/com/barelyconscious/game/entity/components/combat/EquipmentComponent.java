package com.barelyconscious.game.entity.components.combat;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.entity.item.tags.EquipmentItemTag;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.EnumMap;

public class EquipmentComponent extends Component {

    @Getter
    private final Inventory equipmentInventory;

    @Getter
    @AllArgsConstructor
    public static class EquipmentChangedEvent {
        private final EquipmentItemTag equipmentItemTag;
        private final Item item;
    }

    public EquipmentComponent(Actor parent) {
        super(parent);

        this.equipmentInventory = new Inventory(8);
    }

    private static final EnumMap<EquipmentItemTag, Integer> SLOT_ID_BY_TAG = new EnumMap<>(EquipmentItemTag.class) {{
        put(EquipmentItemTag.EQUIPMENT_HEAD, 0);
        put(EquipmentItemTag.EQUIPMENT_NECK, 1);
        put(EquipmentItemTag.EQUIPMENT_CHEST, 2);
        put(EquipmentItemTag.EQUIPMENT_GLOVES, 3);
        put(EquipmentItemTag.EQUIPMENT_LEGS, 4);
        put(EquipmentItemTag.EQUIPMENT_FEET, 5);
        put(EquipmentItemTag.EQUIPMENT_LEFT_HAND, 6);
        put(EquipmentItemTag.EQUIPMENT_RIGHT_HAND, 7);
    }};

    /**
     * @return -1 if item class type is not equipment
     */
    private int itemClassToSlotId(final EquipmentItemTag tag) {
        final int slotId = SLOT_ID_BY_TAG.get(tag);
        if (slotId >= equipmentInventory.size || slotId < 0) {
            return -1;
        }
        return slotId;
    }

    @Nullable
    public Item getEquippedItem(final EquipmentItemTag tag) {
        Inventory.InventoryItem inventoryItem = equipmentInventory.getItem(itemClassToSlotId(tag));
        return inventoryItem == null ? null : inventoryItem.item;
    }

    @CanIgnoreReturnValue
    public Item setEquippedItem(final Item item) {
        EquipmentItemTag equipmentTag = (EquipmentItemTag) item.getTags().stream().filter(t -> t instanceof EquipmentItemTag).findFirst().orElse(null);
        if (equipmentTag == null) {
            return null;
        }

        Inventory.InventoryItem prevItem = equipmentInventory.setItem(itemClassToSlotId(equipmentTag),
            new Inventory.InventoryItem(item, 1));
        return prevItem == null ? null : prevItem.item;
    }
}
