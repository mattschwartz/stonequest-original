package com.barelyconscious.worlds.engine.input;

import com.google.common.collect.Lists;

import java.util.List;

public enum InputLayer {
    GAME_WORLD(0),
    GUI(1),
    USER_INPUT(2);

    public final int zLevel;

    public static List<InputLayer> sorted() {
        return Lists.newArrayList(USER_INPUT, GUI, GAME_WORLD);
    }

    InputLayer(final int zLevel) {
        this.zLevel = zLevel;
    }
}
