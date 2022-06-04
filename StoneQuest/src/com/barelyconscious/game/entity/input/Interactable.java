package com.barelyconscious.game.entity.input;

import java.awt.event.MouseEvent;

public interface Interactable {

    boolean contains(final int screenX, final int screenY);

    boolean isMouseOver();

    void onMouseOver(MouseEvent e);
    void onMouseClicked(MouseEvent e);
    void onMouseEntered(MouseEvent e);
    void onMouseExited(MouseEvent e);

}
