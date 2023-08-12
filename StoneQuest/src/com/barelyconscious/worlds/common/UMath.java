package com.barelyconscious.worlds.common;

import com.barelyconscious.worlds.common.shape.Vector;

import java.util.Random;

public final class UMath {

    public static final double EPSILON = 0.0001;
    public static final Random RANDOM = new Random(1L);

    public static Vector min(final Vector lhs, final Vector rhs) {
        if (rhs.magnitude() < lhs.magnitude()) {
            return rhs;
        }

        return lhs;
    }

    public static int clamp(final int value, final int min, final int max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    public static double clamp(final double value, final double min, final double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    private UMath() {
    }
}
