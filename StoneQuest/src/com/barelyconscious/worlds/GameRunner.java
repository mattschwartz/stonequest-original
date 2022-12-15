package com.barelyconscious.worlds;

import com.barelyconscious.worlds.entity.CameraActor;
import com.barelyconscious.worlds.entity.engine.Engine;
import com.barelyconscious.worlds.entity.GameInstance;
import com.barelyconscious.worlds.entity.World;
import com.barelyconscious.worlds.entity.graphics.Screen;
import com.barelyconscious.worlds.entity.input.KeyInputHandler;
import com.barelyconscious.worlds.entity.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.entity.resources.spritesheet.SpritesheetManager;
import com.barelyconscious.worlds.entity.testgamedata.TestHeroInitializer;
import com.barelyconscious.worlds.entity.testgamedata.TestWorldInitializer;
import com.barelyconscious.worlds.module.WorldsModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class GameRunner {

    public static void main(final String[] args) {
        SpritesheetManager.loadItemsSpritesheet(SpritesheetManager.Namespace.ITEMS, "sprites/items_spritesheet.json", "sprites/items_spritesheet.png");
        SpritesheetManager.loadItemsSpritesheet(SpritesheetManager.Namespace.TEXTURE, "tiles/texture_spritesheet.json", "tiles/texture_spritesheet.png");

        final String propertiesFilePath = "Worlds.properties";
        final Injector injector = Guice.createInjector(new WorldsModule(propertiesFilePath));

        final GameInstance gi = injector.getInstance(GameInstance.class);

        final JFrame frame = injector.getInstance(JFrame.class);
        final World world = injector.getInstance(World.class);
        final Screen screen = injector.getInstance(Screen.class);
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

        GuiInitializer.createGui(screen, world, playerController);
        TestHeroInitializer.createHeroes(world, playerController);
        TestWorldInitializer.createWorld(world);

        world.spawnActor(new CameraActor(screen.getCamera()));

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
