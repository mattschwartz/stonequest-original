package com.barelyconscious.worlds.entity.engine;

import com.barelyconscious.worlds.entity.World;
import com.barelyconscious.worlds.entity.playercontroller.PlayerController;
import com.barelyconscious.worlds.shape.Vector;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Queue;
import java.util.function.Function;

public class EventArgs {

    /**
     * Time in seconds since last update.
     */
    @Getter
    private final float deltaTime;

    @Getter
    private final Vector mouseScreenPos;
    @Getter
    private final Vector mouseWorldPos;

    public static boolean IS_DEBUG = false;
    public static boolean IS_VERBOSE = false;

    private boolean acceptsNewJobs = true;

    private final Queue<JobExecution> engineThings;

    @Getter
    @NonNull
    private final PlayerController playerController;
    @NonNull
    private final World currentWorld;
    @Getter
    @NonNull
    private final WorldUpdateContext worldContext;

    public EventArgs(
        final float deltaTime,
        final Vector mouseScreenPos,
        final Vector mouseWorldPos,
        final Queue<JobExecution> engineThings,
        final @NonNull PlayerController playerController,
        final @NonNull World currentWorld
    ) {
        this.deltaTime = deltaTime;
        this.mouseScreenPos = mouseScreenPos;
        this.mouseWorldPos = mouseWorldPos;
        this.engineThings = engineThings;
        this.playerController = playerController;
        this.currentWorld = currentWorld;
        this.worldContext = new WorldUpdateContext(currentWorld);
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
        private final JobExecution jobExecution;
    }

    @CanIgnoreReturnValue
    public SubmitJobResponse submitJob(final Function<JobRunContext, Void> job) {
        if (acceptsNewJobs) {
            final JobExecution jobExecution = new JobExecution(this, job);

            engineThings.add(jobExecution);

            return new SubmitJobResponse(true, jobExecution);
        }
        return new SubmitJobResponse(false, null);
    }
}
