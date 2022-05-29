package com.barelyconscious.game.entity;

import java.awt.*;

public final class Sprite {

    public final Image image;
    public final int width;
    public final int height;

    public Sprite(final Image image, final int width, final int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }
}
