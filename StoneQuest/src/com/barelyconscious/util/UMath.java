package com.barelyconscious.util;

import com.barelyconscious.game.shape.Vector;

import java.util.Random;

public final class UMath {

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

    public static float clampf(final float value, final float min, final float max) {
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
