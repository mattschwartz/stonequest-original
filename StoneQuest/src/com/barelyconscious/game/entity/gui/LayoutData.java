package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class LayoutData {

    /**
     * The location within the widget that will be used to position
     * the widget within its parent.
     */
    final VDim anchor;
    /**
     * The dimensions of the scaled widget. x=width, y=height
     */
    final VDim size;

    public static VDim ANCHOR_TOP_LEFT = new VDim(0, 0, 0, 0);
    public static VDim ANCHOR_TOP_RIGHT = new VDim(1, 0, 0, 0);

    public static VDim ANCHOR_BOTTOM_LEFT = new VDim(0, 1, 0, 0);
    public static VDim ANCHOR_BOTTOM_RIGHT = new VDim(1, 1, 0, 0);

    public static VDim ANCHOR_TOP_CENTER = new VDim(0.5f, 0, 0, 0);
    public static VDim ANCHOR_BOTTOM_CENTER = new VDim(0.5f, 1, 0, 0);
    public static VDim ANCHOR_LEFT_CENTER = new VDim(0, 0.5f, 0, 0);
    public static VDim ANCHOR_RIGHT_CENTER = new VDim(1, 0.5f, 0, 0);

    public static VDim ANCHOR_CENTER = new VDim(0.5f, 0.5f, 0, 0);

    public static VDim SIZE_FILL = new VDim(1, 1, 0, 0);

    public static Vector PIVOT_TOP_LEFT = Vector.ZERO;
    public static Vector PIVOT_CENTER = new Vector(0.5f, 0.5f);

    /**
     * The default layout:
     * 1. Anchored top left corner
     * 2. Pivot is top left corner
     * 3. Size is fill
     */
    public static LayoutData DEFAULT = new LayoutData(ANCHOR_TOP_LEFT,
        SIZE_FILL);

    public Box applyLayout(final Box parentLayout) {
        final Vector layoutSize = new Vector(
            (size.relative.x * parentLayout.width)
                + size.absolute.x,
            (size.relative.y * parentLayout.height)
                + size.absolute.y
        );
        final Vector position = new Vector(
            parentLayout.left
                + (anchor.relative.x * parentLayout.width)
                + anchor.absolute.x,
            parentLayout.top
                + (anchor.relative.y * parentLayout.height)
                + anchor.absolute.y
        );

        return new Box(
            (int) position.x,
            (int) (position.x + layoutSize.x),
            (int) position.y,
            (int) (position.y + layoutSize.y));
    }

    public static LayoutDataBuilder builder() {
        return new LayoutDataBuilder();
    }

    public static final class LayoutDataBuilder {

        VDim anchor;
        VDim size;

        public LayoutDataBuilder anchor(final VDim anchor) {
            this.anchor = anchor;
            return this;
        }

        public LayoutDataBuilder size(final VDim size) {
            this.size = size;
            return this;
        }

        public LayoutData build() {
            return new LayoutData(anchor, size);
        }

        private LayoutDataBuilder() {
            anchor = ANCHOR_TOP_LEFT;
            size = new VDim(1, 1, 0, 0);
        }
    }
}
