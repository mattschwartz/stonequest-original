package com.barelyconscious.game.shape;

import lombok.ToString;

@ToString
public final class Box implements Shape {

    public final int left, right;
    public final int top, bottom;

    public final int width;
    public final int height;

    public Box(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.width = right - left;
        this.height = bottom - top;
    }

    public Box plus(final Vector v) {
        return new Box(
            (int) v.x + left,
            (int) v.x + right,
            (int) v.y + top,
            (int) v.y + bottom);
    }

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
        return (x >= left && x < right)
            && (y >= top && y < bottom);
    }

    public boolean contains(final Vector point) {
        return this.contains((int) point.x, (int) point.y);
    }
}
