package com.barelyconscious.worlds.common.shape;

import lombok.ToString;

@ToString
public final class Box implements Shape {

    public final int left, right;
    public final int top, bottom;

    public final int width;
    public final int height;

    public static Box square(int size) {
        return new Box(0, size, 0, size);
    }

    public Box(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.width = right - left;
        this.height = bottom - top;
    }

    /**
     * @return a new box with values offset by the supplied position.
     */
    public Box boxAtPosition(final Vector position) {
        return new Box(
            (int) position.x + left,
            (int) position.x + right,
            (int) position.y + top,
            (int) position.y + bottom);
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
