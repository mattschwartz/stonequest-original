package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.wilderness.Settlement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SettlementSystem implements GameSystem {

    @Getter
    private final Settlement playerSettlement;

    @Getter
    private final List<Settlement> settlements = new ArrayList<>();

    public SettlementSystem(final Settlement playerSettlement) {
        this.playerSettlement = playerSettlement;
    }

    public void addSettlement(Settlement settlement) {
        settlements.add(settlement);
    }

    @Override
    public void update(EventArgs eventArgs) {
        GameSystem.super.update(eventArgs);
    }
}
