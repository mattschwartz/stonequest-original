package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.game.*;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.DestroyOnDeathComponent;
import com.barelyconscious.worlds.entity.components.DropOnDeathComponent;
import com.barelyconscious.worlds.entity.components.HealthBarComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.item.GameItems;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.google.common.collect.Lists;
import lombok.val;

public final class TestWorldInitializer {

    public static void createWorld(final World world) {
        GameInstance.instance().setPlayerVillage(new Village("Ravenfell", Vector.ZERO));

        TestMapGenerator.generateMapTiles(world);
        createEntities(world);
        createResources(world);

        world.addActor(new ItemLootActor(
            new Vector(200, 175), GameItems.IRON_SHIELD.toItem()));

    }

    private static void createResources(final World world) {
        var ironResourceNode = new ResourceDeposit("Iron Ore", Vector.ZERO, Lists.newArrayList(
            GameItems.IRON_ORE.toItem(),
            GameItems.IRON_ORE.toItem(),
            GameItems.IRON_ORE.toItem(),
            GameItems.IRON_ORE.toItem()));
        var chamomileResourceNode = new ResourceDeposit("Chamomile", Vector.ZERO, Lists.newArrayList(
            GameItems.CHAMOMILE.toItem(),
            GameItems.CHAMOMILE.toItem(),
            GameItems.CHAMOMILE.toItem(),
            GameItems.CHAMOMILE.toItem()));

        world.addActor(ironResourceNode);
        world.addActor(chamomileResourceNode);

        HarvesterBuilding harvesterBuilding = GameInstance.instance().getBuildingSystem()
            .constructHarvesterBuilding(ironResourceNode, GameInstance.instance().getPlayerVillage());
        harvesterBuilding.delegateOnItemProduced.bindDelegate((item) ->

        {
            System.out.println("Produced an item: " + item.item.getName());
            return null;
        });
        harvesterBuilding.delegateOnProductionHalted.bindDelegate((e) ->

        {
            System.out.println("Production halted");
            return null;
        });

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

        world.addActor(aRat);
    }

    private TestWorldInitializer() {
    }
}
