package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.entity.graphics.RenderLayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public class WSprite {

    private final Image texture;
    private final RenderLayer renderLayer;
    private final int width;
    private final int height;
}
