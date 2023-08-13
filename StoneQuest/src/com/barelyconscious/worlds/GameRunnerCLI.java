package com.barelyconscious.worlds;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.engine.Engine;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.Physics;
import com.barelyconscious.worlds.engine.graphics.IRenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.Screen;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.PartyWagon;
import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.entity.components.AbilityComponent;
import com.barelyconscious.worlds.entity.components.MoveComponent;
import com.barelyconscious.worlds.game.*;
import com.barelyconscious.worlds.game.abilitysystem.Ability;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.gamedata.TestHeroInitializer;
import com.barelyconscious.worlds.gamedata.TestWorldInitializer;
import com.google.common.util.concurrent.RateLimiter;

import java.awt.image.BufferStrategy;
import java.time.Clock;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Scanner;

import static com.barelyconscious.worlds.gamedata.TestHeroInitializer.*;

public class GameRunnerCLI {

    static CliPlayerController playerController = new CliPlayerController();
    static GameState gameState = new GameState();
    static Physics physics = new MockPhysics();
    static Engine engine = new Engine(physics,
        Clock.systemDefaultZone(),
        RateLimiter.create(60.0),
        RateLimiter.create(60.0));
    static World world = new World();
    static Screen blankScreen = new BlankScreen();

    public static void main(String[] args) {
        engine.prestart(
            GameInstance.instance(),
            world,
            blankScreen,
            playerController);

        var pw = new PartyWagon(
            new Inventory(8),
            new Inventory(8));
        world.addActor(pw);
        playerController.setPartyWagon(pw);

        TestHeroInitializer.createHeroes(world, playerController);
        TestWorldInitializer.createWorld(world);

        GameInstance.instance().setHero(HERO_JOHN, GameInstance.PartySlot.LEFT);
        GameInstance.instance().setHero(HERO_NICNOLE, GameInstance.PartySlot.MIDDLE);
        GameInstance.instance().setHero(HERO_PAUL, GameInstance.PartySlot.RIGHT);

        GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);

        System.out.println("\n+---------------------+");
        System.out.println("|  Worlds v1.0 - CLI  |");
        System.out.println("+---------------------+");
        System.out.println("\nEnter a command");

        var scn = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            var input = scn.nextLine();

