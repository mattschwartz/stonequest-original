package com.barelyconscious.worlds.testgamedata;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.HealthBarComponent;
import com.barelyconscious.worlds.entity.components.LightSourceComponent;
import com.barelyconscious.worlds.entity.components.MoveComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.entity.components.StatChangeOverTimeComponent;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.game.item.GameItems;
import com.barelyconscious.worlds.game.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;

public class TestHeroInitializer {

    public static final Hero HERO_NICNOLE;
    public static final Hero HERO_JOHN;
    public static final Hero HERO_PAUL;

    static {
        HERO_NICNOLE = EntityFactory.anEntity()
            .called("Nicnole")
            .locatedAt(new Vector(200, 264))
            .withCreatureLevel(3, 144, 16)
            .withTrait(TraitName.STRENGTH, 13, 13)
            .withTrait(TraitName.DEXTERITY, 9, 9)
            .withTrait(TraitName.CONSTITUTION, 18, 18)
            .withTrait(TraitName.INTELLIGENCE, 11, 11)
            .withTrait(TraitName.WISDOM, 13, 13)
            .withTrait(TraitName.CHARISMA, 11, 11)
            .buildHero(new Inventory(28), HeroClassType.MACHINIST);

        HERO_NICNOLE.addComponent(new MoveComponent(HERO_NICNOLE, 32f));
        HERO_NICNOLE.addComponent(new SpriteComponent(HERO_NICNOLE, Resources.getSprite(ResourceSprite.HERO_2), RenderLayer.ENTITIES));
        HERO_NICNOLE.addComponent(new BoxColliderComponent(HERO_NICNOLE, true, true, new Box(0, 32, 0, 32)));
        HERO_NICNOLE.addComponent(new HealthBarComponent(HERO_NICNOLE, HERO_NICNOLE.getHealthComponent()));


        HERO_JOHN = EntityFactory.anEntity()
            .called("John")
            .locatedAt(new Vector(186, 299))
            .withCreatureLevel(38, 144, 24)
            .withTrait(TraitName.STRENGTH, 10, 10)
            .withTrait(TraitName.DEXTERITY, 15, 15)
            .withTrait(TraitName.CONSTITUTION, 15, 15)
            .withTrait(TraitName.INTELLIGENCE, 11, 11)
            .withTrait(TraitName.WISDOM, 15, 15)
            .withTrait(TraitName.CHARISMA, 11, 11)
            .buildHero(new Inventory(28), HeroClassType.SHADOW_ASSASSIN);

        HERO_JOHN.addComponent(new MoveComponent(HERO_JOHN, 32f));
        HERO_JOHN.addComponent(new SpriteComponent(HERO_JOHN, Resources.getSprite(ResourceSprite.HERO_3), RenderLayer.ENTITIES));
        HERO_JOHN.addComponent(new BoxColliderComponent(HERO_JOHN, true, true, new Box(0, 32, 0, 32)));
        HERO_JOHN.addComponent(new HealthBarComponent(HERO_JOHN, HERO_JOHN.getHealthComponent()));
        HERO_JOHN.addComponent(new StatChangeOverTimeComponent(HERO_JOHN,
            HERO_JOHN.getHealthComponent(),
            6,
            0.5f,
            1f));

        HERO_PAUL = EntityFactory.anEntity()
            .called("Paul")
            .locatedAt(new Vector(200, 200))
            .withCreatureLevel(3, 144, 12)
            .withTrait(TraitName.STRENGTH, 7, 7)
            .withTrait(TraitName.DEXTERITY, 13, 13)
            .withTrait(TraitName.CONSTITUTION, 12, 12)
            .withTrait(TraitName.INTELLIGENCE, 7, 7)
            .withTrait(TraitName.WISDOM, 14, 14)
            .withTrait(TraitName.CHARISMA, 11, 11)
            .buildHero(new Inventory(28), HeroClassType.PRIEST);

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
