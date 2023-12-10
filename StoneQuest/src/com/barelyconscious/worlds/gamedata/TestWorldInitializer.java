package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.entity.wilderness.Settlement;
import com.barelyconscious.worlds.game.*;
import com.barelyconscious.worlds.common.shape.Vector;

public final class TestWorldInitializer {

    public static void createWorld(final World world) {
        GameInstance.instance().getWorld().setPlayerSettlement(
            new Settlement("Ravenfell", Vector.ZERO));

        TestMapGenerator.generateMapTiles(world);

        world.addActor(new ItemLootActor(
            new Vector(200, 175), GameItems.IRON_SHIELD.toItem()));

    }

    private TestWorldInitializer() {
    }
}
