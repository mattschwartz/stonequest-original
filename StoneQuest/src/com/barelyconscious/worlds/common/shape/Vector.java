package com.barelyconscious.worlds.common.shape;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
public final class Vector {

    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector UP = new Vector(0, -1);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector RIGHT = new Vector(1, 0);

    public final double x;
    public final double y;

    public Vector() {
        this(0, 0);
    }

    public Vector(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Vector unitVector() {
        final double magnitude = magnitude();
        if (magnitude == 0) {
            return new Vector(0, 0);
        }

        return new Vector(
            x / magnitude,
            y / magnitude);
    }

    public double magnitude() {
        double sqSum = (x * x) + (y * y);
        if (sqSum == 0) {
            return 0;
        }

        return (double) Math.sqrt(sqSum);
    }

    public Vector multiply(final double amt) {
        return new Vector(
            x * amt,
            y * amt);
    }

    public Vector plus(final double x, final double y) {
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

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}
