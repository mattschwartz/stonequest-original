package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.StatValueComponent;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.Widget;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class ProgressBarWidget extends Widget {

    @Getter
    @Setter
    private float progress;

    private final StatValueComponent stat;
    private final Color backgroundColor;
    private final Color foregroundColor;

    public ProgressBarWidget(
        final Anchor anchor,
        final StatValueComponent stat,
        final Color backgroundColor,
        final Color foregroundColor
    ) {
        super(anchor);
        this.stat = stat;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;

        this.progress = stat.getCurrentValue() / stat.getMaxValue();
        stat.delegateOnValueChanged.bindDelegate((event) -> {
            setProgress(event.currentValue / event.maxValue);
            return null;
        });
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        renderContext.renderGuiRect(backgroundColor, true,
            screenBounds.left, screenBounds.top,
            screenBounds.width, screenBounds.height);

        renderContext.renderGuiRect(foregroundColor, true,
            screenBounds.left, screenBounds.top,
            (int) (screenBounds.width * progress), screenBounds.height);
    }
}
