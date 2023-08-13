package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityFactory;
import com.barelyconscious.worlds.entity.ItemLootActor;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.DestroyOnDeathComponent;
import com.barelyconscious.worlds.entity.components.DropOnDeathComponent;
import com.barelyconscious.worlds.entity.components.HealthBarComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.entity.ResourceNode;
import com.barelyconscious.worlds.game.item.GameItems;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.google.common.collect.Lists;
import lombok.val;

import java.util.List;

public final class TestWorldInitializer {

    public static void createWorld(final World world) {
        TestMapGenerator.generateMapTiles(world);
        createEntities(world);
        createResources(world);

        world.addActor(new ItemLootActor(
            new Vector(200, 175), GameItems.IRON_SHIELD.toItem()));

    }

    private static void createResources(final World world) {
        var ironResourceNode = new ResourceNode("Iron Ore", Vector.ZERO) {
            private List<Item> stock = Lists.newArrayList(
                GameItems.IRON_ORE.toItem(),
                GameItems.IRON_ORE.toItem(),
                GameItems.IRON_ORE.toItem(),
                GameItems.IRON_ORE.toItem());
            @Override
            public List<Item> prospect(Actor a) {
                return stock;
            }

            @Override
            public Item harvest(Actor a) {
                if (stock.isEmpty()) {
                    return null;
                }
                return stock.remove(0);
            }
        };

        world.addActor(ironResourceNode);

    }

    private static void createEntities(final World world) {

        val aRat = EntityFactory.anEntity()
            .called("Sewer Rat")
            .locatedAt(new Vector(264f, 208f))
            .withCreatureLevel(1, 0, 0)
            .withTrait(TraitName.CONSTITUTION, 10f)
            .build();

        aRat.addComponent(new BoxColliderComponent(aRat, true, true, new Box(0, 32, 0, 32)));
        aRat.addComponent(new SpriteComponent(aRat, Resources.getSprite(ResourceSprite.SEWER_RAT)));
        aRat.addComponent(new HealthBarComponent(aRat, aRat.getHealthComponent()));
        aRat.addComponent(new DestroyOnDeathComponent(aRat, 0));

        aRat.addComponent(new DropOnDeathComponent(aRat, GameItems.WILLOW_BARK.toItem()));

        world.addActor(aRat);
    }

    private TestWorldInitializer() {}
}
