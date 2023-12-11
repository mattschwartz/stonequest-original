package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.engine.EventArgs;

/**
 * Empty interface used to identify various game systems.
 */
public interface GameSystem {

    default void update(EventArgs eventArgs) {
    }
}
