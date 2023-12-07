package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.engine.gui.GuiCanvas;
import com.barelyconscious.worlds.engine.gui.widgets.TooltipWidget;

public class GuiSystem implements GameSystem {

    private final GuiCanvas canvas;
    private final TooltipWidget tooltip;

    public GuiSystem(GuiCanvas canvas, TooltipWidget tooltip) {
        this.canvas = canvas;
        this.tooltip = tooltip;
        this.tooltip.setVisible(false);

        canvas.addWidget(tooltip);
    }

    public void showTooltip(
        String title,
        String description,
        String actionText,
        String topRightText
    ) {
        tooltip.setTitle(title);
        tooltip.setDescription(description);
        tooltip.setActionText(actionText);
        tooltip.setTopRightText(topRightText);
        tooltip.setVisible(true);
    }

    public void hideTooltip() {
        tooltip.setVisible(false);
    }
}
