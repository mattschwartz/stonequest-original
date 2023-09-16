package com.barelyconscious.worlds.terminal.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandLineArgsTest {

    @Test
    void testParse() {
        CommandLineArgs args = new CommandLineArgs();

        CommandLineArgs actual = CommandLineArgs.parse(
            "command -t --f=flag_value --f2=flag_value2 parameter1"
        );

        assertEquals("command", actual.commandName);

        assertEquals(1, actual.switches.size());
        assertEquals("t", actual.switches.get(0));

        assertEquals(2, actual.flags.size());
        assertTrue(actual.flags.get("f").equals("flag_value"));
        assertTrue(actual.flags.get("f2").equals("flag_value2"));

        assertEquals(1, actual.parameters.size());
        assertEquals("parameter1", actual.parameters.get(0));

    }
}
