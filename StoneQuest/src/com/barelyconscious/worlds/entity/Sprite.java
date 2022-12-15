package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.entity.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.resources.WSprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Sprite extends WSprite {

    public Sprite(final Image image, final int width, final int height) {
        super((BufferedImage) image, RenderLayer.GROUND, width, height);
    }
}
