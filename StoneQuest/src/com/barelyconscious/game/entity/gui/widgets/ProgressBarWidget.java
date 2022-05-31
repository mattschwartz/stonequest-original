package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.components.HealthComponent;
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

    private final HealthComponent health;
    private final Color backgroundColor;
    private final Color foregroundColor;

    public ProgressBarWidget(
        final Anchor anchor,
        final HealthComponent health,
        final Color backgroundColor,
        final Color foregroundColor
    ) {
        super(anchor);
        this.health = health;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;

        this.progress = health.getCurrentHealth() / health.getMaxHealth();
        health.delegateOnHealthChanged.bindDelegate((event) -> {
            setProgress(event.currentHealth / event.maxHealth);
            return null;
        });
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        renderContext.renderGuiRect(backgroundColor, true,
            screenBounds.left, screenBounds.top,
            screenBounds.width, screenBounds.height);

        if (progress < 1) {
            renderContext.renderGuiRect(foregroundColor.darker(), true,
                screenBounds.left + 2, screenBounds.top + 2,
                screenBounds.width - 4, screenBounds.height - 4);
        }

        if (progress >= 0) {
            renderContext.renderGuiRect(foregroundColor, true,
                screenBounds.left + 2, screenBounds.top + 2,
                (int) (screenBounds.width * progress) - 4, screenBounds.height - 4);
        }
    }
}
