package com.barelyconscious.worlds.engine;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JobRunContext {
    @Getter
    private final String jobId;
    @Getter
    private EventArgs eventArgs;
    private final List<YieldingCallback> yields = new ArrayList<>();

    List<YieldingCallback> getYields() {
        return yields;
    }

    public JobRunContext(final String jobId, final EventArgs e) {
        this.jobId = jobId;
        this.eventArgs = e;
    }

    public boolean isCompleted() {
        return yields.isEmpty();
    }

    public void yield(final long yieldForMillis, final Function<JobRunContext, Void> callback) {
        yields.add(new YieldingCallback(yieldForMillis, callback));
    }

    void setEventArgs(EventArgs eventArgs) {
        this.eventArgs = eventArgs;
    }
}
