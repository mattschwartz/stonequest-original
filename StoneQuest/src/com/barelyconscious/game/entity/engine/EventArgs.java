package com.barelyconscious.game.entity.engine;

import com.barelyconscious.game.shape.Vector;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Queue;
import java.util.function.Function;

@Getter
public class EventArgs {

    /**
     * Time in seconds since last update.
     */
    private final float deltaTime;

    private final Vector mouseScreenPos;
    private final Vector mouseWorldPos;

    public static boolean IS_DEBUG = false;
    public static boolean IS_VERBOSE = false;

    private boolean acceptsNewJobs = true;

    private final Queue<Engine.JobExecution> engineThings;

    public EventArgs(
        final float deltaTime,
        final Vector mouseScreenPos,
        final Vector mouseWorldPos,
        final Queue<Engine.JobExecution> engineThings
    ) {
        this.deltaTime = deltaTime;
        this.mouseScreenPos = mouseScreenPos;
        this.mouseWorldPos = mouseWorldPos;
        this.engineThings = engineThings;
    }

    void startAcceptingJobs() {
        acceptsNewJobs = true;
    }

    void stopAcceptingJobs() {
        acceptsNewJobs = false;
    }

    @Getter
    @AllArgsConstructor
    public static class SubmitJobResponse {
        private final boolean success;
        private final Engine.JobExecution jobExecution;
    }

    @CanIgnoreReturnValue
    public SubmitJobResponse submitJob(final Function<Engine.JobRunContext, Void> job) {
        if (acceptsNewJobs) {
            final Engine.JobExecution jobExecution = new Engine.JobExecution(this, job);

            engineThings.add(jobExecution);

            return new SubmitJobResponse(true, jobExecution);
        }
        return new SubmitJobResponse(false, null);
    }
}
