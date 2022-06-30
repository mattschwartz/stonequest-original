package com.barelyconscious.game.entity;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.item.Item;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * A general-purpose container of Items.
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
    }

    public boolean addItem(final Item item) {
        if (isFull()) {
            return false;
        }

        if (item.isStackable()) {
            int existingSlot = findIndexOfItem(item);
            if (existingSlot == -1) {
                items.add(new InventoryItem(item, 1));
                ++currentSize;
            } else {
                InventoryItem inventoryItem = items.get(existingSlot);
                ++inventoryItem.stackSize;
            }
        } else {
            items.add(new InventoryItem(item, 1));
            ++currentSize;
        }

        delegateOnItemAdded.call(new InventoryItemEvent(item, currentSize));
        delegateOnItemChanged.call(new InventoryItemEvent(item, currentSize));

        return true;
    }

    @Nullable
    @CanIgnoreReturnValue
    public Item removeItemAt(final int slot) {
        if (slot < 0 || slot >= currentSize) {
            return null;
        }
        final InventoryItem itemRemoved = items.remove(slot);

        if (itemRemoved != null) {
            --currentSize;
            delegateOnItemRemoved.call(new InventoryItemEvent(itemRemoved.item, slot));
            delegateOnItemChanged.call(new InventoryItemEvent(itemRemoved.item, slot));
        }

        return null;
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
        if (slot < 0 || slot >= currentSize) {
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
