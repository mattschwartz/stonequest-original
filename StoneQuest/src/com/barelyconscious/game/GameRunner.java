package com.barelyconscious.game;

import com.barelyconscious.game.entity.CameraActor;
import com.barelyconscious.game.entity.engine.Engine;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.entity.input.KeyInputHandler;
import com.barelyconscious.game.entity.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.game.entity.resources.spritesheet.SpritesheetManager;
import com.barelyconscious.game.entity.testgamedata.TestHeroInitializer;
import com.barelyconscious.game.entity.testgamedata.TestWorldInitializer;
import com.barelyconscious.game.module.WorldsModule;
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
