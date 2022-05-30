package com.barelyconscious.game.entity;

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

    public int getViewX() {
        return (int) transform.x;
    }

    public int getViewY() {
        return (int) transform.y;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }
}