            int continuationResult = playerController.handleInput(scn, input);
            switch (continuationResult) {
                case CliPlayerController.CONTINUE:
                    break;
                case CliPlayerController.TICK:
                    System.out.println("The world is updating...");
                    engine.tick(new EventArgs(6, Vector.ZERO, Vector.ZERO, new ArrayDeque<>(),
                        playerController, world, gameState));
                    break;
                case CliPlayerController.EXIT:
                    System.exit(0);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // TYPES
    ////////////////////////////////////////////////////////////////////////////

    static class CliPlayerController extends PlayerController {

        static final int CONTINUE = 0;
        static final int TICK = 1;
        static final int EXIT = 2;

        /**
         * @return true if the game should continue running, false otherwise
         */
        public int handleInput(Scanner scn, String input) {
            switch (input) {
                case "list":
                    System.out.println("Actors: " + world.getActors().size());
                    break;

                case "look":
                    System.out.println("\tNearby entities:");
                    for (Actor actor : world.getActors()) {
                        if (actor instanceof EntityActor) {
                            System.out.printf("\t%s (%s)\n", actor.name, actor.id);
                        }
                    }
                    break;
                case "examine":
                    System.out.println("Examine what?");
                    System.out.print("\t> ");
                    String examineChoice = scn.nextLine();
                    if (examineChoice.equals("self")) {
                        System.out.println("You are " + GameInstance.instance().getHeroSelected().name);
                    } else {
                        world.findActorById(examineChoice).ifPresentOrElse(actor -> {
                            System.out.println("You see " + actor.name);
                        }, () -> {
                            world.findActorByName(examineChoice).ifPresentOrElse(actor -> {
                                System.out.println("You see " + actor.name);
                                if (actor instanceof EntityActor) {
                                    prettyPrintStats((EntityActor) actor);
                                }
                            }, () -> {
                                System.out.println("You don't see anything like that.");
                            });
                        });

                    }
                    break;

                case "whoami":
                    System.out.println("You are " + GameInstance.instance().getHeroSelected().name);
                    break;
                case "party":
                    System.out.println("Party:");
                    for (var slotId : GameInstance.PartySlot.values()) {
                        System.out.println("  " + GameInstance.instance().getHeroBySlot(slotId).name);
                    }
                    break;
                case "cast":
                    List<AbilityComponent> abilities = GameInstance.instance().getHeroSelected().getComponentsOfType(AbilityComponent.class);
                    if (abilities != null) {
                        System.out.println("You know the following abilities:");
                        for (int i = 0; i < abilities.size(); i++) {
                            System.out.println("  " + i + ": " + abilities.get(i).getAbility().getName());
                        }

                        System.out.print("Cast what?\n\t> ");
                        String choice = scn.nextLine();
                        try {
                            int choiceNum = Integer.parseInt(choice);
                            if (choiceNum >= 0 && choiceNum < abilities.size()) {
                                System.out.println("Casting: " + abilities.get(choiceNum).getAbility().getName());
                                Ability.ActionResult result = abilities.get(choiceNum).getAbility().enact(AbilityContext.builder()
                                    .world(world)
                                    .caster(GameInstance.instance().getHeroSelected())
                                    .build());
                                if (!result.success()) {
                                    System.out.println("Failed to cast ability: " + result.message());
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice. Try again next time");
                        }
                    } else {
                        System.out.println("No learned abilities.");
                    }
                    return TICK;

                case "stats":
                    prettyPrintStats(GameInstance.instance().getHeroSelected());

                    return CONTINUE;

                case "facing":
                    Vector facing = GameInstance.instance().getHeroSelected().facing;
                    if (Vector.UP.equals(facing)) {
                        System.out.println("Facing: NORTH");
                    } else if (Vector.DOWN.equals(facing)) {
                        System.out.println("Facing: SOUTH");
                    } else if (Vector.LEFT.equals(facing)) {
                        System.out.println("Facing: WEST");
                    } else if (Vector.RIGHT.equals(facing)) {
                        System.out.println("Facing: EAST");
                    } else {
                        System.out.println("Facing: " + facing);
                    }
                    return CONTINUE;

                case "north":
                    GameInstance.instance().getHeroSelected().facing = Vector.UP;
                    return TICK;
                case "south":
                    GameInstance.instance().getHeroSelected().facing = Vector.DOWN;
                    return TICK;
                case "east":
                    GameInstance.instance().getHeroSelected().facing = Vector.RIGHT;
                    return TICK;
                case "west":
                    GameInstance.instance().getHeroSelected().facing = Vector.LEFT;
                    return TICK;

                case "switch 1":
                    GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);
                    return CONTINUE;
                case "switch 2":
                    GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.MIDDLE);
                    return CONTINUE;
                case "switch 3":
                    GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);
                    return CONTINUE;

                case "inv":
                case "i":
                case "bag":
                    var pouch = playerController.getPartyWagon().getResourcePouch();
                    var storage = playerController.getPartyWagon().getStorage();
                    System.out.printf("Pouch (%d/%d):\n", pouch.currentSize, pouch.size);
                    for (var item : pouch.getItems()) {
                        if (item != null && item.item != null) {
                            System.out.println("\t" + item.item.getName() + " x" + item.stackSize);
                        } else {
                            System.out.println("\t------------");
                        }
                    }

                    System.out.printf("Storage (%d/%d):\n", storage.currentSize, storage.size);
                    for (var item : storage.getItems()) {
                        if (item != null && item.item != null) {
                            System.out.println("\t" + item.item.getName() + " x" + item.stackSize);
                        } else {
                            System.out.println("\t------------");
                        }
                    }


                    break;
                case "exit":
                case "quit":
                    return EXIT;
            }

            return CONTINUE;
        }

        private void prettyPrintStats(final EntityActor entityActor) {
            System.out.println("\t+--- TRAITS ---+--------------------------------+--------- STATS --------+-------------------------+");
            System.out.println("\t|              |  Offenses                      | Defenses               |  Resources              |");
            System.out.printf("\t|  STR     %2.0f  +--------------------------------+------------------------+-------------------------+%n",
                entityActor.trait(TraitName.STRENGTH).get().getCurrentValue());
            System.out.printf("\t|  DEX     %2.0f  |                                |  Health       %3.0f/%3.0f  |                         |%n",
                entityActor.trait(TraitName.DEXTERITY).get().getCurrentValue(),
                entityActor.stat(StatName.HEALTH).get().getCurrentValue(),
                entityActor.stat(StatName.HEALTH).get().getMaxValue());
            System.out.printf("\t|  CON     %2.0f  |  Ability Power        %3.0f/%3.0f  |  Armor        %3.0f/%3.0f  |  Energy        %3.0f/%3.0f  |%n",
                entityActor.trait(TraitName.CONSTITUTION).get().getCurrentValue(),
                entityActor.stat(StatName.ABILITY_POWER).get().getCurrentValue(),
                entityActor.stat(StatName.ABILITY_POWER).get().getMaxValue(),

                entityActor.stat(StatName.ARMOR).get().getCurrentValue(),
                entityActor.stat(StatName.ARMOR).get().getMaxValue(),

                entityActor.stat(StatName.ENERGY).get().getCurrentValue(),
                entityActor.stat(StatName.ENERGY).get().getMaxValue());

            System.out.printf("\t|  INT     %2.0f  |  Ability Speed        %3.0f/%3.0f  |  Fortitude    %3.0f/%3.0f  |  Focus         %3.0f/%3.0f  |%n",
                entityActor.trait(TraitName.INTELLIGENCE).get().getCurrentValue(),
                entityActor.stat(StatName.ABILITY_SPEED).get().getCurrentValue(),
                entityActor.stat(StatName.ABILITY_SPEED).get().getMaxValue(),

                entityActor.stat(StatName.FORTITUDE).get().getCurrentValue(),
                entityActor.stat(StatName.FORTITUDE).get().getMaxValue(),

                entityActor.stat(StatName.FOCUS).get().getCurrentValue(),
                entityActor.stat(StatName.FOCUS).get().getMaxValue());
            System.out.printf("\t|  FAI     %2.0f  |  Precision            %3.0f/%3.0f  |  Warding      %3.0f/%3.0f  |  Spirit        %3.0f/%3.0f  |%n",
                entityActor.trait(TraitName.FAITH).get().getCurrentValue(),
                entityActor.stat(StatName.PRECISION).get().getCurrentValue(),
                entityActor.stat(StatName.PRECISION).get().getMaxValue(),
                entityActor.stat(StatName.WARDING).get().getCurrentValue(),
                entityActor.stat(StatName.WARDING).get().getMaxValue(),
                entityActor.stat(StatName.SPIRIT).get().getCurrentValue(),
                entityActor.stat(StatName.SPIRIT).get().getMaxValue());
            System.out.println("\t+--------------+--------------------------------+------------------------+-------------------------+");

        }
    }


    static class BlankScreen implements Screen {

        final Camera blindCamera = new Camera(0, 0);

        @Override
        public Camera getCamera() {
            return blindCamera;
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public void resizeScreen(int newWidth, int newHeight) {

        }

        @Override
        public void clear() {

        }

        @Override
        public RenderContext createRenderContext() {
            return new MockRenderContext(blindCamera);
        }

        @Override
        public void render(RenderContext renderContext) {
        }
    }

    static class MockRenderContext extends RenderContext {

        public MockRenderContext(Camera camera) {
            super(null, camera);
        }
    }

    static class MockPhysics extends Physics {

        @Override
        public void updatePhysics(EventArgs eventArgs, List<Actor> actors) {
        }
    }
}
