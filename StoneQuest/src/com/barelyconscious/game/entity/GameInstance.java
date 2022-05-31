package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.playercontroller.PlayerController;
import lombok.Getter;
import lombok.Setter;

public final class GameInstance {

    @Getter
    private static GameInstance instance;

    // todo: ewwwwwwwuh
    public static GameInstance createInstance(final World world, final PlayerController playerController) {
        if (instance == null) {
            instance = new GameInstance(world, playerController);
        }

        return instance;
    }

    @Getter
    private World world;

    @Getter
    @Setter
    private PlayerController playerController;

    @Getter
    @Setter
    private Camera camera;

    private GameInstance(final World world, final PlayerController playerController) {
        this.world = world;
        this.playerController = playerController;
    }

    public void changeWorld(final World world) {
        this.world.unloadWorld();

        world.loadWorld();

        this.world = world;
    }
}
