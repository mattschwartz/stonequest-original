package com.barelyconscious.worlds.game.rng;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;
import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.gamedata.GameItems;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.game.systems.ChancellorSystem;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class TerritoryGenerator {

    public static WildernessLevelBuilder generator() {
        return new WildernessLevelBuilder();
    }

    public static class WildernessLevelBuilder {

        private Territory territory;

        public WildernessLevelBuilder territory(Territory territory) {
            this.territory = territory;
            return this;
        }

        public WildernessLevel generate() {
            WildernessLevel result = new WildernessLevel(territory);

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

            // these will go first when we can build the world around them
            spawnTravelLocations(result, territory, worldSpace);

            return result;
        }private void spawnTravelLocations(
            WildernessLevel wilderness,
            Territory territory,
            Actor[][] worldSpace
        ) {
            wilderness.addChild(new LoadTerritoryActor(
                new Vector(64 + UMath.RANDOM.nextDouble() * 64, 64 + UMath.RANDOM.nextDouble() * 64)
            ));
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
            int x = UMath.RANDOM.nextInt(NUM_TILES_ROWS);
            int y = UMath.RANDOM.nextInt(NUM_TILES_COLS);
            do {
                var tile = new Tile(0, "🔷", true, false);
                var transform = new Vector(x * 32, y * 32);

                result[x][y] = new TileActor(
                    transform,
                    tile,
                    new BetterSpriteResource("texture::water"),
                    32, 32, MouseInputHandler.instance());
                x += UMath.RANDOM.nextInt(3) - 1;
                y += UMath.RANDOM.nextInt(3) - 1;
            } while (x >= 0 && x < NUM_TILES_ROWS && y >= 0 && y < NUM_TILES_COLS);

            // randomly create 4 medium patches of grass
            for (int i = 0; i < 24; ++i) {
                int patchX;
                int patchY;
                do {
                    patchX = UMath.RANDOM.nextInt(NUM_TILES_ROWS);
                    patchY = UMath.RANDOM.nextInt(NUM_TILES_COLS);
                } while (result[patchX][patchY] != null && skips-- > 0);

                skips = maxNumSkips;
                for (int j = 0; j < 10; ++j) {
                    int patchSize = UMath.RANDOM.nextInt(3) + 1;
                    for (int k = 0; k < patchSize; ++k) {
                        for (int l = 0; l < patchSize; ++l) {
                            if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                                var tile = new Tile(0, "🟩", false, false);
                                var transform = new Vector((patchX + k) * 32, (patchY + l) * 32);

                                result[patchX + k][patchY + l] = new TileActor(
                                    transform,
                                    tile,
                                    new BetterSpriteResource("texture::grass"),
                                    32, 32, MouseInputHandler.instance());
                            }
                        }
                    }
                }
            }

            // randomly create 4 small patches of grass
            for (int i = 0; i < 24; ++i) {
                int patchX;
                int patchY;
                do {
                    patchX = UMath.RANDOM.nextInt(NUM_TILES_ROWS);
                    patchY = UMath.RANDOM.nextInt(NUM_TILES_COLS);
                } while (result[patchX][patchY] != null && skips-- > 0);

                skips = maxNumSkips;
                for (int j = 0; j < 10; ++j) {
                    int patchSize = UMath.RANDOM.nextInt(2) + 1;
                    for (int k = 0; k < patchSize; ++k) {
                        for (int l = 0; l < patchSize; ++l) {
                            if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                                var tile = new Tile(0, "🟩", false, false);
                                var transform = new Vector((patchX + k) * 32, (patchY + l) * 32);

                                result[patchX + k][patchY + l] = new TileActor(
                                    transform,
                                    tile,
                                    new BetterSpriteResource("texture::falldirt"),
                                    32, 32, MouseInputHandler.instance());
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 24; ++i) {
                int patchX;
                int patchY;
                do {
                    patchX = UMath.RANDOM.nextInt(NUM_TILES_ROWS);
                    patchY = UMath.RANDOM.nextInt(NUM_TILES_COLS);
                } while (result[patchX][patchY] != null && skips-- > 0);

                skips = maxNumSkips;

                for (int j = 0; j < 10; ++j) {
                    int patchSize = UMath.RANDOM.nextInt(2) + 1;
                    for (int k = 0; k < patchSize; ++k) {
                        for (int l = 0; l < patchSize; ++l) {
                            if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                                var tile = new Tile(0, "🟩", false, false);
                                var transform = new Vector((patchX + k) * 32, (patchY + l) * 32);

                                result[patchX + k][patchY + l] = new TileActor(
                                    transform,
                                    tile,
                                    new BetterSpriteResource("texture::fallgrass"),
                                    32, 32, MouseInputHandler.instance());
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 24; ++i) {
                int patchX;
                int patchY;
                do {
                    patchX = UMath.RANDOM.nextInt(NUM_TILES_ROWS);
                    patchY = UMath.RANDOM.nextInt(NUM_TILES_COLS);
                } while (result[patchX][patchY] != null && skips-- > 0);

                skips = maxNumSkips;

                for (int j = 0; j < 10; ++j) {
                    int patchSize = UMath.RANDOM.nextInt(2) + 1;
                    for (int k = 0; k < patchSize; ++k) {
                        for (int l = 0; l < patchSize; ++l) {
                            if (patchX + k >= 0 && patchX + k < NUM_TILES_ROWS && patchY + l >= 0 && patchY + l < NUM_TILES_COLS) {
                                var tile = new Tile(0, "🟩", false, false);
                                var transform = new Vector((patchX + k) * 32, (patchY + l) * 32);

                                result[patchX + k][patchY + l] = new TileActor(
                                    transform,
                                    tile,
                                    new BetterSpriteResource("texture::forest"),
                                    32, 32, MouseInputHandler.instance());
                            }
                        }
                    }
                }
            }

            // randomly generate a lake
            int lakeX = UMath.RANDOM.nextInt(NUM_TILES_ROWS);
            int lakeY = UMath.RANDOM.nextInt(NUM_TILES_COLS);
            int lakeSize = UMath.RANDOM.nextInt(3) + 1;
            for (int i = 0; i < lakeSize; ++i) {
                for (int j = 0; j < lakeSize; ++j) {
                    if (lakeX + i >= 0 && lakeX + i < NUM_TILES_ROWS && lakeY + j >= 0 && lakeY + j < NUM_TILES_COLS) {
                        var tile = new Tile(0, "🟦", false, false);
                        var transform = new Vector((lakeX + i) * 32, (lakeY + j) * 32);

                        result[lakeX + i][lakeY + j] = new TileActor(
                            transform,
                            tile,
                            new BetterSpriteResource("texture::water"),
                            32, 32, MouseInputHandler.instance());
                    }
                }
            }

            // fill the rest of the world with grass
            for (x = 0; x < NUM_TILES_ROWS; ++x) {
                for (y = 0; y < NUM_TILES_COLS; ++y) {
                    if (result[x][y] != null) {
                        continue;
                    }
                    var tile = new Tile(0, "🟤", false, false);
                    var transform = new Vector(x * 32, y * 32);

                    result[x][y] = new TileActor(
                        transform,
                        tile,
                        new BetterSpriteResource("texture::dirt"),
                        32, 32, MouseInputHandler.instance());
                }
            }

            return result;
        }

        private void spawnDeposits(WildernessLevel wilderness, Territory territory, Actor[][] worldSpace) {
            int maxAttempts = 30;
            int j = 0;
            for (var resource : territory.getAvailableResources()) {
                int numDeposits = (int) (NUM_DEPOSITS * resource.richness);
                for (int i = 0; i < numDeposits; ++i) {
                    Vector transform;
                    // don't place deposits on top of each other
                    do {
                        transform = new Vector(
                            UMath.RANDOM.nextInt(NUM_TILES_ROWS),
                            UMath.RANDOM.nextInt(NUM_TILES_COLS));
                        if (++j > maxAttempts) {
                            break;
                        }
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

            if (buildingsWithinTerritory == null) {
                return;
            }

            // place building randomly in map
            for (BuildingActor building : buildingsWithinTerritory) {
                Vector transform;
                // don't place buildings on top of each other
                do {
                    transform = new Vector(
                        UMath.RANDOM.nextInt(NUM_TILES_ROWS),
                        UMath.RANDOM.nextInt(NUM_TILES_COLS));
                } while (worldSpace[(int) transform.x][(int) transform.y] != null
                    && !(worldSpace[(int) transform.x][(int) transform.y] instanceof TileActor));

                var buildingActor = new BuildingActor(building.name, transform.multiply(32));

                // add appropriate sprite to the building actor

                worldSpace[(int) transform.x][(int) transform.y] = building;
                wilderness.addBuilding(buildingActor);
            }
        }

        private void spawnEnemies(WildernessLevel wilderness, Territory territory, Actor[][] worldSpace) {
            int numEnemies = (int) (NUM_ENEMY_PACKS * territory.getHostility());

            for (int i = 0; i < numEnemies; ++i) {
                String name = enemyPrefixes.get(UMath.RANDOM.nextInt(enemyPrefixes.size()))
                    + " " + enemyFrequencies.get(UMath.RANDOM.nextInt(enemyFrequencies.size())).getLeft();

                Vector transform;
                // don't place enemies on top of each other
                do {
                    transform = new Vector(
                        UMath.RANDOM.nextInt(NUM_TILES_ROWS),
                        UMath.RANDOM.nextInt(NUM_TILES_COLS));
                } while (worldSpace[(int) transform.x][(int) transform.y] != null
                    && !(worldSpace[(int) transform.x][(int) transform.y] instanceof TileActor));

                boolean isElite = UMath.RANDOM.nextDouble() < 0.25;
                boolean isBoss = UMath.RANDOM.nextDouble() < 0.05;

                var enemy = EntityFactory.anEntity()
                    .called(name)
                    .locatedAt(transform.multiply(32))
                    .withCreatureLevel(territory.getTerritoryLevel() + (isBoss ? 5 : isElite ? 2 : 0))
                    .withTrait(TraitName.CONSTITUTION, 10f)
                    .withStat(StatName.ARMOR, (isBoss ? 5f : isElite ? 2f : 0f))
                    .build();

                enemy.addComponent(new BoxColliderComponent(enemy, true, true, new Box(0, 32, 0, 32)));
                enemy.addComponent(new SpriteComponent(enemy, Resources.getSprite(ResourceSprite.SEWER_RAT), RenderLayer.ENTITIES));
                enemy.addComponent(new HealthBarComponent(enemy, enemy.getHealthComponent()));
                enemy.addComponent(new DestroyOnDeathComponent(enemy, 0));

                enemy.addComponent(new DropOnDeathComponent(enemy, GameItems.WILLOW_BARK.toItem()));
                enemy.addComponent(new AIMoveComponent(enemy, 32));

                wilderness.addEntity(enemy);
                worldSpace[(int) transform.x][(int) transform.y] = enemy;
            }
        }
    }

    // RNG base values
    public static final int NUM_ENEMY_PACKS = 50; // at 100% hostility
    public static final int NUM_RIPS = 10; // at 100% corruption
    public static final int NUM_DEPOSITS = 8; // at 100% resource availability

    public static final int NUM_TILES_ROWS = 48; // 1,024 height
    public static final int NUM_TILES_COLS = 32; // 1536 width

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
