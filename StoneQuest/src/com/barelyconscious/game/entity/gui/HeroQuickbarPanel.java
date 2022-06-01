package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.PowerComponent;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.widgets.*;
import com.barelyconscious.game.entity.resources.ResourceGUI;
import com.barelyconscious.game.shape.Vector;

import java.awt.*;
import java.util.Objects;

public class HeroQuickbarPanel extends Widget {

    private final SpriteWidget spriteWidget;
    private final SpriteWidget selectedSpriteWidget;

    private final Hero hero;

    public HeroQuickbarPanel(final LayoutData layout, final Hero hero) {
        super(layout);
        this.hero = hero;
        this.spriteWidget = new SpriteWidget(LayoutData.builder()
            .size(new VDim(0, 0, (int) (ResourceGUI.HERO_UNIT_FRAME.width * .75),
                (int) (ResourceGUI.HERO_UNIT_FRAME.height * .75)))
            .build(),
            ResourceGUI.HERO_UNIT_FRAME);
        this.selectedSpriteWidget = new SpriteWidget(LayoutData.builder()
            .size(new VDim(0, 0, (int) (ResourceGUI.HERO_UNIT_FRAME_SELECTED.width * .75),
                (int) (ResourceGUI.HERO_UNIT_FRAME_SELECTED.height * .75)))
            .build(),
            ResourceGUI.HERO_UNIT_FRAME_SELECTED);

        addWidget(new BackgroundPanelWidget(LayoutData.DEFAULT,
            new Color(55, 55, 55)));

        addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 1, 6, -10))
            .size(new VDim(0, 0, 64, 14))
            .build(),
            hero.name));

        addWidget(new SpriteWidget(LayoutData.builder()
            .size(new VDim(0, 0, (int) (ResourceGUI.HERO_PORTRAIT_LEFT.width * .75),
                (int) (ResourceGUI.HERO_PORTRAIT_LEFT.height * .75)))
            .build(),
            ResourceGUI.HERO_PORTRAIT_LEFT));

        addWidget(spriteWidget);
        selectedSpriteWidget.setEnabled(false);
        addWidget(selectedSpriteWidget);

        addWidget(new ProgressBarWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 9, 101))
            .size(new VDim(0, 0, 102, 12))
            .build(),
            hero.getComponent(HealthComponent.class),
            new Color(0, 0, 0, 0),
            new Color(106, 190, 48)));
        addWidget(new ProgressBarWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 9, 122))
            .size(new VDim(0, 0, 102, 7))
            .build(),
            hero.getComponent(PowerComponent.class),
            new Color(0, 0, 0, 0),
            new Color(91, 110, 225)));

        hero.getComponent(HealthComponent.class)
            .delegateOnValueChanged.bindDelegate(e -> {
                final Color textColor;
                final String dmgText;
                if (e.delta > 0) {
                    textColor = Color.GREEN;
                    dmgText = String.format("+%.1f", e.delta);
                } else if (e.delta < 0) {
                    textColor = Color.RED;
                    dmgText = String.format("%.1f", e.delta);
                } else {
                    return null;
                }

                final FloatingTextWidget wFtext = new FloatingTextWidget(LayoutData.builder()
                    .anchor(new VDim(0.4f, 0.67f, 0, 0))
                    .size(LayoutData.SIZE_FILL)
                    .build(),
                    Vector.UP,
                    0.2f,
                    textColor);
                spriteWidget.addWidget(wFtext);
                wFtext.resize(spriteWidget.screenBounds);

                wFtext.beginFloating(dmgText);
                return null;
            });
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        final boolean isHeroSelected = Objects.equals(
            GameInstance.getInstance().getHeroSelected().id,
            hero.id);

        selectedSpriteWidget.setEnabled(isHeroSelected);
    }
}
