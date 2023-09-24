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

    static final Random RNG = new Random();

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

        Actor[][] worldSpace = createWorldSpace(result, territory);

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

    /**
     * returns the worldspace
     */
    private Actor[][] createWorldSpace(WildernessLevel wilderness, Territory territory) {
        var result = new Actor[NUM_TILES_ROWS][NUM_TILES_COLS];

        // random walker to create roads
        result = createRoads(result);

        // add every actor in result to the wilderness as children
        for (var row : result) {
            for (var actor : row) {
                if (actor != null) {
                    wilderness.addChild(actor);
                }
            }
        }

        return result;
    }

    private Actor[][] createRoads(Actor[][] result) {
        int maxNumSkips = 8;
        int skips = maxNumSkips;

        // randomly draw a path east to west
        int x = 0;
        int y = RNG.nextInt(NUM_TILES_COLS);
        do {
            result[x][y] = new Actor("🔷", new Vector(x * 32, y * 32));
            x += RNG.nextInt(3) - 1;
            y += RNG.nextInt(3) - 1;
        } while (x >= 0 && x < NUM_TILES_ROWS && y >= 0 && y < NUM_TILES_COLS);


        // randomly create 4 medium patches of grass
        for (int i = 0; i < 4; ++i) {
            int patchX;
            int patchY;
            do {
                patchX = RNG.nextInt(NUM_TILES_ROWS);
                patchY = RNG.nextInt(NUM_TILES_COLS);
            } while (result[patchX][patchY] != null && skips-- > 0);

            skips = maxNumSkips;
            for (int j = 0; j < 10; ++j) {
                int patchSize = RNG.nextInt(3) + 1;
                for (int k = 0; k < patchSize; ++k) {
                    for (int l = 0; l < patchSize; ++l) {
                        if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                            result[patchX + k][patchY + l] = new Actor("🟩", new Vector((patchX + k) * 32, (patchY + l) * 32));
                        }
                    }
                }
            }
        }

        // randomly create 4 small patches of grass
        for (int i = 0; i < 12; ++i) {
            int patchX;
            int patchY;
            do {
                patchX = RNG.nextInt(NUM_TILES_ROWS);
                patchY = RNG.nextInt(NUM_TILES_COLS);
            } while (result[patchX][patchY] != null && skips-- > 0);

            skips = maxNumSkips;
            for (int j = 0; j < 10; ++j) {
                int patchSize = RNG.nextInt(2) + 1;
                for (int k = 0; k < patchSize; ++k) {
                    for (int l = 0; l < patchSize; ++l) {
                        if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                            result[patchX + k][patchY + l] = new Actor("🟩", new Vector((patchX + k) * 32, (patchY + l) * 32));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 16; ++i) {
            int patchX;
            int patchY;
            do {
                patchX = RNG.nextInt(NUM_TILES_ROWS);
                patchY = RNG.nextInt(NUM_TILES_COLS);
            } while (result[patchX][patchY] != null && skips-- > 0);

            skips = maxNumSkips;

            for (int j = 0; j < 10; ++j) {
                int patchSize = RNG.nextInt(2) + 1;
                for (int k = 0; k < patchSize; ++k) {
                    for (int l = 0; l < patchSize; ++l) {
                        if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                            result[patchX + k][patchY + l] = new Actor("🟩", new Vector((patchX + k) * 32, (patchY + l) * 32));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 16; ++i) {
            int patchX;
            int patchY;
            do {
                patchX = RNG.nextInt(NUM_TILES_ROWS);
                patchY = RNG.nextInt(NUM_TILES_COLS);
            } while (result[patchX][patchY] != null && skips-- > 0);

            skips = maxNumSkips;

            for (int j = 0; j < 10; ++j) {
                int patchSize = RNG.nextInt(2) + 1;
                for (int k = 0; k < patchSize; ++k) {
                    for (int l = 0; l < patchSize; ++l) {
                        if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                            result[patchX + k][patchY + l] = new Actor("🟫", new Vector((patchX + k) * 32, (patchY + l) * 32));
                        }
                    }
                }
            }
        }

        return result;
    }

    private void spawnDeposits(WildernessLevel wilderness, Territory territory, Actor[][] worldSpace) {
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
                    resource.item.getName() + " Deposit",
                    transform.multiply(32),
                    Lists.newArrayList());
                worldSpace[(int) transform.x][(int) transform.y] = deposit;
                wilderness.addDeposit(deposit);
            }
        }
    }

    private void spawnBuildings(WildernessLevel wilderness, Territory territory, Actor[][] worldSpace) {
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
            wilderness.addBuilding(buildingActor);
        }
    }

    private void spawnEnemies(WildernessLevel wilderness, Territory territory, Actor[][] worldSpace) {
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
            wilderness.addEntity(enemy);
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
