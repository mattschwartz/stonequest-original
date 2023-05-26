package com.barelyconscious.worlds.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * Function calls
 */
@Getter
@AllArgsConstructor
public class YieldingCallback {

    private long yieldForMillis;
    private final Function<JobRunContext, Void> callback;

    /**
     * This method is called outside of a job and hence has no job run context. This isn't great... but the
     * yield functionality is helpful elsewhere, so:
     * todo(p1): fix yields outside of job context not able to yield in callback
     */
    public boolean tickAndCall(final EventArgs eventArgs) {
        yieldForMillis -= eventArgs.getDeltaTime() * 1000;
        if (yieldForMillis < 0) {
            callback.apply(new JobRunContext("NULL_JOB_CONTEXT", eventArgs));
            return true;
        }
        return false;
    }

    /**
     * @return true if the callback was called
     */
    public boolean tickAndCall(final JobRunContext runContext) {
        yieldForMillis -= runContext.getEventArgs().getDeltaTime() * 1000;
        if (yieldForMillis < 0) {
            callback.apply(runContext);
            return true;
        }
        return false;
    }
}
