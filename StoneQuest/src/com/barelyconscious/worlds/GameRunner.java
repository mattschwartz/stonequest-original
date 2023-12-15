package com.barelyconscious.worlds;

import com.barelyconscious.worlds.engine.graphics.CanvasScreen;
import com.barelyconscious.worlds.engine.gui.GuiCanvas;
import com.barelyconscious.worlds.entity.CameraActor;
import com.barelyconscious.worlds.engine.Engine;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.GameResourceManager;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.engine.input.KeyInputHandler;
import com.barelyconscious.worlds.game.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.game.rng.TerritoryGenerator;
import com.barelyconscious.worlds.gamedata.TestHeroInitializer;
import com.barelyconscious.worlds.gamedata.TestTechInitializer;
import com.barelyconscious.worlds.gamedata.TestWorldInitializer;
import com.barelyconscious.worlds.gamedata.GuiInitializer;
import com.barelyconscious.worlds.module.WorldsModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class GameRunner {

    public static void main(final String[] args) {
        GameResourceManager.loadResources();
        TerritoryGenerator.loadRequiredResources();

        final String propertiesFilePath = "Worlds.properties";
        final Injector injector = Guice.createInjector(new WorldsModule(propertiesFilePath));

        final GameInstance gi = injector.getInstance(GameInstance.class);

        final JFrame frame = injector.getInstance(JFrame.class);
        final World world = injector.getInstance(World.class);
        final CanvasScreen screen = injector.getInstance(CanvasScreen.class);
        final KeyInputHandler keyInputHandler = injector.getInstance(KeyInputHandler.class);
        final Engine engine = injector.getInstance(Engine.class);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Stopping engine...");
                engine.stop();
                super.windowClosing(e);
            }
        });

        final MouseKeyboardPlayerController playerController = injector.getInstance(MouseKeyboardPlayerController.class);

        engine.prestart(gi, world, screen, playerController);

        TestTechInitializer.init();
        TestWorldInitializer.createWorld(world);
        TestHeroInitializer.createHeroes(world);

        GuiInitializer.createGui(
            injector.getInstance(GuiCanvas.class),
            world, playerController);

        world.addPersistentActor(new CameraActor(screen.getCamera()));

        frame.requestFocus();

        playerController.delegateOnQuitRequested.bindDelegate(t -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            return null;
        });

        engine.start();
        System.out.println("Saving game...");
        System.out.println("Cleaning up...");
    }
}
