package com.barelyconscious.worlds;

import com.barelyconscious.worlds.data.dynamodb.model.RecipeItem;
import com.barelyconscious.worlds.engine.graphics.CanvasScreen;
import com.barelyconscious.worlds.entity.CameraActor;
import com.barelyconscious.worlds.engine.Engine;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.engine.input.KeyInputHandler;
import com.barelyconscious.worlds.game.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.game.resources.spritesheet.SpritesheetManager;
import com.barelyconscious.worlds.module.DatabaseModule;
import com.barelyconscious.worlds.testgamedata.TestHeroInitializer;
import com.barelyconscious.worlds.testgamedata.TestTechInitializer;
import com.barelyconscious.worlds.testgamedata.TestWorldInitializer;
import com.barelyconscious.worlds.module.GuiInitializer;
import com.barelyconscious.worlds.module.WorldsModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

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
