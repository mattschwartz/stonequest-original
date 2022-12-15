package com.barelyconscious.game.entity.testgamedata;

import com.barelyconscious.game.entity.EntityActor;
import com.barelyconscious.game.entity.ItemLootActor;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.DestroyOnDeathComponent;
import com.barelyconscious.game.entity.components.DropOnDeathComponent;
import com.barelyconscious.game.entity.components.HealthBarComponent;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.hero.recipe.CraftingIngredient;
import com.barelyconscious.game.entity.hero.recipe.Recipe;
import com.barelyconscious.game.entity.item.GameItems;
import com.barelyconscious.game.entity.item.tags.CraftingToolItemTag;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import com.google.common.collect.Lists;
import lombok.val;

import java.util.HashMap;

public final class TestWorldInitializer {

    public static void createWorld(final World world) {
        TestMapGenerator.generateMapTiles(world);
        createEntities(world);

        world.spawnActor(new ItemLootActor(
            new Vector(200, 175), GameItems.IRON_SHIELD.toItem()));
    }

    private static void createEntities(final World world) {
        val aRat = new EntityActor(
            "Sewer Rat",
            new Vector(264f, 208f),
            1,
            0,
            0,
            0,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.CONSTITUTION, 10f);
            }}));
        aRat.addComponent(new BoxColliderComponent(aRat, true, true, new Box(0, 32, 0, 32)));
        aRat.addComponent(new SpriteComponent(aRat, Resources.getSprite(ResourceSprite.SEWER_RAT)));
        aRat.addComponent(new HealthBarComponent(aRat, aRat.getComponent(HealthComponent.class)));
        aRat.addComponent(new DestroyOnDeathComponent(aRat, 0));

        aRat.addComponent(new DropOnDeathComponent(aRat, GameItems.WILLOW_BARK.toItem()));

        world.spawnActor(aRat);
    }

    private static void createRecipes() {
        final Recipe aspirin = Recipe.builder()
            .name("Aspirin")
            .description("Mild analgesic")
            .requiredTools(Lists.newArrayList(CraftingToolItemTag.PULVERIZING))
            .ingredients(Lists.newArrayList(new CraftingIngredient(null, GameItems.WILLOW_BARK.getItemId(), 1)))
            .build();
    }

    private TestWorldInitializer() {}
}
