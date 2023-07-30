package com.barelyconscious.worlds;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.engine.Engine;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.Physics;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.Screen;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.GameState;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.google.common.util.concurrent.RateLimiter;

import java.time.Clock;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Scanner;

public class GameRunnerCLI {

    static CliPlayerController playerController = new CliPlayerController();
    static GameState gameState = new GameState(
        GameInstance.instance());

    public static void main(String[] args) {
        engine.prestart(
            GameInstance.instance(),
            world,
            blankScreen,
            playerController);

        world.addActor(
            new PlayerPersonalDevice(
            )
        );

        boolean isRunning = true;
        while (isRunning) {
            System.out.print("> ");
            var scn = new Scanner(System.in);
            var input= scn.nextLine();

            switch (input) {
                case "tick":
                    engine.tick(new EventArgs(
                        1/60f,
                        Vector.ZERO,
                        Vector.ZERO,
                        new ArrayDeque<>(),
                        playerController,
                        world,
                        gameState
                    ));
                    break;
                case "list":
                    System.out.println("Actors:");
                    for (Actor actor : world.getActors()) {
                        System.out.println("  " + actor.name);
                    }
                    break;
                case "exit":
                case "quit":
                    isRunning = false;
                    break;
            }
        }
    }

    static Physics physics = new MockPhysics();
    static Engine engine = new Engine(physics,
        Clock.systemDefaultZone(),
        RateLimiter.create(60.0),
        RateLimiter.create(60.0));
    static World world = new World();
    static Screen blankScreen = new BlankScreen();

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  tick");
        System.out.println("  list");
        System.out.println("  exit");
        System.out.println("  help");
    }

    static class CliPlayerController extends PlayerController {
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
            return null;
        }

        @Override
        public void render(RenderContext renderContext) {

        }
    }

    static class MockPhysics extends Physics {

        @Override
        public void updatePhysics(EventArgs eventArgs, List<Actor> actors) {
            System.out.println("Updating physics");
        }
    }
}
