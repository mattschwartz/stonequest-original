package com.barelyconscious.game.entity;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.item.*;
import lombok.*;

import javax.annotation.*;
import java.util.*;

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

    public int currentSize = 0;
    public final int size;
    private final List<Item> items;

    public Inventory(final int size) {
        this.size = size;
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(final Item item) {
        if (isFull()) {
            return false;
        }
        ++currentSize;
        items.add(item);
        delegateOnItemAdded.call(new InventoryItemEvent(item, currentSize));
        delegateOnItemChanged.call(new InventoryItemEvent(item, currentSize));

        return true;
    }

    @Nullable
    public Item removeItemAt(final int slot) {
        if (slot < 0 || slot >= currentSize) {
            return null;
        }
        final Item itemRemoved = items.remove(slot);

        if (itemRemoved != null) {
            delegateOnItemRemoved.call(new InventoryItemEvent(itemRemoved, slot));
            delegateOnItemChanged.call(new InventoryItemEvent(itemRemoved, slot));
        }

        return itemRemoved;
    }

    @Nullable
    public Item getItem(final int slot) {
        if (slot < 0 || slot >= currentSize) {
            return null;
        }
        return items.get(slot);
    }

    public boolean isFull() {
        return currentSize >= size;
    }
}
