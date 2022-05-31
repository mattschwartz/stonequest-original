package com.barelyconscious.game;

import com.barelyconscious.game.entity.*;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.components.*;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.ResourceSprites;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.tile.Tile;
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
        final MouseInputHandler mouseInputHandler = injector.getInstance(MouseInputHandler.class);
        _createMap(world, mouseInputHandler);
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
        aPlayer.addComponent(new SpriteComponent(aPlayer, Resources.getSprite(ResourceSprites.PLAYER)));
        aPlayer.addComponent(new BoxColliderComponent(aPlayer, true, true, new Box(0, 32, 0, 32)));
        aPlayer.addComponent(new HealthBarComponent(aPlayer));

        aPlayer.getComponent(MoveComponent.class)
            .addForce(Vector.RIGHT, 25f);

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
        aRat.addComponent(new SpriteComponent(aRat, Resources.getSprite(ResourceSprites.SEWER_RAT)));
        aRat.addComponent(new HealthBarComponent(aRat));
        aRat.addComponent(new DestroyOnDeathComponent(aRat, 5));
        aRat.addComponent(new PlayerVisibilityComponent(aRat));

        world.spawnActor(aRat);

        val aBullet = new Actor("Bullet", new Vector(264f, 100f));
        aBullet.addComponent(new MoveComponent(aBullet, 2f));
        aBullet.addComponent(new BoxColliderComponent(aBullet, false, true, new Box(0, 32, 0, 32)));
        aBullet.addComponent(new SpriteComponent(aBullet, Resources.getSprite(ResourceSprites.POTION)));

        aBullet.getComponent(BoxColliderComponent.class)
            .delegateOnOverlap.bindDelegate((col) -> {
                if (col.causedByActor != aBullet) {
                    return null;
                }

                final Actor hit = col.hit;

                final HealthComponent health = hit.getComponent(HealthComponent.class);
                if (health != null && health.isEnabled()) {
                    health.adjustHealth(-6);
                }

                screen.getCamera().transform = screen.getCamera().transform.plus(0, 45);

                aBullet.destroy();
                return null;
            });

        aBullet.getComponent(MoveComponent.class)
            .addForce(Vector.DOWN, 100f);

        world.spawnActor(aBullet);
    }

    private static final Random RANDOM = new Random(100L);

    private static void _createMap(final World world, final MouseInputHandler mouseInputHandler) {
        for (int x = 0; x < 25; ++x) {
            for (int y = 0; y < 25; ++y) {
                final int grassTileId = RANDOM.nextInt(3);
                ResourceSprites rSprite;
                if (grassTileId == 0) {
                    rSprite = ResourceSprites.GRASS_1;
                } else if (grassTileId == 1) {
                    rSprite = ResourceSprites.GRASS_2;
                } else {
                    rSprite = ResourceSprites.GRASS_3;
                }

                val aTile = new TileActor(new Vector(x * rSprite.width, y * rSprite.height), new Tile(
                    0,
                    "Grass",
                    rSprite,
                    false,
                    false),
                    rSprite.width,
                    rSprite.height,
                    mouseInputHandler);
                world.spawnActor(aTile);
            }
        }
    }
}
