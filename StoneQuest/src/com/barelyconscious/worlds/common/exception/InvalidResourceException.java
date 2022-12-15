package com.barelyconscious.worlds.common.exception;

public class InvalidResourceException extends WorldsException {

    public InvalidResourceException(String msg) {
        super(true, true, msg, null);
    }
}
