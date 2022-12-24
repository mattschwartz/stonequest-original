package com.barelyconscious.worlds.game.playercontroller;

import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public class PlayerController {

    @Getter
    @Nullable
    protected Vector mouseScreenPos;
    @Getter
    @Nullable
    protected Vector mouseWorldPos;

    @Getter
    @Nullable
    protected Vector mouseClickedScreenPos;
    @Getter
    @Nullable
    protected Vector mouseClickedWorldPos;

    @Getter
    @NonNull
    private final Inventory inventory;

    public PlayerController() {
        this(new Inventory(0));
    }

    public PlayerController(
        @NonNull final Inventory inventory
    ) {
        this.inventory = inventory;
    }
}