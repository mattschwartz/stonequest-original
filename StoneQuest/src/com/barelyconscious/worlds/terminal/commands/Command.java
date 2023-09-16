package com.barelyconscious.worlds.terminal.commands;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class Command {

    public static final Map<String, Command> commands = new HashMap<>();

    public static void registerCommand(String name, Command command) {
        commands.put(name.toLowerCase(), command);
    }

    public abstract CommandLineResults run(Scanner scn, CommandLineArgs args);

    public static Command findCommand(String name) {
        checkArgument(StringUtils.isNotBlank(name), "Command name cannot be blank");

        return commands.get(name.toLowerCase());
    }
}
