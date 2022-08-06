package com.barelyconscious.game.entity;

import com.barelyconscious.game.shape.Vector;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Queue;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public final class EventArgs {

    /**
     * Time in seconds since last update.
     */
    private final float deltaTime;

    private final Vector mouseScreenPos;
    private final Vector mouseWorldPos;

    public static boolean IS_DEBUG = false;
    public static boolean IS_VERBOSE = false;

    private boolean acceptsNewJobs = true;
    private final Queue<Function<EventArgs, Void>> engineJobQueue;
    private final List<YieldingCallback> engineYields;

    void startAcceptingJobs() {
        acceptsNewJobs = true;
    }

    void stopAcceptingJobs() {
        acceptsNewJobs = false;
    }

    @CanIgnoreReturnValue
    public boolean submitJob(final Function<EventArgs, Void> job) {
        if (acceptsNewJobs) {
            engineJobQueue.add(job);
        }
        return acceptsNewJobs;
    }

    public void yield(final long yieldForMillis, final Function<EventArgs, Void> callback) {
        engineYields.add(new YieldingCallback(yieldForMillis, callback));
    }
}
