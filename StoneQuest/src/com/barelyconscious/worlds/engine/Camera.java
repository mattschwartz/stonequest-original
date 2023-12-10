package com.barelyconscious.worlds.engine;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

public class Camera {

    @Getter
    @Setter
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

    public Vector worldToScreenPos(final double worldX, final double worldY) {
        return new Vector(
            worldX - transform.x,
            worldY - transform.y);
    }

    @Nullable
    public Vector worldToScreenPos(final Vector worldPos) {
        if (worldPos == null) {
            return null;
        }
        return worldToScreenPos(worldPos.x, worldPos.y);
    }

    @Nullable
    public Vector screenToWorldPos(final Vector screenPos) {
        if (screenPos == null) {
            return null;
        }
        return transform.plus(screenPos);
    }
}
