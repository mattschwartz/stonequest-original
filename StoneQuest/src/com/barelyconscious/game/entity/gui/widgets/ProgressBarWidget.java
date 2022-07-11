package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.components.AdjustableValueComponent;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.resources.WSprite;
import com.barelyconscious.game.shape.Box;
import lombok.Setter;

public class ProgressBarWidget extends Widget {

    @Setter
    private float progress;

    private final WSprite progressStart;
    private final WSprite progressMid;
    private final WSprite progressPartialCap;
    private final WSprite progressFullCap;

    public ProgressBarWidget(
        final LayoutData layout,
        final WSprite start,
        final WSprite mid,
        final WSprite partialCap,
        final float progress
    ) {
        super(layout);
        this.progressStart = start;
        this.progressMid = mid;
        this.progressPartialCap = partialCap;
        this.progressFullCap = null;

        this.progress =  progress;
    }

    public ProgressBarWidget(
        final LayoutData layout,
        final AdjustableValueComponent stat,
        final WSprite progressStart,
        final WSprite progressMid,
        final WSprite progressPartialCap,
        final WSprite progressFullCap
    ) {
        super(layout);
        this.progressStart = progressStart;
        this.progressMid = progressMid;
        this.progressPartialCap = progressPartialCap;
        this.progressFullCap = progressFullCap;

        this.progress = stat.getCurrentValue() / stat.getMaxValue();
        stat.delegateOnValueChanged.bindDelegate((event) -> {
            setProgress(event.currentValue / event.maxValue);
            return null;
        });
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (progress <= 0) {
            return;
        }

        int xOffs = renderStart(renderContext);
        xOffs += renderMid(xOffs, renderContext);
        if (progress < 1) {
            renderPartialCap(xOffs, renderContext);
        } else if (progressFullCap != null) {
            renderFullCap(renderContext);
        }
    }

    private void renderFullCap(RenderContext renderContext) {
        final Box bounds = new Box(
            screenBounds.left + screenBounds.width - progressFullCap.getWidth(),
            screenBounds.right,
            screenBounds.top,
            screenBounds.top + progressFullCap.getHeight());
        renderContext.renderGui(progressFullCap.getTexture(), bounds);
    }

    private void renderPartialCap(int xOffs, RenderContext renderContext) {
        final Box bounds = new Box(
            screenBounds.left + xOffs,
            screenBounds.left + xOffs + progressPartialCap.getWidth(),
            screenBounds.top,
            screenBounds.top + progressPartialCap.getHeight());

        renderContext.renderGui(progressPartialCap.getTexture(), bounds);
    }

    private int renderStart(final RenderContext renderContext) {
        final Box bounds = new Box(
            screenBounds.left,
            screenBounds.left + progressStart.getWidth(),
            screenBounds.top,
            screenBounds.top + progressStart.getHeight());

        renderContext.renderGui(progressStart.getTexture(), bounds);

        return bounds.width;
    }

    private int renderMid(final int xOffs, final RenderContext renderContext) {
        final int maxWidth;
        if (progressFullCap != null) {
            maxWidth = screenBounds.width - progressFullCap.getWidth();
        } else {
            maxWidth = screenBounds.width;

        }
        final Box bounds = new Box(
            screenBounds.left + xOffs,
            screenBounds.left + (int) (maxWidth * progress),
            screenBounds.top,
            screenBounds.top + progressStart.getHeight());

        renderContext.renderGui(progressMid.getTexture(), bounds);

        return bounds.width;
    }
}
