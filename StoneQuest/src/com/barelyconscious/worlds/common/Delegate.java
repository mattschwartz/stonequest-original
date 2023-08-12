package com.barelyconscious.worlds.common;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A delegate is a list of callbacks that can be fired at any time. Only the owner of
 * a delegate should call the delegate.
 * @param <TArg0> the type of arguments passed to subscribers of this delegate
 */
@Log4j2
public final class Delegate<TArg0> {

    private final List<Function<TArg0, Void>> boundDelegates = new ArrayList<>();

    public void bindDelegate(final Function<TArg0, Void> callback) {
        boundDelegates.add(callback);
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
