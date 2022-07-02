package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.components.AdjustableValueComponent;
import com.barelyconscious.game.entity.gui.widgets.SpriteWidget;
import com.barelyconscious.game.entity.gui.widgets.TextFieldWidget;
import com.barelyconscious.game.entity.resources.GUISpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;

public class HeroStatsSheetWidget extends MouseInputWidget {

    private final Hero hero;

    public HeroStatsSheetWidget(final Hero hero) {
        super(LayoutData.builder()
            .anchor(new VDim(0, 0, 0, -(15 + GUISpriteSheet.Resources.HERO_STAT_SHEET_BACKDROP.getRegion().getHeight())))
            .size(new VDim(0, 0, GUISpriteSheet.Resources.HERO_STAT_SHEET_BACKDROP.getRegion().getWidth(),
                GUISpriteSheet.Resources.HERO_STAT_SHEET_BACKDROP.getRegion().getHeight()))
            .build());
        this.hero = hero;

        SpriteWidget backdrop = new SpriteWidget(LayoutData.DEFAULT,
            Resources.instance().getSprite(GUISpriteSheet.Resources.HERO_STAT_SHEET_BACKDROP));
        addWidget(backdrop);

        final String nameText = "{SIZE=22}" + hero.name;
        backdrop.addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 11, 28))
            .size(new VDim(0, 0, 156, 0))
            .build(),
            nameText));
        setupStatsWidgets(backdrop);

        final String heroClassText = "{SIZE=12}{COLOR=200,200,200,255}" + hero.getHeroClassType().getHeroClassName();
        addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 11, 38))
            .size(new VDim(0, 0, 156, 0))
            .build(),
            heroClassText));
    }

    private void setupStatsWidgets(SpriteWidget backdrop) {
        createAndBindStatWidget(backdrop, Stats.StatName.STRENGTH, 1);
        createAndBindStatWidget(backdrop, Stats.StatName.DEXTERITY, 34);
        createAndBindStatWidget(backdrop, Stats.StatName.CONSTITUTION, 67);
        createAndBindStatWidget(backdrop, Stats.StatName.INTELLIGENCE, 102);
        createAndBindStatWidget(backdrop, Stats.StatName.WISDOM, 136);
        createAndBindStatWidget(backdrop, Stats.StatName.CHARISMA, 169);
    }

    private void createAndBindStatWidget(
        final SpriteWidget backdrop,
        final Stats.StatName statName,
        final int yOffs
    ) {
        final AdjustableValueComponent avc = hero.getEntityStatsComponent().getStat(statName);
        final String formatString = "{COLOR=255,255,255,255}{SIZE=20}{STYLE=CENTER}";

        final TextFieldWidget statWidget = new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 178, yOffs + 21))
            .size(new VDim(0, 0, 30, 30))
            .build(), formatString + Math.round(avc.getCurrentValue()));
        avc.delegateOnValueChanged.bindDelegate(e -> {
            statWidget.setText(formatString + Math.round(e.currentValue));
            return null;
        });

        backdrop.addWidget(statWidget);
    }
}
