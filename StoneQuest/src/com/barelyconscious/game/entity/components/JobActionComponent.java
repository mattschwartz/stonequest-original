package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;

import java.util.ArrayDeque;
import java.util.Queue;
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
public class JobActionComponent extends Component {

    public static final int DEFAULT_MAX_DEPTH = 3;

    public static class TooManyActionsException extends Exception {
    }

    private final Queue<Function<EventArgs, Void>> actionQueue = new ArrayDeque<>();
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
    public void queueAction(final Function<EventArgs, Void> a) throws TooManyActionsException {
        if (actionQueue.size() >= maxDepth) {
            throw new TooManyActionsException();
        }
        actionQueue.add(a);
    }

    public void replaceActions(final Function<EventArgs, Void> a) {
        actionQueue.clear();
        actionQueue.add(a);
    }

    public void cancelAction(final Function<EventArgs, Void> a) {
        actionQueue.remove(a);
    }

    public void cancelAllActions() {
        actionQueue.clear();
    }

    @Override
    public void update(EventArgs eventArgs) {
        // submit just 1 job to the engine per update
        final Function<EventArgs, Void> action = actionQueue.poll();
        if (action != null) {
            eventArgs.submitJob(action);
        }
    }
}
