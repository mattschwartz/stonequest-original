package com.barelyconscious.worlds.common.exception;

/**
 * Thrown when trying to configure the game with invalid configuration.
 *
 * This is a fatal exception
 */
public class InvalidGameConfigurationException extends WorldsException {

    /**
     * Fatal, nonretryable exception.
     * @param msg error message to be logged
     * @param t throwable caught
     */
    public InvalidGameConfigurationException(final String msg, final Throwable t) {
        super(false, true, msg, t);
    }

    public InvalidGameConfigurationException(final String msg) {
        this(msg, null);
    }
}
