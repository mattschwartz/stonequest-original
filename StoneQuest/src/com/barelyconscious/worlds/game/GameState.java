package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.game.systems.*;
import com.barelyconscious.worlds.game.systems.combat.CombatSystem;
import lombok.Builder;
import lombok.Getter;

/**
 * Tracks properties that change during gameplay and are visible to every
 * entity and component.
 * <p>
 * TODO(p1): conversion between SaveGame<->GameState
 */
@Getter
@Builder
public class GameState {

    public final Delegate<GameState> onGameStateChanged = new Delegate<>();

    private int collectorLevel;
    private int extractorLevel;
    private final CollectorSystem.CollectorState collectorState;
    private final SettlementSystem.SettlementState settlementState;
    private final WildernessSystem.WildernessState wildernessState;
    private final CombatSystem.CombatState combatState;
    private final PartySystem.PartyState partyState;
    private final ExtractorSystem.ExtractorState extractorState;
}
