package com.barelyconscious.worlds.game.rng;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.ChancellorSystem;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Random;

public class TerritoryGeneration {
    /**
     * properties that qualify this territory and are used
     * for generation in the world
     */
//    public static class TerritoryProperties {
//        Biome biome;
//        Climate climate;
//        String faction;
//        double hostility; // 0-1
//        double corruption; // 0-1
//    }

    // RNG base values
    public static final int NUM_ENEMY_PACKS = 50; // at 100% hostility
    public static final int NUM_RIPS = 10; // at 100% corruption
    public static final int NUM_DEPOSITS = 8; // at 100% resource availability

    public static final int NUM_TILES_ROWS = 16; // 1,024 height
    public static final int NUM_TILES_COLS = 24; // 1536 width

    public WildernessLevel generateTerritory(Territory territory) {
        WildernessLevel result = new WildernessLevel(); // empty container actor

        Actor[][] worldSpace = new Actor[NUM_TILES_ROWS][NUM_TILES_COLS];

        // determine tileset based on biome
        // based on climate and biome, determine weather
        // place tiles based on tileset, plus some good old fashioned RNG
        // place buildings that have been constructed in this territory
        spawnBuildings(result, territory, worldSpace);
        // based on resource availability, spawn deposits
        spawnDeposits(result, territory, worldSpace);
        // based on hostility, spawn enemies
        spawnEnemies(result, territory, worldSpace);
        // based on corruption, spawn rips
        // based on faction, spawn faction-specific enemies
        // spawn any special events, like side quests or bosses

        return result;
    }

    private void spawnDeposits(Actor result, Territory territory, Actor[][] worldSpace) {
        for (var resource : territory.getAvailableResources()) {
            int numDeposits = (int) (NUM_DEPOSITS * resource.richness);
            for (int i = 0; i < numDeposits; ++i) {
                Vector transform;
                // don't place deposits on top of each other
                do {
                    transform = new Vector(
                        RNG.nextInt(NUM_TILES_ROWS),
                        RNG.nextInt(NUM_TILES_COLS));
                } while (worldSpace[(int) transform.x][(int) transform.y] != null);

                var deposit = new ResourceDeposit(
                    "Deposit",
                    transform.multiply(32),
                    Lists.newArrayList());
                worldSpace[(int) transform.x][(int) transform.y] = deposit;
                result.addChild(deposit);
            }
        }
    }

    private void spawnBuildings(Actor result, Territory territory, Actor[][] worldSpace) {
        List<BuildingActor> buildingsWithinTerritory = GameInstance.instance().getSystem(ChancellorSystem.class)
            .getBuildingsWithinTerritory(territory);

        // place building randomly in map
        for (BuildingActor building : buildingsWithinTerritory) {
            Vector transform;
            // don't place buildings on top of each other
            do {
                transform = new Vector(
                    RNG.nextInt(NUM_TILES_ROWS),
                    RNG.nextInt(NUM_TILES_COLS));
            } while (worldSpace[(int) transform.x][(int) transform.y] != null);

            var buildingActor = new BuildingActor(building.name, transform.multiply(32));

            // add appropriate sprite to the building actor

            worldSpace[(int) transform.x][(int) transform.y] = building;
            result.addChild(buildingActor);
        }
    }

    static final Random RNG = new Random(1234567890L);

    private void spawnEnemies(Actor container, Territory territory, Actor[][] worldSpace) {
        int numEnemies = (int) (NUM_ENEMY_PACKS * territory.getHostility());

        for (int i = 0; i < numEnemies; ++i) {
            String name = enemyPrefixes.get(RNG.nextInt(enemyPrefixes.size()))
                + " " + enemyFrequencies.get(RNG.nextInt(enemyFrequencies.size())).getLeft();

            Vector transform;
            // don't place enemies on top of each other
            do {
                transform = new Vector(
                    RNG.nextInt(NUM_TILES_ROWS),
                    RNG.nextInt(NUM_TILES_COLS));
            } while (worldSpace[(int) transform.x][(int) transform.y] != null);

            boolean isElite = RNG.nextDouble() < 0.25;
            boolean isBoss = RNG.nextDouble() < 0.05;

            var enemy = new EntityActor(
                name,
                transform.multiply(32), // to calculate world position
                territory.getTerritoryLevel() + (isBoss ? 5 : isElite ? 2 : 0),
                0, -1, -1, -1);
            container.addChild(enemy);
            worldSpace[(int) transform.x][(int) transform.y] = enemy;
        }
    }

    static List<Pair<String, Double>> enemyFrequencies = Lists.newArrayList(
        Pair.of("goblin", 0.5),
        Pair.of("orc", 0.3),
        Pair.of("troll", 0.2),
        Pair.of("ogre", 0.1),
        Pair.of("giant", 0.05),
        Pair.of("dragon", 0.01),
        Pair.of("demon", 0.01));

    static List<String> enemyPrefixes = Lists.newArrayList(
        "Menacing",
        "Fearsome",
        "Terrifying",
        "Horrifying",
        "Monstrous",
        "Gigantic",
        "Colossal",
        "Gargantuan",
        "Hulking",
        "Massive",
        "Enormous",
        "Ferocious",
        "Savage",
        "Vicious",
        "Brutal",
        "Ruthless",
        "Merciless",
        "Spiteful",
        "Malicious",
        "Vindictive",
        "Cruel",
        "Sadistic",
        "Vile",
        "Wicked",
        "Evil",
        "Diabolical",
        "Demonic",
        "Satanic",
        "Hellish",
        "Infernal",
        "Fiendish",
        "Nefarious",
        "Villainous",
        "Sinister",
        "Depraved",
        "Corrupt");
}
