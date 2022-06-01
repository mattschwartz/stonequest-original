package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.playercontroller.PlayerController;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

public final class GameInstance {

    @Getter
    private static GameInstance instance;

    // todo: ewwwwwwwuh
    //  can maybe pass these through event args?
    //  should probable introduce a new component that handles configuring the delegates instead of player controller
    //  and that can be capture somewhere in engine where game instance is known
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

    public enum PartySlot {
        LEFT(0),
        MIDDLE(1),
        RIGHT(2);

        public final int index;
        PartySlot(final int index) {
            this.index = index;
        }
    }

    @Getter
    @Setter
    private Hero heroSelected;
    private final Hero[] heroParty = new Hero[PartySlot.values().length];

    private GameInstance(final World world, final PlayerController playerController) {
        this.world = world;
        this.playerController = playerController;
    }

    @Nullable
    public Hero setHero(final Hero hero, final PartySlot slot) {
        final Hero existing = heroParty[slot.index];
        heroParty[slot.index] = hero;
        return existing;
    }

    public Hero getHeroInGroup(final PartySlot slot) {
        return heroParty[slot.index];
    }

    public void changeWorld(final World world) {
        this.world.unloadWorld();

        world.loadWorld();

        this.world = world;
    }
}
