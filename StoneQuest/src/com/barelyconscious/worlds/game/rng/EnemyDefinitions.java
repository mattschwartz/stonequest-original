package com.barelyconscious.worlds.game.rng;

import com.barelyconscious.worlds.GameRunner;
import com.barelyconscious.worlds.common.exception.MissingResourceException;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EnemyDefinitions {

    private static final String ENEMY_DEFINITIONS_PATH = "data/enemy_definition.json";

    public static final Map<String, EnemyDefinition> definitionByEnemyType = new HashMap<>();

    @Data
    @AllArgsConstructor
    public static final class EnemyDefinition {
        public final String enemyType;
        public final List<String> adjectives;
    }

    public static void loadDefinitions() {
        final Gson gson = new Gson();
        EnemyDefinition[] definitions;

        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(ENEMY_DEFINITIONS_PATH))
                .openStream();

            final String json = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

            definitions = gson.fromJson(json, EnemyDefinition[].class);

        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + ENEMY_DEFINITIONS_PATH, e);
        }

        for (EnemyDefinition definition : definitions) {
            definitionByEnemyType.put(definition.enemyType, definition);
        }
    }
}
