package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;
import com.barelyconscious.worlds.game.item.GameItems;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.systems.ChancellorSystem;
import com.barelyconscious.worlds.game.types.Biome;
import com.barelyconscious.worlds.game.types.Climate;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class TestMapGenerator {
    public static void generateMapTiles(final World world) {
        createTiles(world);
    }

    private static final int[] map = new int[]{
        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
        3, 1, 1, 1, 1, 1, 6, 5, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3,
        3, 7, 7, 7, 7, 1, 6, 5, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3,
        3, 7, 7, 7, 7, 1, 6, 5, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3,
        3, 7, 7, 7, 7, 1, 6, 5, 6, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 1, 1, 1, 1, 1, 6, 5, 6, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 1, 1, 1, 1, 1, 6, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3,
        3, 1, 1, 1, 1, 1, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3,
        3, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3,
        3, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 3,
        3, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 3,
        3, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 2, 3, 3, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 4, 4, 4, 2, 2, 2, 2, 3, 3, 1, 1, 1, 1, 1, 1, 1, 3, 3, 2, 2, 2, 2, 3,
        3, 2, 4, 4, 4, 2, 2, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 2, 3, 3, 2, 1, 1, 1, 1, 3, 3, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 4, 2, 2, 2, 4, 2, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 4, 4, 4, 4, 2, 2, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 4, 2, 4, 4, 2, 2, 4, 2, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,};

    private static final Map<Integer, BetterSpriteResource> KEY_SPRITE = new HashMap<>() {{
        put(1, new BetterSpriteResource("texture::fallgrass"));
        put(2, new BetterSpriteResource("texture::falldirt"));
        put(3, new BetterSpriteResource("texture::reinforcedcaveFloor_v1"));
        put(4, new BetterSpriteResource("texture::falldirt"));
        put(5, new BetterSpriteResource("texture::cobble"));
        put(6, new BetterSpriteResource("texture::gravel"));
        put(7, new BetterSpriteResource("texture::farm"));
    }};

    private static void createTiles(final World world) {
        createTerritories(world);

        int tileWidth = 16;
        int tileHeight = 16;

        int i = 0;
        for (int x = 0; x < tileWidth; ++x) {
            for (int y = 0; y < tileHeight; ++y) {
                int key = map[
                    x + y * tileWidth
                    ];
                final BetterSpriteResource rSprite = KEY_SPRITE.get(key);

                int width = 64;
                int height = 64;

                final Tile tile = new Tile(0, "Grass", key == 3, false);
                final Vector transform = new Vector(x * width, y * height);
                final TileActor aTile = new TileActor(transform, tile, rSprite,
                    width, height, MouseInputHandler.instance());

                if (x < 3 && y < 3) {
                    aTile.setOpacity(TileActor.OpacityPresets.VISIBLE);
                } else if (x < 8 && y < 8) {
                    aTile.setOpacity(TileActor.OpacityPresets.LIGHTLY_OBSCURED);
                } else {
                    aTile.setOpacity(TileActor.OpacityPresets.HEAVILY_OBSCURED);
                }

                world.addActor(aTile);
            }
        }
    }

    private static void createTerritories(World world) {
        GameInstance gi = GameInstance.instance();
        ChancellorSystem cs = gi.getSystem(ChancellorSystem.class);

        Territory territory1 = new Territory(
            "Territory(0,0)",
            Vector.ZERO,
            1,
            Biome.FOREST,
            Climate.TEMPERATE,
            0.25,
            0.10,
            Lists.newArrayList(
                new TerritoryResource(
                    GameItems.WOOD.toItem(),
                    0.85)));
        Territory territory2 = new Territory(
            "Territory(0,1)",
            Vector.ZERO,
            2,
            Biome.FOREST,
            Climate.TEMPERATE,
            0.4,
            0.25,
            Lists.newArrayList(
                new TerritoryResource(
                    GameItems.WOOD.toItem(),
                    0.85),
                new TerritoryResource(
                    GameItems.IRON_ORE.toItem(), 0.4),
                new TerritoryResource(
                    GameItems.CHAMOMILE.toItem(), 0.6)));

        world.getTerritories().add(territory1);
        world.getTerritories().add(territory2);

        cs.claimTerritory(
            territory1,
            gi.getPlayerVillage());
        cs.claimTerritory(
            territory2,
            gi.getPlayerVillage());

        // construct a harvester in territory 1
        HarvesterBuilding harvesterBuilding = cs.constructHarvester(territory1, territory1.getAvailableResources().get(0), Vector.ZERO);

        assert harvesterBuilding != null;

        harvesterBuilding.delegateOnItemProduced.bindDelegate((item) -> {
            log.info("Produced {} x{}", item.item.getName(), item.amount);
            return null;
        });

        harvesterBuilding.delegateOnProductionHalted.bindDelegate((e) -> {
            log.info("Production halted");
            return null;
        });

        harvesterBuilding = cs.constructHarvester(territory2, territory2.getAvailableResources().get(1), Vector.ZERO);

        assert harvesterBuilding != null;

        harvesterBuilding.delegateOnItemProduced.bindDelegate((item) -> {
            log.info("Produced {} x{}", item.item.getName(), item.amount);
            return null;
        });

        harvesterBuilding.delegateOnProductionHalted.bindDelegate((e) -> {
            log.info("Production halted");
            return null;
        });
    }
}
