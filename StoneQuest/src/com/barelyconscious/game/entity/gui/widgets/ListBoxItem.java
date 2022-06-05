package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;

public abstract class ListBoxItem {

    private String id;
    private String displayText;

    private final LayoutData layout;

    public ListBoxItem(LayoutData layout) {
        this.layout = layout;
    }

    public void onSelected() {
    }

    public void onSelectedLost() {
    }

    public abstract void render(RenderContext renderContext);
}
