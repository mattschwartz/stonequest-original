package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.entity.wilderness.Settlement;
import com.barelyconscious.worlds.game.*;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.systems.SettlementSystem;

public final class TestWorldInitializer {

    public static void createWorld(final World world) {
        TestMapGenerator.generateMapTiles(world);

        world.addActor(new ItemLootActor(
            new Vector(200, 175), GameItems.IRON_SHIELD.toItem()));

    }

    private TestWorldInitializer() {
    }
}
