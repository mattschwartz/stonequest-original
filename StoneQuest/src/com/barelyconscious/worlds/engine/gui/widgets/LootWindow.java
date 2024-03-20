package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.game.item.Item;

import java.util.ArrayList;
import java.util.List;

public class LootWindow extends Widget {

    private final List<Item> items = new ArrayList<>();

    public LootWindow() {
        super(LayoutData.builder().build());
    }

    public void setItems() {

    }

    public void removeItem(Item item) {

    }
}
