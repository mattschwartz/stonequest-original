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
import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.entity.components.AbilityComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.GameState;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.game.abilitysystem.Ability;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.gamedata.TestHeroInitializer;
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

        TestHeroInitializer.createHeroes(world, playerController);

        GameInstance.instance().setHero(HERO_JOHN, GameInstance.PartySlot.LEFT);
        GameInstance.instance().setHero(HERO_NICNOLE, GameInstance.PartySlot.MIDDLE);
        GameInstance.instance().setHero(HERO_PAUL, GameInstance.PartySlot.RIGHT);

        GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);


        var scn = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            var input= scn.nextLine();
            if (!playerController.handleInput(scn, input)) {
                break;
            }

            engine.tick(new EventArgs(1/60f, Vector.ZERO, Vector.ZERO, new ArrayDeque<>(),
                playerController, world, gameState));
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // TYPES
    ////////////////////////////////////////////////////////////////////////////

    static class CliPlayerController extends PlayerController {

        /**
         * @return true if the game should continue running, false otherwise
         */
        public boolean handleInput(Scanner scn, String input) {
            switch (input) {
                case "list":
                    System.out.println("Actors:");
                    for (Actor actor : world.getActors()) {
                        System.out.println("  " + actor.name);
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
                    System.out.println("You know the following abilities:");
                    if (abilities != null) {
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
                                    .caster(GameInstance.instance().getHeroSelected())
                                    .build());
                                System.out.println("Result: " + result);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice. Try again next time");
                        }
                    } else {
                        System.out.println("No learned abilities.");
                    }
                    break;

                case "stats":
                    System.out.println("Stats:");
                    System.out.println("  Spirit: " + GameInstance.instance().getHeroSelected().stat(StatName.SPIRIT).get().getCurrentValue());
                    break;

                case "inv":
                case "i":
                case "bag":
                case "exit":
                case "quit":
                    return false;
            }
            return true;
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
            System.out.println("\t\t[ENG] Rendering");
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
            System.out.println("\t\t[ENG] Updating physics");
        }
    }
}
