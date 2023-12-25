package com.barelyconscious.worlds.game.rng;

import com.barelyconscious.worlds.GameRunner;
import com.barelyconscious.worlds.common.exception.MissingResourceException;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.types.Biome;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Might consider putting the load functionality in TerritoryGenerator?
 * or might want to expand the responsibilities of this class
 */
public class BiomeDefinitions {

    private static final String BIOME_TILEMAPS_PATH = "data/biome_definition.json";

    public static final Map<Biome, List<BetterSpriteResource>> spritesByBiome = new HashMap<>();
    public static final Map<Biome, List<String>> nounsByBiome = new HashMap<>();

    public static final Map<Biome, BiomeDefinition> definitionByBiome = new HashMap<>();

    @Data
    @AllArgsConstructor
    private static final class BiomeDefinition {
        public final Biome biome;
        public final List<String> tileset;
        public final double baseHostility;
        public final double baseCorruption;
        public final List<String> generativeNouns;

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
        BiomeDefinition[] definitions;

        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(BIOME_TILEMAPS_PATH))
                .openStream();

            final String json = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

            definitions = gson.fromJson(json, BiomeDefinition[].class);

        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + BIOME_TILEMAPS_PATH, e);
        }

        for (BiomeDefinition definition : definitions) {
            final List<BetterSpriteResource> sprites = new ArrayList<>();

            for (String tileName : definition.getTileset()) {
                sprites.add(new BetterSpriteResource("texture::" + tileName));
            }

            spritesByBiome.put(definition.getBiome(), sprites);
            nounsByBiome.put(definition.getBiome(), definition.getGenerativeNouns());
        }
    }
}