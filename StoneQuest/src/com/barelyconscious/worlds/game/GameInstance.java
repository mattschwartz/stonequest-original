package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.entity.components.AbilityComponent;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Expect that pretttty much all of this is going to go away
 */
public final class GameInstance {

    public final Delegate<HeroSelectionChanged> delegateHeroSelectionChanged = new Delegate<>();

    @AllArgsConstructor
    public static final class HeroSelectionChanged {

        public final Hero selectedHero;
        public final PartySlot selectedPartySlot;
        public final Hero previouslySelectedHero;
        public final PartySlot previouslySelectedPartySlot;
    }

    private static final class InstanceHolder {
        static final GameInstance instance = new GameInstance();
    }

    public static GameInstance instance() {
        return GameInstance.InstanceHolder.instance;
    }

    private World world;

    @Getter
    @Setter
    private PlayerController playerController = new PlayerController();

    @Getter
    @Setter
    private PlayerPersonalDevice playerPersonalDevice;

    /**
     * A device used by the player to store and interact with media
     */
    @Getter
    @Setter
    private Camera camera;

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

    @Getter
    private PartySlot selectedHeroId;
    private final Hero[] heroParty = new Hero[PartySlot.values().length];

    /**
     * todo thoughts on how to do ability slots
     *
     * heroes have ability components which include all of their abilities. the hero
     * party slot only has 6 slots and each slot has a keybinding, which may change if
     * that hero is selected. the player also can bind abilities to the UI, so hero party slots
     * have 6 ability slots each and each ability slot has a keybinding, and a reference to an ability.
     */
    private Map<Hero, List<AbilityComponent>> heroAbilities = new HashMap<>();

    public PartySlot getSlotByHero(final Hero hero) {
        if (heroParty[PartySlot.LEFT.index] == hero) {
            return PartySlot.LEFT;
        } else if (heroParty[PartySlot.MIDDLE.index] == hero) {
            return PartySlot.MIDDLE;
        } else {
            return PartySlot.RIGHT;
        }
    }

    public Hero getHeroSelected() {
        return heroParty[selectedHeroId.index];
    }

    public void setHeroSelectedSlot(final PartySlot selectedIndex) {
        final PartySlot prevSelectedPartySlot = selectedHeroId;
        final Hero prevHeroSelected = heroParty[selectedIndex.index];

        selectedHeroId = selectedIndex;
        final Hero selectedHero = heroParty[selectedHeroId.index];

        delegateHeroSelectionChanged.call(new HeroSelectionChanged(selectedHero, selectedIndex, prevHeroSelected, prevSelectedPartySlot));
    }

    public void setHero(final Hero hero, final PartySlot slot) {
        heroParty[slot.index] = hero;
    }

    public void changeWorld(final World world) {
        if (this.world != null) {
            this.world.unloadWorld();
        }

        world.loadWorld();

        this.world = world;
    }
}
