package com.barelyconscious.worlds.terminal;

import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.engine.Engine;
import com.barelyconscious.worlds.engine.Physics;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.Screen;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.GameState;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.google.common.util.concurrent.RateLimiter;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import picocli.CommandLine;

import java.io.IOException;
import java.time.Clock;
import java.util.concurrent.Callable;

public class GameRunnerTerminal {

    private static final TerminalPlayerController playerController = new TerminalPlayerController();
    private static final GameState gameState = new GameState();
    private static final Physics physics = new Physics();
    private static final Engine engine = new Engine(physics,
        Clock.systemDefaultZone(),
        RateLimiter.create(1),
        RateLimiter.create(1));
    private static World world = new World();
    private static Screen terminalScreen = new TerminalWorldScreen();

    @CommandLine.Command(name = "run", description = "Run the game in a terminal")
    static class TerminalPlayerController extends PlayerController
    implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return null;
        }
    }

    static class TerminalWorldScreen implements Screen {

        @Override
        public Camera getCamera() {
            return null;
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

    public static void main(String[] args) throws IOException {
        engine.prestart(GameInstance.instance(),
            world, terminalScreen, playerController);

        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        com.googlecode.lanterna.screen.Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
    }

}
