package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.Interactable;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.awt.event.MouseEvent;

public abstract class ListBoxItem extends Widget {

    public final Delegate<ItemClicked> delegateOnClicked = new Delegate<>();

    @Setter
    private boolean isSelected = false;

    @AllArgsConstructor
    public static final class ItemClicked {
        public final ListBoxItem itemClicked;
        public final MouseEvent e;
    }

    public ListBoxItem(MouseInputHandler mouse) {
        super(LayoutData.DEFAULT);
        mouse.registerInteractable(new HookInteractable(this), InputLayer.GUI);
    }

    private class HookInteractable implements Interactable {

        private final ListBoxItem self;
        public HookInteractable(ListBoxItem self) {
            this.self = self;
        }

        @Override
        public boolean contains(int screenX, int screenY) {
            return screenBounds.contains(screenX, screenY);
        }

        private boolean isMouseOver = false;

        @Override
        public boolean isMouseOver() {
            return isMouseOver;
        }

        @Override
        public void onMouseOver(MouseEvent e) {
        }

        @Override
        public void onMouseClicked(MouseEvent e) {
            delegateOnClicked.call(new ItemClicked(self, e));
        }

        @Override
        public void onMouseEntered(MouseEvent e) {
            isMouseOver = true;
        }

        @Override
        public void onMouseExited(MouseEvent e) {
            isMouseOver = false;
        }
    }

    ;
}
