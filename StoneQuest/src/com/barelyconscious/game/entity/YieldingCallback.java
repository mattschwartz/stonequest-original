package com.barelyconscious.game.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * A job
 */
@Getter
@AllArgsConstructor
public class YieldingCallback {

    private long yieldForMillis;
    private final Function<EventArgs, Void> callback;

    /**
     * @return true if the callback was called
     */
    public boolean tickAndCall(final EventArgs eventArgs) {
        yieldForMillis -= eventArgs.getDeltaTime() * 1000;
        if (yieldForMillis < 0) {
            callback.apply(eventArgs);
            return true;
        }
        return false;
    }
}
