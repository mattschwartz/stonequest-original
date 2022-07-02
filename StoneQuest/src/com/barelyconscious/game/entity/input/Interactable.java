package com.barelyconscious.game.entity.input;

import java.awt.event.MouseEvent;

public interface Interactable {

    boolean contains(final int screenX, final int screenY);

    boolean isMouseOver();

    default boolean isInteractableEnabled() {
        return true;
    }

    /**
     * @return true if the event has been consumed and should not be processed further
     */
    default boolean onMouseOver(MouseEvent e) {
        return false;
    }

    /**
     * @return true if the event has been consumed and should not be processed further
     */
    default boolean onMouseClicked(MouseEvent e) {
        return false;
    }

    /**
     * @return true if the event has been consumed and should not be processed further
     */
    default boolean onMouseEntered(MouseEvent e) {
        return false;
    }

    /**
     * @return true if the event has been consumed and should not be processed further
     */
    default boolean onMouseExited(MouseEvent e) {
        return false;
    }
}
