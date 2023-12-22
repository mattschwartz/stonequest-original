package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.wilderness.Settlement;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SettlementSystem implements GameSystem {

    @Getter
    @Builder
    public static class SettlementState {
        private final Settlement playerSettlement;
        private final List<Settlement> settlements = new ArrayList<>();
    }
}
