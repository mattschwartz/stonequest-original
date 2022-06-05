package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;

import java.util.ArrayList;
import java.util.List;

public class ListBoxWidget extends Widget {

    private final List<ListBoxItem> items;

    private int currentlySelectedIndex = -1;

    public ListBoxWidget(LayoutData layout) {
        super(layout);
        this.items = new ArrayList<>();
    }

    public void addItem(final ListBoxItem item) {
    }

    public void removeItem(final ListBoxItem item) {
    }
    public void removeItemAt(final int index) {
    }

    public ListBoxItem getSelectedItem() {
        return null;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
    }
}
