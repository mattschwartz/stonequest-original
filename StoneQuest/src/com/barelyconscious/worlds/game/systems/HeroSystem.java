package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.game.GameInstance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class HeroSystem implements GameSystem {

    public final Delegate<HeroSelectionChanged> delegateHeroSelectionChanged = new Delegate<>();

    // todo move to GameState probably
    @Getter
    @AllArgsConstructor
    public static final class HeroSelectionChanged {

        private final Hero selectedHero;
        private final PartySlot selectedPartySlot;
        private final Hero previouslySelectedHero;
        private final PartySlot previouslySelectedPartySlot;
    }

    public enum PartySlot {
        LEFT(0),
        MIDDLE(1),
        RIGHT(2);

        private final static Map<Integer, PartySlot> slotsById = new HashMap<>() {{
            put(0, LEFT);
            put(1, MIDDLE);
            put(2, RIGHT);
        }};

        public static PartySlot fromSlotId(final int id) {
            return slotsById.get(id);
        }

        public final int index;

        PartySlot(final int index) {
            this.index = index;
        }
    }

    @Builder
    public static final class HeroState {

        @Getter
        private PartySlot selectedHeroId;
        private final Hero[] heroParty = new Hero[PartySlot.values().length];
    }

    public PartySlot getSlotByHero(final Hero hero) {
        var heroParty = GameInstance.instance().getGameState().getHeroState().heroParty;

        if (heroParty[PartySlot.LEFT.index] == hero) {
            return PartySlot.LEFT;
        } else if (heroParty[PartySlot.MIDDLE.index] == hero) {
            return PartySlot.MIDDLE;
        } else {
            return PartySlot.RIGHT;
        }
    }

    public Hero getHeroBySlot(final PartySlot slot) {
        var heroParty = GameInstance.instance().getGameState().getHeroState().heroParty;

        return heroParty[slot.index];
    }

    public Hero getHeroSelected() {
        var heroParty = GameInstance.instance().getGameState().getHeroState().heroParty;
        var selectedHeroId = GameInstance.instance().getGameState().getHeroState().selectedHeroId;

        return heroParty[selectedHeroId.index];
    }

    public void setHeroSelectedSlot(final PartySlot selectedIndex) {
        final HeroState state = GameInstance.instance().getGameState().getHeroState();

        final PartySlot prevSelectedPartySlot = state.selectedHeroId;
        final Hero prevHeroSelected = state.heroParty[selectedIndex.index];

        state.selectedHeroId = selectedIndex;
        final Hero selectedHero = state.heroParty[state.selectedHeroId.index];

        delegateHeroSelectionChanged.call(new HeroSelectionChanged(selectedHero, selectedIndex, prevHeroSelected, prevSelectedPartySlot));
    }

    public void setHero(final Hero hero, final PartySlot slot) {
        var heroParty = GameInstance.instance().getGameState().getHeroState().heroParty;
        heroParty[slot.index] = hero;
    }
}
