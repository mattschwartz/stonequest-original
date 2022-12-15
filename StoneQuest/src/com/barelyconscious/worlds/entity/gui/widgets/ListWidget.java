package com.barelyconscious.worlds.entity.gui.widgets;

import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.entity.graphics.FontContext;
import com.barelyconscious.worlds.entity.graphics.RenderContext;
import com.barelyconscious.worlds.entity.gui.LayoutData;
import com.barelyconscious.worlds.entity.gui.MouseInputWidget;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A widget that supports a dynamic list of items to be rendered to the screen.
 * <p>
 * Users can select item(s) in the list using the mouse.
 * <p>
 * Users can use arrow keys to navigate items in the list and space or enter to
 * select. (todo - NYI)
 */
public class ListWidget extends MouseInputWidget {

    private final List<ListItemWidget> items = new ArrayList<>();

    public ListWidget(LayoutData layout) {
        super(layout);
    }

    public void addItem(final ListItemWidget item) {
        items.add(item);
    }

    public void removeItem(final ListItemWidget itemToRemove) {
        items.remove(itemToRemove);
    }

    public void removeItemAt(final int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        int xPad = 5;
        int yPad = 7;
        int xStart = screenBounds.left + xPad;
        int yStart = screenBounds.top + 16;

        FontContext fc = renderContext.getFontContext();
        int fontHeight = fc.getStringHeight("W");
        Color prevColor = fc.getColor();

        for (int i = 0; i < items.size(); ++i) {
            final ListItemWidget item = items.get(i);
            if (item.selected) {
                fc.setColor(Color.yellow);
            } else {
                fc.setColor(Color.gray);
            }

            fc.drawString(item.displayName, FontContext.TextAlign.LEFT,
                xStart,
                yStart + ((fontHeight + yPad) * i));
        }

        fc.setColor(prevColor);
    }

    /**
     * An item that can be added to a ListWidget.
     */
    @Getter
    @Setter
    public  static class ListItemWidget {

        private final String id;
        private final String displayName;
        private boolean selected;

        public ListItemWidget(final String id, final String displayName) {
            this.id = id;
            this.displayName = displayName;
        }
    }
}
