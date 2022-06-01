package com.barelyconscious.game.entity.input;

import lombok.Builder;
import lombok.Getter;

import java.awt.event.MouseEvent;

@Builder
public class IMouseEvent {

    /**
     * Allows receivers to prevent further receivers from receiving
     * the event.
     */
    @Getter
    private boolean isConsumed;
    private final MouseEvent mouseEvent;

}
