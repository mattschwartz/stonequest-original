package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.engine.gui.collector.CollectorDialogPanel;
import com.barelyconscious.worlds.engine.gui.GuiCanvas;
import com.barelyconscious.worlds.engine.gui.WorldLogPanel;
import com.barelyconscious.worlds.engine.gui.extractor.ExtractorDialogPanel;
import com.barelyconscious.worlds.engine.gui.widgets.TooltipWidget;
import lombok.Getter;

public class GuiSystem implements GameSystem {

    private final GuiCanvas canvas;
    @Getter
    private final TooltipWidget tooltip;
    @Getter
    private final WorldLogPanel worldLogPanel;
    @Getter
    private final CollectorDialogPanel collectorDialogPanel;
    @Getter
    private final ExtractorDialogPanel extractorDialogPanel;

    public GuiSystem(
        GuiCanvas canvas,
        TooltipWidget tooltip,
        WorldLogPanel worldLogPanel,
        CollectorDialogPanel collectorDialogPanel,
        ExtractorDialogPanel extractorDialogPanel
    ) {
        this.canvas = canvas;
        this.tooltip = tooltip;
        this.worldLogPanel = worldLogPanel;
        this.collectorDialogPanel = collectorDialogPanel;
        this.extractorDialogPanel = extractorDialogPanel;

        tooltip.setVisible(false);
        collectorDialogPanel.setEnabled(false);
        extractorDialogPanel.setEnabled(false);

        canvas.addWidget(tooltip);
        canvas.addWidget(worldLogPanel);
        canvas.addWidget(collectorDialogPanel);
        canvas.addWidget(extractorDialogPanel);
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
