package com.barelyconscious.worlds.exception;

public abstract class WorldsException extends RuntimeException {

    public final boolean isRetryable;
    public final boolean isFatal;

    public WorldsException(
        final boolean isRetryable,
        final boolean isFatal,
        final String msg,
        final Throwable t
    ) {
        super(msg, t);

        this.isRetryable = isRetryable;
        this.isFatal = isFatal;
    }
}
