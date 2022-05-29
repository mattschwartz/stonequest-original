package com.barelyconscious.game.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Delegate<TArg0> {

    private final List<Function<TArg0, Void>> boundDelegates = new ArrayList<>();

    public void bindDelegate(final Function<TArg0, Void> callback) {
        boundDelegates.add(callback);
    }

    public void call(final TArg0 arg0) {
        boundDelegates.forEach(t -> t.apply(arg0));
    }
}
