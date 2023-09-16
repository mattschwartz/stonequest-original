package com.barelyconscious.worlds.terminal.commands;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.regex.qual.Regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

public class CommandLineArgs {

    public String commandName;

    public List<String> switches = new ArrayList<>();
    /**
     * Stored like <'--f', 'flag_value'>
     * if a flag can be specified multiple ways, then all variants are mapped to the same value
     * for seamless retrieval
     */
    public Map<String, String> flags = new HashMap<>();

    public List<String> parameters = new ArrayList<>();

    static final Pattern flagPattern = Pattern.compile("--([a-zA-Z0-9_]+)=([a-zA-Z0-9_]+)");
    static final Pattern switchPattern = Pattern.compile("-([a-zA-Z0-9_]+)");

    /**
     * Input will look like
     * > help
     * > command (subcommand) [flags] [parameters]
     * > act mine --target="Iron Ore" (command=act, subcommand=mine, flag=target, fvalue=Iron Ore)
     * > move north (command=move, parameter=north)
     *
     * @param input
     * @return
     */
    public static CommandLineArgs parse(String input) {
        checkArgument(StringUtils.isNotBlank(input), "input cannot be blank");

        CommandLineArgs result = new CommandLineArgs();
        String processedInput = input;
        Matcher matcher = flagPattern.matcher(processedInput);

        while (matcher.find()) {
            result.flags.put(matcher.group(1), matcher.group(2));
            processedInput = processedInput.replace("--" + matcher.group(1) + "=" + matcher.group(2), "");
        }

        matcher = switchPattern.matcher(processedInput);
        while (matcher.find()) {
            result.switches.add(matcher.group(1));
            processedInput = processedInput.replace("-" + matcher.group(1), "");
        }

        processedInput = processedInput.trim();
        String[] split = processedInput.split(" ");

        if (split.length == 0) {
            throw new RuntimeException("Parse exception: no command name specified");
        }

        result.commandName = split[0];
        if (split.length > 0) {
            for (var parameter : List.of(split).subList(1, split.length)) {
                if (StringUtils.isNotBlank(parameter)) {
                    result.parameters.add(parameter);
                }
            }
        }

        return result;
    }
}
