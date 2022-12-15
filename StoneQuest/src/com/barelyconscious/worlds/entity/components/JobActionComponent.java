package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.entity.engine.JobRunContext;
import lombok.extern.log4j.Log4j2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

/**
 * Actions are queued and processed in order. All actors who can perform actions have their
 * own action queue.
 * <p>
 * todo: managing actions in an update thread will eventually lead to nondeterministic
 *  results. If an actor casts a healing spell in combat while near death, whether the
 *  actor dies as a result of a fatal blow is dependent on whether the other damaging
 *  actor is updated before or after the healing spell is processed.
 */
@Log4j2
public class JobActionComponent extends Component {

    public static final int DEFAULT_MAX_DEPTH = 3;

    public static class TooManyActionsException extends Exception {
    }

    private final Queue<Function<JobRunContext, Void>> pendingJobs = new ConcurrentLinkedQueue<>();
    private final Queue<Function<JobRunContext, Void>> activeJobs = new ConcurrentLinkedQueue<>();
    private final int maxDepth;

    public JobActionComponent(final Actor parent, final int maxDepth) {
        super(parent);
        this.maxDepth = maxDepth;
    }

    public JobActionComponent(final Actor parent) {
        this(parent, DEFAULT_MAX_DEPTH);
    }

    /**
     * @throws TooManyActionsException when caller tries to submit a job beyond the configured maxDepth. Exceeding
     *                                 maxDepth will prevent further jobs from running.
     */
    public void queueAction(final Function<JobRunContext, Void> a) throws TooManyActionsException {
        log.debug("There are {} pending jobs and {} active jobs. You have {} depth.",
            pendingJobs.size(), activeJobs.size(), maxDepth);
        if (activeJobs.size() + pendingJobs.size() >= maxDepth) {
            throw new TooManyActionsException();
        }
        pendingJobs.add(a);
    }

    public void replaceActions(final Function<JobRunContext, Void> a) {
        pendingJobs.clear();
        pendingJobs.add(a);
    }

    public void cancelAction(final Function<JobRunContext, Void> a) {
        pendingJobs.remove(a);
    }

    public void cancelAllActions() {
        pendingJobs.clear();
    }


    @Override
    public void update(final EventArgs eventArgs) {
        if (activeJobs.size() + pendingJobs.size() >= maxDepth) {
            return;
        }

        // submit just 1 job to the engine per update
        final Function<JobRunContext, Void> action = pendingJobs.peek();
        if (action != null) {
            final EventArgs.SubmitJobResponse response = eventArgs.submitJob(action);
            if (response.isSuccess()) {
                activeJobs.add(action);
                pendingJobs.remove(action);

                response.getJobExecution().delegateOnCompleted.bindDelegate(t -> {
                    activeJobs.remove(action);
                    return null;
                });

                log.debug("Submitted job {}. {} pending job(s) and {} active Job(s)",
                    response.getJobExecution().getJobId().substring(0, 8), pendingJobs.size(), activeJobs.size());
            } else {
                log.warn("Failed to submit job");
            }
        }
    }
}
