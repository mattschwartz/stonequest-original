package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.components.AdjustableValueComponent;
import com.barelyconscious.game.entity.gui.widgets.ButtonWidget;
import com.barelyconscious.game.entity.gui.widgets.SpriteWidget;
import com.barelyconscious.game.entity.gui.widgets.TextFieldWidget;
import com.barelyconscious.game.entity.gui.widgets.TooltipWidget;
import com.barelyconscious.game.entity.input.InputLayer;
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
            .anchor(new VDim(0, 0, 11, 20))
            .size(new VDim(0, 0, 156, 0))
            .build(),
            nameText));
        setupStatsWidgets(backdrop);

        final String heroClassText = "{SIZE=12}{COLOR=200,200,200,255}" + hero.getHeroClassType().getHeroClassName();
        addWidget(new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 11, 30))
            .size(new VDim(0, 0, 156, 0))
            .build(),
            heroClassText));

        createHeroDescriptionTooltipWidget(backdrop);

        addWidget(new ButtonWidget(
            LayoutData.builder()
                .anchor(new VDim(1, 0,
                    -GUISpriteSheet.Resources.BUTTON_DEFAULT.getRegion().getWidth(),
                    -GUISpriteSheet.Resources.BUTTON_DEFAULT.getRegion().getHeight() - 4))
                .size(GUISpriteSheet.Resources.BUTTON_DEFAULT)
                .build(),
            "Details",
            () -> {
                System.out.println("Button clicked");
                return null;
            }
        ));
    }

    private void setupStatsWidgets(SpriteWidget backdrop) {
        createAndBindStatWidget(backdrop, Stats.StatName.STRENGTH, 1);
        createAndBindStatWidget(backdrop, Stats.StatName.DEXTERITY, 33);
        createAndBindStatWidget(backdrop, Stats.StatName.CONSTITUTION, 66);
        createAndBindStatWidget(backdrop, Stats.StatName.INTELLIGENCE, 101);
        createAndBindStatWidget(backdrop, Stats.StatName.WISDOM, 134);
        createAndBindStatWidget(backdrop, Stats.StatName.CHARISMA, 167);
    }

    private void createAndBindStatWidget(
        final SpriteWidget backdrop,
        final Stats.StatName statName,
        final int yOffs
    ) {
        final AdjustableValueComponent avc = hero.getEntityStatsComponent().getStat(statName);
        final String formatString = "{COLOR=255,255,255,255}{SIZE=20}{STYLE=CENTER}";

        MouseInputWidget miw = new MouseInputWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 179, yOffs))
            .size(new VDim(0, 0, 31, 31))
            .build(),
            InputLayer.USER_INPUT);
        backdrop.addWidget(miw);

        final TextFieldWidget statWidget = new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0.5f, 0, 0))
            .build(),
            formatString + Math.round(avc.getCurrentValue()));
        avc.delegateOnValueChanged.bindDelegate(e -> {
            statWidget.setText(formatString + Math.round(e.currentValue));
            return null;
        });
        miw.addWidget(statWidget);

        TooltipWidget ttw = new TooltipWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 4, -4))
            .build(),
            statName.name,
            statName.description,
            null, null);
        statWidget.addWidget(ttw);

        miw.delegateOnMouseOver.bindDelegate(e -> {
            ttw.setEnabled(e);
            return null;
        });
        ttw.setEnabled(false);
    }

    private void createHeroDescriptionTooltipWidget(SpriteWidget backdrop) {
        MouseInputWidget miw = new MouseInputWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 12, 11))
            .size(new VDim(0, 0, 156, 32))
            .build(),
            InputLayer.USER_INPUT);
        backdrop.addWidget(miw);

        TooltipWidget ttw = new TooltipWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 4, -4))
            .build(),
            hero.name,
            "Level " + hero.getEntityLevelComponent().getEntityLevel() + " " + hero.getHeroClassType().getHeroClassName(),
            null, null);
        miw.addWidget(ttw);

        miw.delegateOnMouseOver.bindDelegate(e -> {
            ttw.setEnabled(e);
            return null;
        });
        ttw.setEnabled(false);
    }
}
