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
        createEntities(world);

        world.addActor(new ItemLootActor(
            new Vector(200, 175), GameItems.IRON_SHIELD.toItem()));

    }

    private static void createEntities(final World world) {
        val aRat = EntityFactory.anEntity()
            .called("Sewer Rat")
            .locatedAt(new Vector(264f, 208f))
            .withCreatureLevel(1, 0, 0)
            .withTrait(TraitName.CONSTITUTION, 10f)
            .withStat(StatName.ARMOR, 1f)
            .build();

        aRat.addComponent(new BoxColliderComponent(aRat, true, true, new Box(0, 32, 0, 32)));
        aRat.addComponent(new SpriteComponent(aRat, Resources.getSprite(ResourceSprite.SEWER_RAT)));
        aRat.addComponent(new HealthBarComponent(aRat, aRat.getHealthComponent()));
        aRat.addComponent(new DestroyOnDeathComponent(aRat, 0));
        aRat.addComponent(new DropOnDeathComponent(aRat, GameItems.WILLOW_BARK.toItem()));
        aRat.addComponent(new AIMoveComponent(aRat, 32));

        world.addActor(aRat);
    }

    private TestWorldInitializer() {
    }
}
