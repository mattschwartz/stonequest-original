package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.game.*;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.val;

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
