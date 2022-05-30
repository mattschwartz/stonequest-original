package com.barelyconscious.game;

import com.barelyconscious.game.entity.*;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.resources.ResourceSprites;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.module.WorldsModule;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import lombok.val;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class GameRunner {

    public static void main(final String[] args) {
        final String propertiesFilePath = "Worlds.properties";
        final Injector injector = Guice.createInjector(new WorldsModule(propertiesFilePath));

        final JFrame frame = injector.getInstance(JFrame.class);
        final World world = injector.getInstance(World.class);
        _populateTestWorld(world);
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

    private static void _populateTestWorld(final World world) {
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
                .addForce(Vector.RIGHT, 320f);

        world.spawnActor(aPlayer);

        val aRat = new AEntity(
            "Sewer Rat",
            new Vector(264f, 200f),
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
}
