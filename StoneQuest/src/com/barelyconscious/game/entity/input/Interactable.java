package com.barelyconscious.game.entity.input;

import java.awt.event.MouseEvent;

public interface Interactable {

    boolean contains(final int screenX, final int screenY);

    boolean isMouseOver();

    void onMouseOver(final MouseEvent e);
    void onMouseClicked(final MouseEvent e);
    void onMouseEntered(final MouseEvent e);
    void onMouseExited(final MouseEvent e);
}
