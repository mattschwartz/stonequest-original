package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;

import java.util.List;

// todo(p0) implement
public class GridLayoutWidget extends Widget {

    private Widget[] widgetCells;
    private int rows;
    private int columns;
    private Vector cellSize;

    public GridLayoutWidget(LayoutData layout, final int rows, final int columns) {
        super(layout);

        this.rows = rows;
        this.columns = columns;
        this.cellSize = Vector.ZERO;
        this.widgetCells = new Widget[rows * columns];
    }

    public void setData(final List<Widget> widgetCells) {
        this.widgetCells = widgetCells.toArray(Widget[]::new);
    }

    public void setCell(final int row, final int column, final Widget widget) {
        final int cellIndex = row + column * rows;
        widgetCells[cellIndex] = widget;
    }

    @Override
    public Widget addWidget(Widget widget) {
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                final int cellIndex = row + column * rows;
                if (cellIndex < widgetCells.length) {
                    if (widgetCells[cellIndex] == null) {
                        setCell(row, column, widget);
                        return widget;
                    }
                }
            }
        }
        return widget;
    }

    @Override
    public void resize(Box bounds) {
        super.resize(bounds);
        this.cellSize = new Vector(
            bounds.width / (float) columns,
            bounds.height / (float) rows);

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < columns; ++c) {
                final int cellIndex = r + c * rows;
                if (cellIndex >= widgetCells.length) {
                    continue;
                }

                final Widget w = widgetCells[cellIndex];
                if (w == null) {
                    continue;
                }

                w.resize(screenBounds);

                int xOffs = (int) ((float) c * (cellSize.x - 3));
                int yOffs = (int) ((float) r * cellSize.y);

                w.resize(new Box(
                    screenBounds.left + xOffs,
                    screenBounds.left + xOffs + (int) cellSize.x,
                    screenBounds.top + yOffs,
                    screenBounds.bottom + yOffs + (int) cellSize.y
                ));

            }
        }
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        super.onRender(eventArgs, renderContext);

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < columns; ++c) {
                final int cellIndex = r + c * rows;
                if (cellIndex < widgetCells.length) {
                    final Widget w = widgetCells[cellIndex];
                    if (w != null) {
                        w.render(eventArgs, renderContext);
                    }
                }
            }
        }
    }
}
