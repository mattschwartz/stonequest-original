package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.shape.Vector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class VDim {

    public Vector relative;
    public Vector absolute;

    public VDim(
        final float relativeX, final float relativeY,
        final int absoluteX, final int absoluteY
    ) {
        this.relative = new Vector(relativeX, relativeY);
        this.absolute = new Vector(absoluteX, absoluteY);
    }
}
