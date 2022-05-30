package com.barelyconscious.game.exception;

public class MissingResourceException extends WorldsException{

    public MissingResourceException(
        final String msg,
        final Throwable t
    ) {
        super(false, true, msg, t);
    }
}
