package com.barelyconscious.worlds.common;

import com.barelyconscious.worlds.GameRunner;
import com.barelyconscious.worlds.common.exception.MissingResourceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public final class UFileLoader {

    private UFileLoader() {
    }

    public static String readFileContents(final String filepath) {
        try {
            final var sb = new StringBuilder();
            InputStream inputStream = UFileLoader.loadFile(filepath);

            try (var br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }

            return sb.toString();
        } catch (IOException e) {
            throw new MissingResourceException("Failed to load resource: " + filepath, e);
        }
    }

    public static InputStream loadFile(final String filepath) {
        try {
            return Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(filepath))
                .openStream();
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + filepath, e);
        }
    }
}
