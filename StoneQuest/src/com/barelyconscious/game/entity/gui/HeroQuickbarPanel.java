package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.PowerComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.gui.widgets.*;
import com.barelyconscious.game.entity.resources.*;
import com.barelyconscious.game.shape.Vector;

import java.awt.*;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class HeroQuickbarPanel extends Widget {

    private final SpriteWidget spriteWidget;
    private final SpriteWidget selectedSpriteWidget;

    private final WSprite spriteHeroPortrait;
    private final Hero hero;

    public HeroQuickbarPanel(
        final LayoutData layout,
        final Hero hero,
        final WSprite spriteHeroPortrait
    ) {
        super(layout);
        checkArgument(hero != null, "hero is null");
        checkArgument(spriteHeroPortrait != null, "spriteHeroPortrait is null");

        this.hero = hero;
        this.spriteHeroPortrait = spriteHeroPortrait;

        this.spriteWidget = new SpriteWidget(Resources.instance().getSprite(
            GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP));
        WSprite selectedSprite = Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_SELECTED);
        this.selectedSpriteWidget = new SpriteWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 0, -11))
            .size(new VDim(0, 0, selectedSprite.getWidth(), selectedSprite.getHeight()))
            .build(),
            selectedSprite);

        this.spriteWidget.addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 62 + (134 / 2), 16))
            .size(new VDim(0, 0, 134, 18))
            .build(),
            hero.name));
        this.spriteWidget.addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(1, 0, -18, 16))
            .size(new VDim(0, 0, 22, 14))
            .build(),
            Integer.toString(hero.getEntityLevel())));

        addWidget(spriteWidget);
        selectedSpriteWidget.setEnabled(false);
        addWidget(selectedSpriteWidget);

        this.spriteWidget.addWidget(new ProgressBarWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 62, 25))
            .size(new VDim(0, 0, 167, 15))
            .build(),
            Objects.requireNonNull(hero.getComponent(HealthComponent.class)),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_START),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_MIDDLE),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_PARTIAL_CAP),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_FULL_CAP)));
        addWidget(new ProgressBarWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 62, 43))
            .size(new VDim(0, 0, 167, 11))
            .build(),
            Objects.requireNonNull(hero.getComponent(PowerComponent.class)),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_START),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_MIDDLE),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_PARTIAL_CAP),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_FULL_CAP)));

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
                    .anchor(new VDim(0, 0, 10, -8))
                    .size(LayoutData.SIZE_FILL)
                    .build(),
                    Vector.UP,
                    .75f,
                    textColor);
                wFtext.resize(spriteWidget.screenBounds);
                wFtext.beginFloating(dmgText);
                addWidget(wFtext);

                return null;
            });
        addWidget(new SpriteWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 12, 12))
            .size(new VDim(0, 0, 32, 32))
            .build(),
            spriteHeroPortrait));

        GameInstance.getInstance().delegateHeroSelectionChanged.bindDelegate(e -> {
            this.selectedSpriteWidget.setEnabled(e.selectedHero == hero);
            return null;
        });
    }
}
