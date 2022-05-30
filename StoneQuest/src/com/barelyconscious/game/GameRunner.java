package com.barelyconscious.game;

import com.barelyconscious.game.entity.*;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.resources.ResourceSprites;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.module.WorldsModule;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.val;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public final class GameRunner {

    public static void main(final String[] args) {
        final String propertiesFilePath = "Worlds.properties";
        final Injector injector = Guice.createInjector(new WorldsModule(propertiesFilePath));

        final JFrame frame = injector.getInstance(JFrame.class);
        final World world = injector.getInstance(World.class);
        final Screen screen = injector.getInstance(Screen.class);
        _createMap(world);
        _populateTestWorld(world, screen);
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


        engine.start();
        System.out.println("Saving game...");
        System.out.println("Cleaning up...");
        System.out.println("");
    }

    private static void _populateTestWorld(final World world, final Screen screen) {
        val aPlayer = new Hero(
            "Hero1",
            new Vector(200, 200),
            3,
            9f,
            15f,
            11,
            12,
            new Stats(),
            144f,
            new Inventory(28));
        aPlayer.addComponent(new MoveComponent(aPlayer, 1f));
        aPlayer.addComponent(new SpriteComponent(aPlayer, Resources.loadSprite(ResourceSprites.PLAYER)));
        aPlayer.addComponent(new BoxColliderComponent(aPlayer, true, true, new Box(0, 32, 0, 32)));

        aPlayer.getComponent(MoveComponent.class)
            .addForce(Vector.RIGHT, 300f);

        world.spawnActor(aPlayer);

        val aRat = new AEntity(
            "Sewer Rat",
            new Vector(264f, 208f),
            1,
            0,
            10,
            10,
            0,
            0,
            new Stats());
        aRat.addComponent(new BoxColliderComponent(aRat, true, true, new Box(0, 32, 0, 32)));
        aRat.addComponent(new SpriteComponent(aRat, Resources.loadSprite(ResourceSprites.SEWER_RAT)));

        world.spawnActor(aRat);
    }

    private static final Random RANDOM = new Random(100L);

    private static void _createMap(final World world) {
        for (int x = 0; x < 75; ++x) {
            for (int y = 0; y < 75; ++y) {
                val aTile = new Actor(
                    new Vector(x * 32f, y * 32f));

                final int grassTileId = RANDOM.nextInt(3);
                ResourceSprites rSprite;
                if (grassTileId == 0) {
                    rSprite = ResourceSprites.GRASS_1;
                } else if (grassTileId == 1) {
                    rSprite = ResourceSprites.GRASS_2;
                } else {
                    rSprite = ResourceSprites.GRASS_3;
                }

                aTile.addComponent(new SpriteComponent(aTile, Resources.loadSprite(rSprite), RenderLayer.GROUND));
                world.spawnActor(aTile);
            }
        }
    }
}
