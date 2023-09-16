package com.barelyconscious.worlds.game.rng;

public class TerritoryGeneration {

    enum Biome {
        FOREST,
        PLAINS,
        DESERT,
        TUNDRA,
        SWAMP,
        MOUNTAINOUS,
    }

    enum Climate {
        TEMPERATE,
        TROPICAL,
        ARID,
        COLD,
        WET,
    }

    /**
     * properties that qualify this territory and are used
     * for generation in the world
     */
    public static class TerritoryProperties {
        Biome biome;
        Climate climate;
        String faction;
        double hostility; // 0-1
        double corruption; // 0-1
    }
}
