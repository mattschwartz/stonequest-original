package com.barelyconscious.game.entity.engine;

import com.barelyconscious.game.delegate.Delegate;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Log4j2
public class JobExecution {

    @Getter
    private final String jobId;
    @Getter
    private final String createdDateTime;

    public final Delegate<Void> delegateOnJobSucceeded = new Delegate<>();
    public final Delegate<Exception> delegateOnJobFailed = new Delegate<>();
    /**
     * Delegate always fired whether the job completed successfully or not.
     */
    public final Delegate<Void> delegateOnCompleted = new Delegate<>();

    private final JobRunContext runContext;
    private final Function<JobRunContext, Void> job;
    private boolean jobExecuted;
    private final Stopwatch stopwatch;

    public JobExecution(final EventArgs eventArgs, final Function<JobRunContext, Void> job) {
        this.job = job;
        this.jobExecuted = false;
        this.jobId = UUID.randomUUID().toString();
        this.createdDateTime = Clock.systemDefaultZone().toString();
        this.stopwatch = Stopwatch.createStarted();

        this.runContext = new JobRunContext(jobId, eventArgs);
    }

    boolean isJobComplete(final EventArgs eventArgs) {
        if (!jobExecuted) {
            job.apply(runContext);
            jobExecuted = true;
        }

        final List<Exception> failedJobs = new ArrayList<>();
        runContext.setEventArgs(eventArgs);
        runContext.getYields().removeIf(cb -> {
            try {
                return cb.tickAndCall(runContext);
            } catch (final Exception e) {
                log.warn("[Job={}] Failed to update: {}", jobId, e.getMessage(), e);
                failedJobs.add(e);
            }
            return true;
        });

        if (runContext.isCompleted()) {
            log.debug("Completed job={} in {}ms", jobId, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            if (failedJobs.isEmpty()) {
                delegateOnJobSucceeded.call(null);
            } else {
                delegateOnJobFailed.call(failedJobs.get(0));
            }
            delegateOnCompleted.call(null);
            return true;
        }

        return false;
    }
}
