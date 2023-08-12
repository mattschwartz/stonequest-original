package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.game.abilitysystem.Ability;

import java.util.concurrent.Semaphore;

/**
 * A text widget that displays an error message across the screen to the player.
 */
public class ErrorAlertTextWidget extends Widget {

    private final long durationMillis = 3;
    private long remainingCooldownMillis =0;
    private boolean onCooldown = false;

    private final Semaphore showOnCooldown = new Semaphore(1);

    private final TextFieldWidget textFieldWidget;

    public ErrorAlertTextWidget() {
        super(LayoutData.builder()
            .anchor(new VDim(0, 0.0, 15, 15))
            .size(new VDim(0, 0, 200, 25))
            .build());

        textFieldWidget = new TextFieldWidget(
            "{COLOR=YELLOW}Something",
            FontContext.TextAlign.LEFT,
            FontContext.VerticalTextAlignment.CENTER);
        textFieldWidget.setFontSize(24);
        addWidget(textFieldWidget);
        textFieldWidget.setEnabled(false);

        Ability.delegateOnAbilityFailed.bindDelegate(e -> {
            show(e.ability.getName() + " failed: " + e.message);
            return null;
        });
    }

    private void show(final String message) {
        onCooldown = true;
        remainingCooldownMillis = durationMillis;

        textFieldWidget.setEnabled(true);
        textFieldWidget.setText(message);
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        super.onRender(eventArgs, renderContext);

        if (remainingCooldownMillis > UMath.EPSILON) {
            remainingCooldownMillis -= eventArgs.getDeltaTime() * 1000;
        }

        if (onCooldown) {
            try {
                showOnCooldown.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            eventArgs.submitJob(t -> {
                t.yield((long)(durationMillis * 1000), u -> {
                    textFieldWidget.setEnabled(false);
                    return null;
                });
                return null;
            });

            showOnCooldown.release();
            onCooldown = false;
        }
    }
}
