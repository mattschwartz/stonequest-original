package com.barelyconscious.game.shape;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public final class Box implements Shape {

    public int left, right;
    public int top, bottom;

    public boolean intersects(final Shape other) {
        if (other instanceof Box) {
            final Box otherBox = (Box) other;
            return left < otherBox.right && right > otherBox.left
                && top < otherBox.bottom && bottom > otherBox.top;
        } else {
            return false;
        }
    }

    public boolean contains(final int x, final int y) {
        return (x >= left && x <= right)
            && (y >= top && y <= bottom);
    }

    public boolean contains(final Vector point) {
        return this.contains((int) point.x, (int) point.y);
    }
}
