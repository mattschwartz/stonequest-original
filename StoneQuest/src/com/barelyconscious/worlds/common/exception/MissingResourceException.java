package com.barelyconscious.worlds.common.exception;

public class MissingResourceException extends WorldsException{

    public  MissingResourceException(final String msg) {
        super(false, true, msg, null);
    }

    public MissingResourceException(
        final String msg,
        final Throwable t
    ) {
        super(false, true, msg, t);
    }
}
