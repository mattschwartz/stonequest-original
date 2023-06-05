package com.barelyconscious.worlds.common;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Log4j2
public final class Delegate<TArg0> {

    private final List<Function<TArg0, Void>> boundDelegates = new ArrayList<>();

    public void bindDelegate(final Function<TArg0, Void> callback) {
        boundDelegates.add(callback);
    }

    // todo idk if this works
    public void freeDelegate(final Function<TArg0, Void> callback) {
        boundDelegates.remove(callback);
    }

    public void call(final TArg0 arg0) {
        boundDelegates.forEach(t -> {
            try {
                t.apply(arg0);
            } catch (final Exception e) {
                log.warn("Delegate call failed. Continuing...", e);
            }
        });
    }
}
