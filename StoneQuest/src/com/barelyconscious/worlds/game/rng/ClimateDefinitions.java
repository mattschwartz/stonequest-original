package com.barelyconscious.worlds.game.rng;

import com.barelyconscious.worlds.GameRunner;
import com.barelyconscious.worlds.common.exception.MissingResourceException;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.types.Climate;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ClimateDefinitions {

    private static final String CLIMATE_TILEMAPS_PATH = "data/climate_definition.json";

    public static final Map<Climate, List<String>> adjectivesByClimate = new HashMap<>();

    public static final Map<Climate, ClimateDefinitions.ClimateDefinition> definitionByClimate = new HashMap<>();

    @Data
    @AllArgsConstructor
    private static final class ClimateDefinition {
        public final Climate climate;
        public final List<String> tileset;
        public final double baseHostility;
        public final double baseCorruption;
        public final List<String> adjectives;

        public List<BetterSpriteResource> getSprites() {
            final List<BetterSpriteResource> sprites = new ArrayList<>();

            for (String tileName : tileset) {
                sprites.add(new BetterSpriteResource("texture::" + tileName));
            }
            return sprites;
        }
    }

    public static void loadDefinitions() {
        final Gson gson = new Gson();
        ClimateDefinitions.ClimateDefinition[] definitions;

        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(CLIMATE_TILEMAPS_PATH))
                .openStream();

            final String json = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

            definitions = gson.fromJson(json, ClimateDefinitions.ClimateDefinition[].class);

        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + CLIMATE_TILEMAPS_PATH, e);
        }

        for (ClimateDefinitions.ClimateDefinition definition : definitions) {
            adjectivesByClimate.put(definition.getClimate(), definition.adjectives);
        }
    }
}
