package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.tags.EncryptableTag;

public class ExtractorSystem implements GameSystem {

    public static final class ExtractorState {

        private int decryptionLevel;
    }

    public Item extractData(Item item) {
        if (!item.getTags().contains(EncryptableTag.ENCRYPTED)) {
            GameInstance.log("Cannot extract data from " + item.getName());
            return null;
        }

        return null;
    }
}
