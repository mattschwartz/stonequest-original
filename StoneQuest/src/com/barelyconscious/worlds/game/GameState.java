package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.game.abilitysystem.Ability;
import com.barelyconscious.worlds.game.hero.skill.CitizenSkill;
import com.barelyconscious.worlds.game.hero.skill.FactionSkill;
import com.barelyconscious.worlds.game.hero.skill.HeroSkill;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Tracks properties that change during gameplay and are visible to every
 * entity and component.
 * <p>
 * TODO(p1): conversion between SaveGame<->GameState
 */
public class GameState {

    @Getter
    public static class GameStateData {

        public GameStateData(){}

        private HeroState heroSlot1;
        private HeroState heroSlot2;
        private HeroState heroSlot3;

        private final Set<HeroSkill> heroSkills = new HashSet<>();
        private final Set<CitizenSkill> citizenSkills = new HashSet<>();
        private final Set<FactionSkill> factionSkills = new HashSet<>();
    }

    /**
     * To answer questions about the hero state:
     * 1. What are the heroes stats?
     * 2. What are they wearing?
     * 3. What are they carrying?
     * 4. What are they casting?
     */
    public record HeroState(
        Ability currentAbility,
        Inventory backpack,
        Inventory resourcesInventory,
        Inventory toolsInventory
    ) {
    }

    public GameState(final GameInstance gameInstance) {
//        this.data = GameStateData.builder()
//            .heroSlot1(gameInstance.getHeroInGroup(GameInstance.PartySlot.LEFT))
//            .heroSlot2(gameInstance.getHeroInGroup(GameInstance.PartySlot.MIDDLE))
//            .heroSlot3(gameInstance.getHeroInGroup(GameInstance.PartySlot.RIGHT))
//            .backpack(gameInstance.getPlayerController().getInventory())
//            .build();
    }

    @Getter
    private  GameStateData data;
}
