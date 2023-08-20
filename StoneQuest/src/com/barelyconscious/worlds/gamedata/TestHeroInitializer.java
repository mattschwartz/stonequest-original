package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.game.hero.HeroClassType;
import com.barelyconscious.worlds.game.item.GameItems;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.gamedata.abilities.BulletAbility;
import com.barelyconscious.worlds.gamedata.abilities.RenewAbility;

public class TestHeroInitializer {

    public static final Hero HERO_NICNOLE;
    public static final Hero HERO_JOHN;
    public static final Hero HERO_PAUL;

    static {
        HERO_NICNOLE = EntityFactory.anEntity()
            .called("Nicnole")
            .locatedAt(new Vector(200, 264))
            .withCreatureLevel(3, 144, 16)
            .withHeroClass(HeroClassType.MACHINIST)
            .withStat(StatName.ARMOR, 18f)
            .withStat(StatName.ABILITY_POWER, 6f)
            .withStat(StatName.ABILITY_SPEED, 3f)
            .buildHero(HeroClassType.MACHINIST);

        HERO_NICNOLE.addComponent(new MoveComponent(HERO_NICNOLE, 32f));
        HERO_NICNOLE.addComponent(new SpriteComponent(HERO_NICNOLE, Resources.getSprite(ResourceSprite.HERO_2), RenderLayer.ENTITIES));
        HERO_NICNOLE.addComponent(new BoxColliderComponent(HERO_NICNOLE, true, true, new Box(0, 32, 0, 32)));
        HERO_NICNOLE.addComponent(new HealthBarComponent(HERO_NICNOLE, HERO_NICNOLE.getHealthComponent()));


        HERO_JOHN = EntityFactory.anEntity()
            .called("John")
            .locatedAt(new Vector(186, 299))
            .withCreatureLevel(38, 144, 24)
            .withHeroClass(HeroClassType.THIEF)
            .withStat(StatName.ARMOR, 9f)
            .withStat(StatName.FOCUS, 10)
            .withStat(StatName.PRECISION, 2.5)
            .withStat(StatName.ABILITY_POWER, 6)
            .buildHero(HeroClassType.THIEF);

        HERO_JOHN.addComponent(new MoveComponent(HERO_JOHN, 32f));
        HERO_JOHN.addComponent(new SpriteComponent(HERO_JOHN, Resources.getSprite(ResourceSprite.HERO_3), RenderLayer.ENTITIES));
        HERO_JOHN.addComponent(new BoxColliderComponent(HERO_JOHN, true, true, new Box(0, 32, 0, 32)));
        HERO_JOHN.addComponent(new HealthBarComponent(HERO_JOHN, HERO_JOHN.getHealthComponent()));
        HERO_JOHN.addComponent(new StatChangeOverTimeComponent(HERO_JOHN,
            HERO_JOHN.getHealthComponent(),
            6,
            0.5f,
            1f));

        HERO_JOHN.getEquipment().setEquippedItem(GameItems.IRON_SWORD.toItem());

        HERO_PAUL = EntityFactory.anEntity()
            .called("Paul")
            .locatedAt(new Vector(200, 200))
            .withCreatureLevel(3, 144, 12)
            .withHeroClass(HeroClassType.PRIEST)
            .withStat(StatName.ARMOR, 3f)
            .withStat(StatName.FOCUS, -1f)
            .withStat(StatName.SPIRIT, 10f)
            .buildHero(HeroClassType.PRIEST);

        // Add some abilities
        HERO_PAUL.addComponent(new AbilityComponent(HERO_PAUL, new RenewAbility()));
        HERO_PAUL.addComponent(new AbilityComponent(HERO_PAUL, new BulletAbility()));

        // Add some equipment
        HERO_PAUL.getEquipment().setEquippedItem(GameItems.IRON_SHIELD.toItem());
        HERO_PAUL.getEquipment().setEquippedItem(GameItems.CLOTH_ROBE.toItem());

        HERO_PAUL.addComponent(new MoveComponent(HERO_PAUL, 32f));
        HERO_PAUL.addComponent(new SpriteComponent(HERO_PAUL, Resources.getSprite(ResourceSprite.HERO_1), RenderLayer.ENTITIES));
        HERO_PAUL.addComponent(new BoxColliderComponent(HERO_PAUL, true, true, new Box(0, 32, 0, 32)));
        HERO_PAUL.addComponent(new HealthBarComponent(HERO_PAUL, HERO_PAUL.getHealthComponent()));
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
        GameInstance.instance().setHero(heroPaul, GameInstance.PartySlot.LEFT);
        world.addActor(HERO_PAUL);

        GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);

        setupPartyInventory(playerController);
    }

    private static void setupPartyInventory(final PlayerController pc) {
        // todo deprecated method...
        Inventory inventory = pc.getInventory();
        if (inventory != null) {
            inventory.addItem(GameItems.WILLOW_BARK.toItem());
            inventory.addItem(GameItems.CURED_LEATHER.toItem());
            inventory.addItem(GameItems.IRON_ORE.toItem());
            inventory.addItem(GameItems.ELDRITCH_CIRCUIT.toItem());
            inventory.addItem(GameItems.ELDRITCH_CIRCUIT.toItem());
        }

        if (GameInstance.instance().getWagon() != null) {
            Inventory resourcePouch = GameInstance.instance().getWagon().getResourcePouch();
            Inventory storage = GameInstance.instance().getWagon().getStorage();

            resourcePouch.addItem(GameItems.WILLOW_BARK.toItem());
            resourcePouch.addItem(GameItems.CURED_LEATHER.toItem());
            resourcePouch.addItem(GameItems.IRON_ORE.toItem());
            resourcePouch.addItem(GameItems.ELDRITCH_CIRCUIT.toItem());
            resourcePouch.addItem(GameItems.ELDRITCH_CIRCUIT.toItem());

            storage.addItem(GameItems.IRON_SHIELD.toItem());
            storage.addItem(GameItems.CLOTH_ROBE.toItem());
            storage.addItem(GameItems.RECURVE_BOW.toItem());
        }
    }
}
