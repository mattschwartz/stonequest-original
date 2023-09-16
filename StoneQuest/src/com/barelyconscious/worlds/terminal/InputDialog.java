package com.barelyconscious.worlds.terminal;

import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class InputDialog {

    public static QuestionBuilder question(String question) {
        return new QuestionBuilder(question);
    }

    public static <T> PollBuilder<T> pollObjects(
        String prompt,
        List<T> objects
    ) {
        return new PollBuilder<>(prompt, objects);
    }

    public static class PollBuilder<T> {

        private final String prompt;
        private final List<T> objects;
        private final List<String> optionStrings = new ArrayList<>();

        public PollBuilder<T> withFormatter(
            Function<T, String> getPromptString // to be able to format the text of each option
        ) {
            for (T object : objects) {
                optionStrings.add(getPromptString.apply(object));
            }

            return this;
        }

        public @Nullable T prompt(Scanner scn, boolean allowRetry) {
            do {
                System.out.println(prompt);

                for (int i = 0; i < optionStrings.size(); ++i) {
                    System.out.printf("  [%d] %s\n", i + 1, optionStrings.get(i));
                }
                System.out.printf("  [%d] %s\n", optionStrings.size() + 1, "Nevermind");
                System.out.print("> ");

                int choice = InputDialog.tryGetInt(scn);
                if (choice == optionStrings.size() + 1) {
                    return null;
                }
                if (choice > 0 && choice <= optionStrings.size()) {
                    try {
                        return objects.get(choice - 1);
                    } catch (Exception e) {
                        System.out.println("Invalid choice: " + e.getMessage() + "\n");
                    }
                } else {
                    System.out.println("Invalid choice!\n");
                }
            } while (allowRetry);

            return null;
        }

        private PollBuilder(String prompt, List<T> objects) {
            this.prompt = prompt;
            this.objects = objects;
        }
    }

    public static class QuestionBuilder {

        private final List<Pair<String, Callable<Void>>> options = new ArrayList<>();

        private final String question;

        public QuestionBuilder answer(String option, Callable<Void> action) {
            options.add(Pair.of(option, action));
            return this;
        }

        /**
         * @return true if the user chose an option, false otherwise
         */
        public boolean ask(Scanner scn, boolean allowRetry) {

            do {
                System.out.println(question);

                for (int i = 0; i < options.size(); ++i) {
                    var opt = options.get(i);
                    System.out.printf("  [%d] %s\n", i + 1, opt.getLeft());
                }
                System.out.printf("  [%d] %s\n", options.size() + 1, "Nevermind");
                System.out.print("> ");
                int choice = InputDialog.tryGetInt(scn);
                if (choice == options.size() + 1) {
                    return false;
                }
                if (choice > 0 && choice <= options.size()) {
                    try {
                        options.get(choice - 1).getRight().call();
                        return true;
                    } catch (Exception e) {
                        System.out.println("Invalid choice: " + e.getMessage() + "\n");
                    }
                } else {
                    System.out.println("Invalid choice!\n");
                }
            } while (allowRetry);

            return false;
        }

        private QuestionBuilder(String question) {
            this.question = question;
        }
    }

    public static int tryGetInt(Scanner scn) {
        String input = scn.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
