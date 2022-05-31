package com.barelyconscious.game.entity;

import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;

public final class Camera {

    public Vector transform;

    private int viewWidth;
    private int viewHeight;

    public Camera(final int viewWidth, final int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.transform = new Vector(0, 0);
    }

    public void resize(final int newWidth, final int newHeight) {
        viewWidth = newWidth;
        viewHeight = newHeight;
    }

    public Box getWorldBounds() {
        return new Box(
            (int) transform.x,
            (int) transform.x + viewWidth,
            (int) transform.y,
            (int) transform.y + viewHeight
        );
    }

    public int getWorldX() {
        return (int) transform.x;
    }

    public int getWorldY() {
        return (int) transform.y;
    }

    public int getScreenWidth() {
        return viewWidth;
    }

    public int getScreenHeight() {
        return viewHeight;
    }

    public Vector worldToScreenPos(final float worldX, final float worldY) {
        return new Vector(
            worldX - transform.x,
            worldY - transform.y);
    }

    public Vector worldToScreenPos(final Vector worldPos) {
        return worldToScreenPos(worldPos.x, worldPos.y);
    }

    public Vector screenToWorldPos(final Vector screenPos) {
        return transform.plus(screenPos);
    }
}
