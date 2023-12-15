package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.engine.gui.GuiCanvas;
import com.barelyconscious.worlds.engine.gui.WorldLogPanel;
import com.barelyconscious.worlds.engine.gui.widgets.TooltipWidget;
import lombok.Getter;

public class GuiSystem implements GameSystem {

    private final GuiCanvas canvas;
    @Getter
    private final TooltipWidget tooltip;
    @Getter
    private final WorldLogPanel worldLogPanel;

    public GuiSystem(GuiCanvas canvas, TooltipWidget tooltip, WorldLogPanel worldLogPanel) {
        this.canvas = canvas;
        this.tooltip = tooltip;
        this.worldLogPanel = worldLogPanel;

        tooltip.setVisible(false);
        canvas.addWidget(tooltip);
        canvas.addWidget(worldLogPanel);
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
