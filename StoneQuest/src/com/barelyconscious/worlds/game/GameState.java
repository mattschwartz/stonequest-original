package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.game.hero.skill.CitizenSkill;
import com.barelyconscious.worlds.game.hero.skill.FactionSkill;
import com.barelyconscious.worlds.game.hero.skill.HeroSkill;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * The current state of the game - things like:
 * - Heroes: their stats, equipment, abilities, etc
 * - Inventory
 * - Enemies in the world
 * - Doodads in the world like loot, chests, doors
 *
 * TODO(p1): conversion between SaveGame<->GameState
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

        private final Set<HeroSkill> heroSkills = new HashSet<>();
        private final Set<CitizenSkill> citizenSkills = new HashSet<>();
        private final Set<FactionSkill> factionSkills = new HashSet<>();

        private GameStateData() {
        }
    }

    public static class Contractor {

        private PartyFunds partyFunds;
        private Wagon partyWagon;
        private ResourecePouch partyResourcePouch;
    }

    private final GameStateData data;

    public GameState() {
        this.data = new GameStateData();
    }

    public void setHeroSlot(final Hero hero, final int slotId) {
        data.heroSlot1 = hero;
    }
}
