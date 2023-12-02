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
        equipmentInventory.delegateOnItemChanged.bindDelegate(this::onEquipmentChanged);
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

    private Void onEquipmentChanged(Inventory.InventoryItemEvent inventoryItemEvent) {
        var newItem = inventoryItemEvent.item;
        var prevItem = inventoryItemEvent.prevItem;

        if (newItem == prevItem) {
            log.warn("Nothing changed!?");
            return null;
        }

        EquipmentItemTag equipmentTag;
        if (newItem == null) {
            equipmentTag = (EquipmentItemTag) prevItem.getTags().stream().filter(t -> t instanceof EquipmentItemTag).findFirst().orElse(null);
        } else {
            equipmentTag = (EquipmentItemTag) newItem.getTags().stream().filter(t -> t instanceof EquipmentItemTag).findFirst().orElse(null);
        }

        if (equipmentTag == null) {
            log.error("Invalid item!");
            return null;
        }

        if (prevItem != null) {
            for (ItemProperty property : prevItem.getProperties()) {
                property.removeProperty((EntityActor) getParent());
            }
        }
        if (newItem != null) {
            for (ItemProperty property : newItem.getProperties()) {
                property.applyProperty((EntityActor) getParent());
            }
        }

        return null;
    }

    public void setEquippedItem(final Item item) {
        EquipmentItemTag equipmentTag = (EquipmentItemTag) item.getTags().stream().filter(t -> t instanceof EquipmentItemTag).findFirst().orElse(null);
        if (equipmentTag == null) {
            return;
        }

        equipmentInventory.setItem(itemClassToSlotId(equipmentTag), new Inventory.InventoryItem(item, 1));
    }
}
