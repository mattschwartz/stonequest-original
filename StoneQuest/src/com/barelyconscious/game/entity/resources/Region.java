package com.barelyconscious.game.entity.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Region {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
}
