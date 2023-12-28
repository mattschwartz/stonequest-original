package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.tags.EncryptableTag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ExtractorSystem implements GameSystem {

    public final Delegate<ExtractorState> delegateOnStateChanged = new Delegate<>();

    @Getter
    @Builder
    public static final class ExtractorState {

        private final List<Item> codecs;
        private final List<Item> peripherals;
    }

    public void addCodec(Item item) {
        var state = GameInstance.instance().getGameState()
                .getExtractorState();

        if (state.getCodecs().size() >= 3) {
            GameInstance.log("Cannot add more than 3 codecs");
            return;
        }

        state.getCodecs().add(item);

        delegateOnStateChanged.call(state);
    }

    public void addPeripheral(Item item) {
        var state = GameInstance.instance().getGameState()
                .getExtractorState();

        if (state.getPeripherals().size() >= 3) {
            GameInstance.log("Cannot add more than 3 peripherals");
            return;
        }

        state.getPeripherals().add(item);

        delegateOnStateChanged.call(state);
    }

    public Item extractData(Item item) {
        if (!item.getTags().contains(EncryptableTag.ENCRYPTED)) {
            GameInstance.log("Cannot extract data from " + item.getName());
            return null;
        }

        return null;
    }
}
