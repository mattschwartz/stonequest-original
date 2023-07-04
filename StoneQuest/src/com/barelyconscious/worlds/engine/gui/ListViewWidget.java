package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;

import java.util.ArrayList;
import java.util.List;

public class ListViewWidget extends Widget {

    private List<Widget> items;

    public ListViewWidget(LayoutData layout) {
        super(layout);
        items = new ArrayList<>();
    }

    public void addItem(Widget item) {
        items.add(item);
        addWidget(item);
    }

    public void removeItem(Widget item) {
        items.remove(item);
        item.setRemoving(true);
    }

    @Override
    public void resize(Box bounds) {
        super.resize(bounds);
        int totalHeight = widgets.stream().mapToInt(w -> w.getScreenBounds().height).sum();
        
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        super.onRender(eventArgs, renderContext);

    }
}
