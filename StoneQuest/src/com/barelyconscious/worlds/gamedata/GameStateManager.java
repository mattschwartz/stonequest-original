package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.Wagon;
import com.barelyconscious.worlds.entity.wilderness.Settlement;
import com.barelyconscious.worlds.game.GameState;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.systems.CollectorSystem;
import com.barelyconscious.worlds.game.systems.PartySystem;
import com.barelyconscious.worlds.game.systems.SettlementSystem;
import com.barelyconscious.worlds.game.systems.WildernessSystem;
import com.barelyconscious.worlds.game.systems.combat.CombatSystem;
import com.google.common.collect.Lists;

public class GameStateManager {

    public static GameState load() {
        final Settlement playerSettlement = new Settlement("Ravenfell");

        return GameState.builder()
            .collectorLevel(1)
            .extractorLevel(1)
            .collectorState(new CollectorSystem.CollectorState(
                Lists.newArrayList(
                    GameItems.ELDRITCH_CIRCUIT.toItem()
                ),
                Lists.newArrayList(
                    GameItems.ELDRITCH_CIRCUIT.toItem(),
                    GameItems.ELDRITCH_DRIVE.toItem(),
                    GameItems.RECOVERED_IMAGE.toItem()
                ),
                Lists.newArrayList(
                    "A circuit recovered from a piece of tech.",
                    "A drive recovered from a piece of tech.",
                    "A recovered image of a person."
                )
            ))
            .settlementState(SettlementSystem.SettlementState.builder()
                .playerSettlement(playerSettlement)
                .build()
            )
            .wildernessState(WildernessSystem.WildernessState.builder()
                .build()
            )
            .combatState(CombatSystem.CombatState.builder()
                .build()
            )
            .partyState(PartySystem.PartyState.builder()
                .gold(1044)
                .wagon(new Wagon(
                    new Inventory(32),
                    new Inventory(16)
                ))
                .build())
            .build();
    }
}
