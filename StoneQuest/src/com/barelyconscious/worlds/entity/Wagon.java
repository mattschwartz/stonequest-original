package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.Inventory;
import lombok.Getter;
import lombok.Setter;

public class Wagon extends Actor {

    @Getter
    @Setter
    private Inventory resourcePouch;
    @Getter
    @Setter
    private Inventory storage;

    public Wagon(
        Inventory resourcePouch,
        Inventory storage
    ) {
        super("Party Wagon", Vector.ZERO);
        this.resourcePouch = resourcePouch;
        this.storage = storage;
    }
}
