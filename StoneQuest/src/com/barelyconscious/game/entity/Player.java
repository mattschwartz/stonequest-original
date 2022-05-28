package com.barelyconscious.game.entity;

import java.util.*;

public class Player extends AEntity {

    private float currentExperience;

    public Player(final int level, final float currentExperience, final Map<Stats, Float> stats) {
        super(level, stats);
        this.currentExperience = currentExperience;
    }
}
