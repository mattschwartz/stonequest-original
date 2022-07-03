package com.barelyconscious.game.entity.components.combat;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.entity.item.ItemClassType;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

public class EquipmentComponent extends Component {

    @Getter
    private final Inventory equipmentInventory;

    public final Delegate<Item> delegateOnEquipmentChanged = new Delegate<>();

    @Getter
    @AllArgsConstructor
    public static class EquipmentChangedEvent {
        private final ItemClassType itemClassType;
        private final Item item;
    }

    public EquipmentComponent(Actor parent) {
        super(parent);

        this.equipmentInventory = new Inventory(8);
    }

    /**
     * @return -1 if item class type is not equipment
     */
    private int itemClassToSlotId(final ItemClassType itemClassType) {
        final int slotId = itemClassType.ordinal() - ItemClassType.EQUIPMENT_HEAD.ordinal();
        if (slotId >= equipmentInventory.size || slotId < 0) {
            return -1;
        }
        return slotId;
    }

    @Nullable
    public Item getEquippedItem(final ItemClassType itemClassType) {
        Inventory.InventoryItem inventoryItem = equipmentInventory.getItem(itemClassToSlotId(itemClassType));
        return inventoryItem == null ? null : inventoryItem.item;
    }

    @CanIgnoreReturnValue
    public Item setEquippedItem(final Item item) {
        Inventory.InventoryItem prevItem = equipmentInventory.setItemAt(itemClassToSlotId(item.getItemClassType()), item);
        delegateOnEquipmentChanged.call(item);

        return prevItem == null ? null : prevItem.item;
    }
}
