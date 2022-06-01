package com.barelyconscious.game.entity.input;

import com.google.common.collect.Lists;

import java.util.List;

public enum InputLayer {
    GAME_WORLD(0),
    GUI(1);

    public final int zLevel;

    public static List<InputLayer> sorted() {
        return Lists.newArrayList(GUI, GAME_WORLD);
    }

    InputLayer(final int zLevel) {
        this.zLevel = zLevel;
    }
}