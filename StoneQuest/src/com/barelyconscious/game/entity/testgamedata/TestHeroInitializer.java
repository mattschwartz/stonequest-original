package com.barelyconscious.game.entity.testgamedata;

import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.HeroClassType;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.HealthBarComponent;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.components.StatChangeOverTime;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.item.GameItems;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;

import java.util.HashMap;

public class TestHeroInitializer {

    public static final Hero HERO_NICNOLE;
    public static final Hero HERO_JOHN;
    public static final Hero HERO_PAUL;

    static {
        HERO_NICNOLE = new Hero(
            "Nicnole",
            new Vector(200, 264),
            3,
            14,
            16,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.STRENGTH, 13f);
                put(Stats.StatName.DEXTERITY, 9f);
                put(Stats.StatName.CONSTITUTION, 18f);
                put(Stats.StatName.INTELLIGENCE, 11f);
                put(Stats.StatName.WISDOM, 13f);
                put(Stats.StatName.CHARISMA, 11f);
            }}),
            144f,
            new Inventory(28),
            HeroClassType.MACHINIST);
        HERO_NICNOLE.addComponent(new MoveComponent(HERO_NICNOLE, 32f));
        HERO_NICNOLE.addComponent(new SpriteComponent(HERO_NICNOLE, Resources.getSprite(ResourceSprite.HERO_2), RenderLayer.ENTITIES));
        HERO_NICNOLE.addComponent(new BoxColliderComponent(HERO_NICNOLE, true, true, new Box(0, 32, 0, 32)));
        HERO_NICNOLE.addComponent(new HealthBarComponent(HERO_NICNOLE, HERO_NICNOLE.getComponent(HealthComponent.class)));

        HERO_JOHN = new Hero(
            "John",
            new Vector(186, 299),
            38,
            24,
            24,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.STRENGTH, 10f);
                put(Stats.StatName.DEXTERITY, 15f);
                put(Stats.StatName.CONSTITUTION, 15f);
                put(Stats.StatName.INTELLIGENCE, 11f);
                put(Stats.StatName.WISDOM, 15f);
                put(Stats.StatName.CHARISMA, 11f);
            }}),
            144f,
            new Inventory(28),
            HeroClassType.SHADOW_ASSASSIN);
        HERO_JOHN.addComponent(new MoveComponent(HERO_JOHN, 32f));
        HERO_JOHN.addComponent(new SpriteComponent(HERO_JOHN, Resources.getSprite(ResourceSprite.HERO_3), RenderLayer.ENTITIES));
        HERO_JOHN.addComponent(new BoxColliderComponent(HERO_JOHN, true, true, new Box(0, 32, 0, 32)));
        HERO_JOHN.addComponent(new HealthBarComponent(HERO_JOHN, HERO_JOHN.getComponent(HealthComponent.class)));
        HERO_JOHN.addComponent(new StatChangeOverTime(HERO_JOHN,
            HERO_JOHN.getComponent(HealthComponent.class),
            6,
            0.5f,
            1f));

        HERO_PAUL = new Hero(
            "Paul",
            new Vector(200, 200),
            3,
            11,
            12,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.STRENGTH, 7f);
                put(Stats.StatName.DEXTERITY, 13f);
                put(Stats.StatName.CONSTITUTION, 12f);
                put(Stats.StatName.INTELLIGENCE, 7f);
                put(Stats.StatName.WISDOM, 14f);
                put(Stats.StatName.CHARISMA, 15f);
            }}),
            144f,
            new Inventory(28),
            HeroClassType.PRIEST);

        HERO_PAUL.getEquipment().setEquippedItem(GameItems.IRON_SHIELD.toItem());
        HERO_PAUL.getEquipment().setEquippedItem(GameItems.CLOTH_ROBE.toItem());

        HERO_PAUL.addComponent(new MoveComponent(HERO_PAUL, 32f));
        HERO_PAUL.addComponent(new SpriteComponent(HERO_PAUL, Resources.getSprite(ResourceSprite.HERO_1), RenderLayer.ENTITIES));
        HERO_PAUL.addComponent(new BoxColliderComponent(HERO_PAUL, true, true, new Box(0, 32, 0, 32)));
        HERO_PAUL.addComponent(new HealthBarComponent(HERO_PAUL, HERO_PAUL.getComponent(HealthComponent.class)));
    }

    public static void createHeroes() {
        World world = GameInstance.getInstance().getWorld();

        Hero heroJohn = HERO_JOHN;
        world.spawnActor(heroJohn);
        GameInstance.getInstance().setHero(heroJohn, GameInstance.PartySlot.RIGHT);
        world.spawnActor(HERO_JOHN);

        Hero heroNicnole = HERO_NICNOLE;
        GameInstance.getInstance().setHero(heroNicnole, GameInstance.PartySlot.MIDDLE);
        world.spawnActor(heroNicnole);
        world.spawnActor(HERO_NICNOLE);

        Hero heroPaul = HERO_PAUL;
        world.spawnActor(heroPaul);
        GameInstance.getInstance().setHero(heroPaul, GameInstance.PartySlot.LEFT);
        world.spawnActor(HERO_PAUL);

        GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);

        setupPartyInventory();
    }

    private static void setupPartyInventory() {
        Inventory inventory = GameInstance.getInstance().getPlayerController().getInventory();

        inventory.addItem(GameItems.WILLOW_BARK.toItem());
        inventory.addItem(GameItems.CURED_LEATHER.toItem());
        inventory.addItem(GameItems.IRON_ORE.toItem());
        inventory.addItem(GameItems.STREAM_DRIVE.toItem());
        inventory.addItem(GameItems.STREAM_DRIVE.toItem());
    }
}
