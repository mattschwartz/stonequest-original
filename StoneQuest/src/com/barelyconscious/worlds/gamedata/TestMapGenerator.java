package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.entity.wilderness.Settlement;
import com.barelyconscious.worlds.entity.wilderness.Territory;
import com.barelyconscious.worlds.entity.wilderness.WildernessLevel;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.rng.TerritoryGenerator;
import com.barelyconscious.worlds.game.systems.ChancellorSystem;
import com.barelyconscious.worlds.game.systems.SettlementSystem;
import com.barelyconscious.worlds.game.systems.WildernessSystem;
import com.barelyconscious.worlds.game.types.Biome;
import com.barelyconscious.worlds.game.types.Climate;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestMapGenerator {
    public static void generateMapTiles(final World world) {
        createTiles(world);
    }

    static Territory territory1;

    private static void createTiles(final World world) {
        createTerritories(world);
        // load the first territory into the world

        WildernessLevel wild = TerritoryGenerator.wildernessBuilder()
            .territory(territory1).generate();

        world.setWildernessLevel(wild);
    }

    // just for testing
    private static void createTerritories(World world) {
        GameInstance gi = GameInstance.instance();
        var settlement = gi.getSystem(SettlementSystem.class);
        var wild = gi.getSystem(WildernessSystem.class);

        territory1 = new Territory(
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
        wild.putTerritory(Vector.ZERO, territory1);

        Territory territory2 = new Territory(
            "Territory(0,1)",
            new Vector(0, 1),
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
        wild.putTerritory(new Vector(0, 1), territory2);

        var playerSettlement = settlement.getPlayerSettlement();
        playerSettlement.claimTerritory(Settlement.ClaimTerritoryRequest.builder()
            .territory(territory1)
            .build());
        playerSettlement.claimTerritory(Settlement.ClaimTerritoryRequest.builder()
            .territory(territory2)
            .build());

        HarvesterBuilding harvesterBuilding = playerSettlement.constructBuilding(Settlement.ConstructBuildingRequest.builder()
            .territory(territory1)
            .resource(territory1.getAvailableResources().get(0))
            .build()).harvesterBuilding;
        assert harvesterBuilding != null;


        harvesterBuilding.delegateOnItemProduced.bindDelegate((item) -> {
            log.info("Produced {} x{}", item.item.getName(), item.amount);
            return null;
        });

        harvesterBuilding.delegateOnProductionHalted.bindDelegate((e) -> {
            log.info("Production halted");
            return null;
        });

        harvesterBuilding = playerSettlement.constructBuilding(Settlement.ConstructBuildingRequest.builder()
            .territory(territory2)
            .resource(territory2.getAvailableResources().get(1))
            .build()).harvesterBuilding;

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
