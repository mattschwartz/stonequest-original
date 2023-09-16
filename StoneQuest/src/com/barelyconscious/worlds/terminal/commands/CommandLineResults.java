package com.barelyconscious.worlds.terminal.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandLineResults {

    public ContinuationResult continuationResult;

    public enum ContinuationResult {
        CONTINUE,
        TICK,
        EXIT
    }

    public static final CommandLineResults CONTINUE = new CommandLineResults(ContinuationResult.CONTINUE);
    public static final CommandLineResults TICK = new CommandLineResults(ContinuationResult.TICK);
    public static final CommandLineResults EXIT = new CommandLineResults(ContinuationResult.EXIT);
}
