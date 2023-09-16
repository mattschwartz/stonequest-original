package com.barelyconscious.worlds.terminal.commands;

import java.util.Scanner;

public class HelpCommand extends Command {

    @Override
    public CommandLineResults run(Scanner scn, CommandLineArgs args) {
        System.out.println("Available commands");
        for (var command : Command.commands.keySet()) {
            System.out.println("  " + command);
        }
        return CommandLineResults.CONTINUE;
    }
}
