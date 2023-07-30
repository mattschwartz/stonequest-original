package com.barelyconscious.worlds.testgamedata;

import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.game.GameInstance;

public class TestTechInitializer {

    public static void init() {
        GameInstance.instance().setPlayerPersonalDevice(
            new PlayerPersonalDevice()
        );
    }
}
