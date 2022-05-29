package com.barelyconscious.game.shape;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Vector {

    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector UP = new Vector(0, -1);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector RIGHT = new Vector(1, 0);

    public final float x;
    public final float y;

    public Vector() {
        this(0, 0);
    }

    public Vector(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Vector unitVector() {
        float sqSum = (x * x) + (y * y);
        if (sqSum == 0) {
            return new Vector(0, 0);
        }

        float magnitude = (float) Math.sqrt(sqSum);

        return new Vector(
            x / magnitude,
            y / magnitude);
    }

    public Vector mult(final float amt) {
        return new Vector(
            x * amt,
            y * amt);
    }

    public Vector plus(final float x, final float y) {
        return new Vector(
            this.x + x,
            this.y + y);
    }

    public Vector plus(final Vector rhs) {
        return plus(rhs.x, rhs.y);
    }

    public Vector minus(final Vector rhs) {
        return plus(-rhs.x, -rhs.y);
    }

}
