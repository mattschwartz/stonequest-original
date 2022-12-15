package com.barelyconscious.worlds.entity.testgamedata;

import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.entity.GameInstance;
import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.entity.HeroClassType;
import com.barelyconscious.worlds.entity.Inventory;
import com.barelyconscious.worlds.entity.Stats;
import com.barelyconscious.worlds.entity.World;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.HealthBarComponent;
import com.barelyconscious.worlds.entity.components.HealthComponent;
import com.barelyconscious.worlds.entity.components.LightSourceComponent;
import com.barelyconscious.worlds.entity.components.MoveComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.entity.components.StatChangeOverTime;
import com.barelyconscious.worlds.entity.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.item.GameItems;
import com.barelyconscious.worlds.entity.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.entity.playercontroller.PlayerController;
import com.barelyconscious.worlds.entity.resources.ResourceSprite;
import com.barelyconscious.worlds.entity.resources.Resources;
import com.barelyconscious.worlds.shape.Box;
import com.barelyconscious.worlds.shape.Vector;

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

    public static void createHeroes(final World world, final PlayerController playerController) {
        Hero heroJohn = HERO_JOHN;
        heroJohn.addComponent(new LightSourceComponent(heroJohn, 250));
        heroJohn.addComponent(new Component(heroJohn) {
            @Override
            public void update(EventArgs eventArgs) {
                LightSourceComponent component = getParent().getComponent(LightSourceComponent.class);
                if (component != null && component.isEnabled()) {
                    if (GameInstance.instance().getHeroSelected() == HERO_JOHN) {
                        component.setOpacity(1);
                    } else {
                        component.setOpacity(0.5f);
                    }
                }
            }
        });
        world.addActor(heroJohn);
        GameInstance.instance().setHero(heroJohn, GameInstance.PartySlot.RIGHT);
        world.addActor(HERO_JOHN);

        Hero heroNicnole = HERO_NICNOLE;
        heroNicnole.addComponent(new LightSourceComponent(heroNicnole, 250));
        heroNicnole.addComponent(new Component(heroNicnole) {
            @Override
            public void update(EventArgs eventArgs) {
                LightSourceComponent component = getParent().getComponent(LightSourceComponent.class);
                if (component != null && component.isEnabled()) {
                    if (GameInstance.instance().getHeroSelected() == HERO_NICNOLE) {
                        component.setOpacity(1);
                    } else {
                        component.setOpacity(0.5f);
                    }
                }
            }
        });
        GameInstance.instance().setHero(heroNicnole, GameInstance.PartySlot.MIDDLE);
        world.addActor(heroNicnole);
        world.addActor(HERO_NICNOLE);

        Hero heroPaul = HERO_PAUL;
        heroPaul.addComponent(new LightSourceComponent(heroPaul, 250));
        heroPaul.addComponent(new Component(heroPaul) {
            @Override
            public void update(EventArgs eventArgs) {
                LightSourceComponent component = getParent().getComponent(LightSourceComponent.class);
                if (component != null && component.isEnabled()) {
                    if (GameInstance.instance().getHeroSelected() == HERO_PAUL) {
                        component.setOpacity(1);
                    } else {
                        component.setOpacity(0.5f);
                    }
                }
            }
        });
        world.addActor(heroPaul);
        GameInstance.instance().setHero(heroPaul, GameInstance.PartySlot.LEFT);
        world.addActor(HERO_PAUL);

        GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);

        setupPartyInventory(playerController);
    }

    private static void setupPartyInventory(final PlayerController pc) {
        if (!(pc instanceof MouseKeyboardPlayerController)) {
            return;
        }
        Inventory inventory = ((MouseKeyboardPlayerController) pc).getInventory();

        inventory.addItem(GameItems.WILLOW_BARK.toItem());
        inventory.addItem(GameItems.CURED_LEATHER.toItem());
        inventory.addItem(GameItems.IRON_ORE.toItem());
        inventory.addItem(GameItems.ELDRITCH_CIRCUIT.toItem());
        inventory.addItem(GameItems.ELDRITCH_CIRCUIT.toItem());
    }
}
