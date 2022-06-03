package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.entity.graphics.RenderLayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

@Getter
@AllArgsConstructor
public class WSprite {

    private final BufferedImage texture;
    private final RenderLayer renderLayer;
    private final int width;
    private final int height;
}
