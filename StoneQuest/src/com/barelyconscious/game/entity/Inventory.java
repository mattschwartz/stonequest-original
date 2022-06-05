package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.item.Item;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A general-purpose container of Items.
 */
public final class Inventory {

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
        return items.add(item);
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
