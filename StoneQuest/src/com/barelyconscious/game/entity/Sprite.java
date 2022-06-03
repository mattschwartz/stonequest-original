package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.resources.WSprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Sprite extends WSprite {

    public Sprite(final Image image, final int width, final int height) {
        super((BufferedImage) image, RenderLayer.GROUND, width, height);
    }
}
