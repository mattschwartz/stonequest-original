package com.barelyconscious.game.entity;

import com.barelyconscious.game.shape.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class EventArgs {

    /**
     * Time in seconds since last update.
     */
    private final float deltaTime;

    private final Vector mouseScreenPos;
    private final Vector mouseWorldPos;

    public static boolean IS_DEBUG = false;
}
