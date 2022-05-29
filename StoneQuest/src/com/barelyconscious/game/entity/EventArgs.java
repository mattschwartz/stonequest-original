package com.barelyconscious.game.entity;

public final class EventArgs {

    /**
     * Time in seconds since last update.
     */
    public final float deltaTime;

    public EventArgs(final float deltaTime) {
        this.deltaTime = deltaTime;
    }
}
