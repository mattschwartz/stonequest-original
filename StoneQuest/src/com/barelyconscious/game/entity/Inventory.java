package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A general-purpose container of Items.
 */
public final class Inventory {

    public final int size;
    private final List<Item> items;

    public Inventory(final int size) {
        this.size = size;
        items = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            items.add(null);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(final Item item) {
        if (isFull()) {
            return false;
        }
        return items.add(item);
    }

    public boolean insertItem(final Item item, final int slot) {
        if (isFull()) {
            return false;
        }
        items.add(slot, item);
        return true;
    }

    public Optional<Item> getItem(final int slot) {
        if (slot < 0 || slot > items.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(items.get(slot));
    }

    public boolean isFull() {
        return items.size() >= size;
    }
}
