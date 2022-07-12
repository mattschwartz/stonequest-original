package com.barelyconscious.game.entity;

import lombok.Getter;

/**
 * The current state of the game - things like:
 * - Heroes: their stats, equipment, abilities, etc
 * - Inventory
 * - Enemies in the world
 * - Doodads in the world like loot, chests, doors
 */
public class GameState {

    @Getter
    public static class GameStateData {

        private Hero heroSlot1;
        private Hero heroSlot2;
        private Hero heroSlot3;

        private Inventory backpack;
        private Inventory resourcesInventory;
        private Inventory toolsInventory;

        private GameStateData() {
        }
    }

    private final GameStateData data;

    public GameState() {
        this.data = new GameStateData();
    }

    public void setHeroSlot(final Hero hero, final int slotId) {
        data.heroSlot1 = hero;
    }
}
