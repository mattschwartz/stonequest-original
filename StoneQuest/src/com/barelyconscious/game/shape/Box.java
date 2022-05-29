package com.barelyconscious.game.shape;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class Box implements Shape {

    public int left, right;
    public int top, bottom;

    public boolean intersects(final Shape other) {
        if (other instanceof Box) {
            final Box otherBox = (Box) other;
            return left < otherBox.right && right > otherBox.left
                && top > otherBox.bottom && bottom < otherBox.top;
        } else {
            return false;
        }
    }
}
