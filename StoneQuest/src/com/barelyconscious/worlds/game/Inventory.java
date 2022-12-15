package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.game.item.Item;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * A general-purpose container of Items.
 *
 * todo(p0): need to implement a locking mechanism on items to prevent dupes
 */
public final class Inventory {

    public final Delegate<InventoryItemEvent> delegateOnItemAdded = new Delegate<>();
    public final Delegate<InventoryItemEvent> delegateOnItemRemoved = new Delegate<>();
    public final Delegate<InventoryItemEvent> delegateOnItemChanged = new Delegate<>();

    @AllArgsConstructor
    public static final class InventoryItemEvent {

        @NonNull
        public final Item item;
        public final int index;
    }

    @AllArgsConstructor
    public static class InventoryItem {
        public Item item;
        public int stackSize;
    }

    public int currentSize = 0;
    public final int size;
    private final List<InventoryItem> items;

    public Inventory(final int size) {
        this.size = size;
        items = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            items.add(null);
        }
    }

    /**
     * @param inventoryItem if null, nothing happens
     */
    public InventoryItem setItem(final int slotId, final InventoryItem inventoryItem) {
        if (slotId < size && inventoryItem != null) {
            final InventoryItem prevItem = items.set(slotId, inventoryItem);
            if (prevItem == null) {
                currentSize++;
            }

            delegateOnItemAdded.call(new InventoryItemEvent(inventoryItem.item, currentSize));
            delegateOnItemChanged.call(new InventoryItemEvent(inventoryItem.item, currentSize));

            return prevItem;
        }
        return null;
    }

    public boolean contains(Item item) {
        return findIndexOfItem(item) != -1;
    }

    /**
     * @deprecated use setItem instead
     */
    @CanIgnoreReturnValue
    @Deprecated
    public InventoryItem setItemAt(final int slotId, final Item item) {
        if (slotId < size) {
            final InventoryItem prevItem = items.set(slotId, new InventoryItem(item, 1));
            if (prevItem == null) {
                currentSize++;
            }

            delegateOnItemAdded.call(new InventoryItemEvent(item, currentSize));
            delegateOnItemChanged.call(new InventoryItemEvent(item, currentSize));

            return prevItem;
        }
        return null;
    }

    public boolean addItem(final Item item) {
        if (isFull() || item == null) {
            return false;
        }

        if (item.isStackable()) {
            int existingSlot = findIndexOfItem(item);
            if (existingSlot == -1) {
                final int slotId = findEmptySlotId();
                items.set(slotId, new InventoryItem(item, 1));
                ++currentSize;
            } else {
                InventoryItem inventoryItem = items.get(existingSlot);
                ++inventoryItem.stackSize;
            }
        } else {
            final int slotId = findEmptySlotId();
            items.set(slotId, new InventoryItem(item, 1));
            ++currentSize;
        }

        delegateOnItemAdded.call(new InventoryItemEvent(item, currentSize));
        delegateOnItemChanged.call(new InventoryItemEvent(item, currentSize));

        return true;
    }

    /**
     * finds the slot id for an empty slot in the inventory if any
     *
     * @return -1 if inventory is full
     */
    private int findEmptySlotId() {
        if (items.get(currentSize) == null) {
            return currentSize;
        } else {
            for (int i = 0; i < items.size(); ++i) {
                if (items.get(i) == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @return the item and stack size and removes it from the inventory
     */
    public InventoryItem removeStackAt(final int slotId) {
        if (slotId < 0 || slotId >= size) {
            return null;
        }
        final InventoryItem itemRemoved = items.set(slotId, null);

        if (itemRemoved != null) {
            --currentSize;
            delegateOnItemRemoved.call(new InventoryItemEvent(itemRemoved.item, slotId));
            delegateOnItemChanged.call(new InventoryItemEvent(itemRemoved.item, slotId));
        }

        return itemRemoved;
    }

    @Nullable
    @CanIgnoreReturnValue
    public Item removeItemAt(final int slot) {
        final InventoryItem itemRemoved = removeStackAt(slot);
        return itemRemoved == null ? null : itemRemoved.item;
    }

    /**
     * if stackable, the item's stack size is reduced and if <= 0, item is removed. if not stackable, item is removed
     */
    @CanIgnoreReturnValue
    public Item consumeOrRemoveItem(final int slot) {
        InventoryItem inventoryItem = items.get(slot);

        if (inventoryItem.item.isStackable()) {
            --inventoryItem.stackSize;
            if (inventoryItem.stackSize <= 0) {
                return removeItemAt(slot);
            }
        } else {
            return removeItemAt(slot);
        }
        return null;
    }

    @Nullable
    public InventoryItem getItem(final int slot) {
        if (slot < 0 || slot >= size) {
            return null;
        }
        return items.get(slot);
    }

    public boolean isFull() {
        return currentSize >= size;
    }

    /**
     * @return -1 if no item found
     */
    public int findIndexOfItem(@NonNull final Item item) {
        int itemSlot = 0;
        for (final InventoryItem inventoryItem : items) {
            if (inventoryItem != null && inventoryItem.item.getItemId() == item.getItemId()) {
                return itemSlot;
            }
            ++itemSlot;
        }
        return -1;
    }
}
