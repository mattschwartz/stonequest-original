package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.MouseInputHandler;

import java.awt.event.MouseEvent;

/**
 * A type of widget that can process mouse input
 */
public class MouseInputWidget extends Widget {

    public MouseInputWidget(LayoutData layout) {
        super(layout);

        MouseInputHandler.instance().registerInteractable(this, InputLayer.GUI);
    }

    @Override
    public boolean isInteractableEnabled() {
        return isEnabled();
    }

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        return true;
    }
}
