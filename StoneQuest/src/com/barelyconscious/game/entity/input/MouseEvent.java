package com.barelyconscious.game.entity.input;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MouseEvent {

    public final int mouseX;
    public final int mouseY;

    public final int worldX;
    public final int worldY;

    public final boolean mouseMoved;
    public final boolean isMouseDown;

}
