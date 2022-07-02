package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.PowerComponent;
import com.barelyconscious.game.entity.gui.widgets.FloatingTextWidget;
import com.barelyconscious.game.entity.gui.widgets.ProgressBarWidget;
import com.barelyconscious.game.entity.gui.widgets.SpriteWidget;
import com.barelyconscious.game.entity.gui.widgets.TextFieldWidget;
import com.barelyconscious.game.entity.resources.GUISpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.resources.WSprite;
import com.barelyconscious.game.shape.Vector;
import lombok.extern.log4j.Log4j2;

import java.awt.Color;
import java.awt.event.MouseEvent;

import static com.google.common.base.Preconditions.checkArgument;

@Log4j2
public class HeroQuickbarPanel extends MouseInputWidget {

    private final SpriteWidget spriteWidget;
    private final SpriteWidget selectedSpriteWidget;
    private final OnHoverRenderWidget hoverControlWidget;

    private final WSprite spriteHeroPortrait;
    private final Hero hero;

    private final HeroStatsSheetWidget statsSheet;

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
        this.statsSheet = new HeroStatsSheetWidget(hero);

        this.spriteWidget = new SpriteWidget(Resources.instance().getSprite(
            GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP));
        WSprite selectedSprite = Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_SELECTED);
        this.selectedSpriteWidget = new SpriteWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 0, -11))
            .size(new VDim(0, 0, selectedSprite.getWidth(), selectedSprite.getHeight()))
            .build(),
            selectedSprite);

        this.spriteWidget.addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 62, 16))
            .size(new VDim(0, 0, 134, 18))
            .build(),
            hero.name));
        this.spriteWidget.addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(1, 0, -28, 16))
            .size(new VDim(0, 0, 20, 14))
            .build(),
            Integer.toString(hero.getEntityLevelComponent().getEntityLevel())));

        addWidget(spriteWidget);
        selectedSpriteWidget.setEnabled(false);
        addWidget(selectedSpriteWidget);

        this.spriteWidget.addWidget(new ProgressBarWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 62, 25))
            .size(new VDim(0, 0, 167, 15))
            .build(),
            hero.getHealthComponent(),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_START),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_MIDDLE),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_PARTIAL_CAP),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_HEALTHBAR_PROGRESS_FULL_CAP)));
        addWidget(new ProgressBarWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 62, 43))
            .size(new VDim(0, 0, 167, 11))
            .build(),
            hero.getPowerComponent(),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_START),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_MIDDLE),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_PARTIAL_CAP),
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_UNITFRAME_POWERBAR_PROGRESS_FULL_CAP)));

        hero.getHealthComponent()
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


        addWidget(hoverControlWidget = new OnHoverRenderWidget(hero.getHealthComponent(), hero.getPowerComponent()));
        addWidget(statsSheet);

        hoverControlWidget.setVisible(false);
        statsSheet.setEnabled(false);
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        hoverControlWidget.setVisible(true);
        return super.onMouseEntered(e);
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        hoverControlWidget.setVisible(false);
        return super.onMouseExited(e);
    }

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        if (isMouseOver()) {
            statsSheet.setEnabled(!statsSheet.isEnabled());
            System.out.println("Stats sheet=" + statsSheet.isEnabled());
            return true;
        } else {
            return false;
        }
    }

    private static class OnHoverRenderWidget extends Widget {

        private final TextFieldWidget healthWidget;
        private final TextFieldWidget powerWidget;
//        private final TextFieldWidget experienceWidget;

        public OnHoverRenderWidget(final HealthComponent healthComponent, final PowerComponent powerComponent) {
            super(LayoutData.DEFAULT);

            healthWidget = setupHealthWidget(healthComponent);
            powerWidget = setupPowerWidget(powerComponent);

            addWidget(healthWidget);
            addWidget(powerWidget);
        }

        private TextFieldWidget setupHealthWidget(final HealthComponent healthComponent) {
            final TextFieldWidget result = new TextFieldWidget(LayoutData.builder()
                .anchor(new VDim(0, 0, 65, 37))
                .size(new VDim(0, 0, 161, 13))
                .build(),
                formatAdjustableValue(healthComponent.getCurrentValue(), healthComponent.getMaxValue(), null));

            healthComponent.delegateOnValueChanged.bindDelegate(e -> {
                result.setText(formatAdjustableValue(e.currentValue, e.maxValue, null));
                return null;
            });

            return result;
        }

        private TextFieldWidget setupPowerWidget(final PowerComponent powerComponent) {
            final TextFieldWidget result = new TextFieldWidget(LayoutData.builder()
                .anchor(new VDim(0, 0, 65, 52))
                .size(new VDim(0, 0, 161, 8))
                .build(),
                formatAdjustableValue(powerComponent.getCurrentValue(), powerComponent.getMaxValue(), "{SIZE=13}"));

            powerComponent.delegateOnValueChanged.bindDelegate(e -> {
                result.setText(formatAdjustableValue(e.currentValue, e.maxValue, "{SIZE=13}"));
                return null;
            });

            return result;
        }

        private String formatAdjustableValue(float currentValue, float maxValue, final String format) {
            return String.format("%s%.0f / %.0f", format == null ? "" : format, currentValue, maxValue);
        }

        public void setVisible(final boolean isVisible) {
            healthWidget.setEnabled(isVisible);
            powerWidget.setEnabled(isVisible);
        }
    }
}
