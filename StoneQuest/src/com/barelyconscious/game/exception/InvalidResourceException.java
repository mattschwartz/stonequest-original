package com.barelyconscious.game.exception;

public class InvalidResourceException extends WorldsException {

    public InvalidResourceException(String msg) {
        super(true, true, msg, null);
    }
}
