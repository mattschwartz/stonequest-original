package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.tags.EquipmentItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nullable;
import java.util.EnumMap;

@Log4j2
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
        if (slotId >= equipmentInventory.capacity || slotId < 0) {
            return -1;
        }
        return slotId;
    }

    @Nullable
    public Item getEquippedItem(final EquipmentItemTag tag) {
        Inventory.InventoryItem inventoryItem = equipmentInventory.getItem(itemClassToSlotId(tag));
        return inventoryItem == null ? null : inventoryItem.item;
    }

    public Item setEquippedItem(final EquipmentItemTag equipmentTag, final Item item) {
        if (item != null) {
            for (ItemProperty property : item.getProperties()) {
                log.info("Applying property {} to {}", property.getClass().getSimpleName(), getParent().getName());
                property.applyProperty((EntityActor) getParent());
            }
        }

        Inventory.InventoryItem prevItem = equipmentInventory.setItem(itemClassToSlotId(equipmentTag),
            new Inventory.InventoryItem(item, 1));

        if (prevItem == null || prevItem.item == null) {
            log.info("Nothing?");
            return null;
        }

        for (ItemProperty property : prevItem.item.getProperties()) {
            log.info("Removing property {} from {}", property.getClass().getSimpleName(), getParent().getName());
            property.removeProperty((EntityActor) getParent());
        }

        return prevItem.item;
    }

    public Item setEquippedItem(final Item item) {
        EquipmentItemTag equipmentTag = (EquipmentItemTag) item.getTags().stream().filter(t -> t instanceof EquipmentItemTag).findFirst().orElse(null);
        if (equipmentTag == null) {
            return null;
        }

        return setEquippedItem(equipmentTag, item);
    }
}
