package com.barelyconscious.worlds;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.Engine;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.Physics;
import com.barelyconscious.worlds.engine.graphics.Screen;
import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.game.*;
import com.barelyconscious.worlds.game.systems.ChancellorSystem;
import com.barelyconscious.worlds.gamedata.TestHeroInitializer;
import com.barelyconscious.worlds.gamedata.TestWorldInitializer;
import com.barelyconscious.worlds.terminal.BlankScreen;
import com.barelyconscious.worlds.terminal.TerminalPlayerController;
import com.google.common.util.concurrent.RateLimiter;

import java.time.Clock;
import java.util.ArrayDeque;
import java.util.Scanner;

import static com.barelyconscious.worlds.gamedata.TestHeroInitializer.*;

public class GameRunnerCLI {

    static GameState gameState = new GameState();
    static Physics physics = new Physics();
    //new MockPhysics();
    static Engine engine = new Engine(physics,
        Clock.systemDefaultZone(),
        RateLimiter.create(60.0),
        RateLimiter.create(60.0));
    static World world = new World();
    static Screen blankScreen = new BlankScreen();

    static TerminalPlayerController playerController = new TerminalPlayerController(new Scanner(System.in), world);

    public static void main(String[] args) {
        setUpGame();
        tick();

        System.out.println("\n+---------------------+");
        System.out.println("|  Worlds v1.0 - CLI  |");
        System.out.println("+---------------------+");
        System.out.println("\nEnter a command");

        while (true) {
            System.out.print("> ");

            int continuationResult = playerController.handleInput();
            switch (continuationResult) {
                case TerminalPlayerController.CONTINUE:
                    break;
                case TerminalPlayerController.TICK:
                    System.out.println("The world is updating...");
                    tick();
                    break;
                case TerminalPlayerController.EXIT:
                    System.exit(0);
            }
        }
    }

    public static void tick() {
        engine.tick(new EventArgs(1.5, Vector.ZERO, Vector.ZERO, new ArrayDeque<>(),
            playerController, world, gameState));
    }

    private static void setUpGame( ) {
        GameInstance gi = GameInstance.instance();
        gi.registerSystem(new ChancellorSystem());

        engine.prestart(
            gi,
            world,
            blankScreen,
            playerController);

        var pw = new Wagon(
            new Inventory(8),
            new Inventory(8));
        world.addActor(pw);
        gi.setWagon(pw);

        TestHeroInitializer.createHeroes(world);
        TestWorldInitializer.createWorld(world);

        gi.setHero(HERO_JOHN, GameInstance.PartySlot.LEFT);
        gi.setHero(HERO_NICNOLE, GameInstance.PartySlot.MIDDLE);
        gi.setHero(HERO_PAUL, GameInstance.PartySlot.RIGHT);

        gi.setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);
    }
}
