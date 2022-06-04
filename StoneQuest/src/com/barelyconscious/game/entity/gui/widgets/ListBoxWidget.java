package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ListBoxWidget extends Widget {

    private final List<ListBoxItem> items;

    private int currentlySelectedIndex = -1;

    public ListBoxWidget(LayoutData layout) {
        super(layout);
        this.items = new ArrayList<>();
    }

    public ListBoxItem getSelectedItem() {
        if (currentlySelectedIndex == -1) {
            return null;
        }

        return items.get(currentlySelectedIndex);
    }

    public List<ListBoxItem> addItem(final ListBoxItem item) {
        items.add(item);
        item.delegateOnClicked.bindDelegate(this::handleItemClicked);
        return items;
    }

    private Void handleItemClicked(final ListBoxItem.ItemClicked e) {
        ListBoxItem prev = getSelectedItem();
        if (prev != null) {
            prev.setSelected(false);
        }

        e.itemClicked.setSelected(true);
        currentlySelectedIndex = items.indexOf(e.itemClicked);
        return null;
    }

    public ListBoxItem getItemAt(final int index) {
        return items.get(index);
    }

    public void removeItem(final ListBoxItem item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        
    }
}
