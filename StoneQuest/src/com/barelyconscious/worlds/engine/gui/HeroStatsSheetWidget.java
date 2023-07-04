package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.engine.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.worlds.engine.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.engine.gui.widgets.SpriteWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TooltipWidget;
import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.input.InputLayer;
import com.barelyconscious.worlds.game.resources.GUISpriteSheet;
import com.barelyconscious.worlds.game.resources.Resources;

import java.awt.Color;

public class HeroStatsSheetWidget extends Widget {

    private final Hero hero;
    private final Widget detailedStatsWidget;
    private final Widget equipmentDollWidget;

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
        detailedStatsWidget = createDetailedStatsWidget(backdrop, hero);

        addWidget(new ButtonWidget(
            LayoutData.builder()
                .anchor(new VDim(1, 0,
                    -GUISpriteSheet.Resources.BUTTON_DEFAULT.getRegion().getWidth(),
                    -GUISpriteSheet.Resources.BUTTON_DEFAULT.getRegion().getHeight() - 4))
                .size(GUISpriteSheet.Resources.BUTTON_DEFAULT)
                .build(),
            "Details",
            () -> {
                detailedStatsWidget.setEnabled(!detailedStatsWidget.isEnabled());
                return null;
            }
        ));

        this.equipmentDollWidget = setupEquipmentDollWidget();
    }

    private Widget setupEquipmentDollWidget() {
        final EquipmentDollWidget equipmentDollWidget = new EquipmentDollWidget(LayoutData.DEFAULT, hero.getEquipment());

        addWidget(equipmentDollWidget);
        equipmentDollWidget.setEnabled(false);
        return equipmentDollWidget;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        super.setEnabled(isEnabled);
        detailedStatsWidget.setEnabled(false);
        equipmentDollWidget.setEnabled(isEnabled);
    }

    private void setupStatsWidgets(SpriteWidget backdrop) {
        createAndBindStatWidget(backdrop, TraitName.STRENGTH, 1);
        createAndBindStatWidget(backdrop, TraitName.DEXTERITY, 33);
        createAndBindStatWidget(backdrop, TraitName.CONSTITUTION, 66);
        createAndBindStatWidget(backdrop, TraitName.INTELLIGENCE, 101);
        createAndBindStatWidget(backdrop, TraitName.FAITH, 134);
    }

    private void createAndBindStatWidget(
        final SpriteWidget backdrop,
        final TraitName trait,
        final int yOffs
    ) {
        final DynamicValueComponent avc = hero.trait(trait).get();
        final String formatString = "{COLOR=255,255,255,255}{SIZE=20}{STYLE=CENTER}";

        MouseInputWidget miw = new MouseInputWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 179, yOffs))
            .size(new VDim(0, 0, 31, 31))
            .build(),
            InputLayer.GUI);
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
            trait.name,
            trait.description,
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
            ttw.setVisible(e);
            return null;
        });
        ttw.setVisible(false);
    }

    private Widget createDetailedStatsWidget(SpriteWidget backdrop, Hero hero) {
        final Widget miw = new MouseInputWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 0, -(161 + 6) - 26))
            .size(new VDim(0, 0, GUISpriteSheet.Resources.HERO_STAT_SHEET_BACKDROP.getRegion().getWidth(), 161))
            .build(), InputLayer.GUI);
        final Widget detailedStatsWidget = new BackgroundPanelWidget(LayoutData.DEFAULT,
            new Color(33, 33, 33, 255));

        final GridLayoutWidget glw = new GridLayoutWidget(
            LayoutData.builder()
                .anchor(new VDim(0, 0, 2, 13))
                .size(new VDim(1, 1, 0, 0))
                .build(),
            hero.getStats().keySet().size(),
            2);

        int row = 0;
        for (var statName : hero.getStats().keySet()) {
            var statNameTfw = new TextFieldWidget(LayoutData.DEFAULT, "{COLOR=LIGHT_GRAY}" + statName.name);
            statNameTfw.setTextAlignment(FontContext.TextAlign.LEFT);
            statNameTfw.setVerticalTextAlignment(FontContext.VerticalTextAlignment.TOP);

            glw.setCell(row, 0, statNameTfw);

            var statValue = hero.getStats().get(statName);
            var statValueTfw = new TextFieldWidget(LayoutData.DEFAULT,
                "{COLOR=GREEN}" + statValue.getCurrentValue());

            statValueTfw.setTextAlignment(FontContext.TextAlign.RIGHT);
            statValueTfw.setVerticalTextAlignment(FontContext.VerticalTextAlignment.TOP);
            glw.setCell(row, 1, statValueTfw);

            ++row;
        }

//        final String[] str = new String[]{
//            "{COLOR=LIGHT_GRAY}Melee Damage",
//            "{COLOR=GREEN}{STYLE=BOLD}1d8+3",
//            "{COLOR=LIGHT_GRAY}Spell DC",
//            "{COLOR=GREEN}{STYLE=BOLD}18",
//            "{COLOR=LIGHT_GRAY}Crit Chance",
//            "{COLOR=GREEN}{STYLE=BOLD}50.0%",
//            "{COLOR=LIGHT_GRAY}Armor",
//            "{COLOR=GREEN}{STYLE=BOLD}180",
//            "{COLOR=LIGHT_GRAY}Evasion",
//            "{COLOR=GREEN}{STYLE=BOLD}25.0%",
//            "{COLOR=LIGHT_GRAY}Fire Magic Bonus",
//            "{COLOR=LIGHT_GRAY}0%",
//            "{COLOR=LIGHT_GRAY}Frost Magic Bonus",
//            "{COLOR=LIGHT_GRAY}0%",
//            "{COLOR=LIGHT_GRAY}Faith Magic Bonus",
//            "{COLOR=GREEN}{STYLE=BOLD}9%",
//            "{COLOR=LIGHT_GRAY}Eldritch Bonus",
//            "{COLOR=RED}{STYLE=BOLD}-150%",
//            "{COLOR=WHITE}Experience",
//            "{COLOR=GREEN}{STYLE=BOLD}175/600"
//        };
//
//        int i = 0;
//        for (int r = 0; r < 10; ++r) {
//            for (int c = 0; c < 2; ++c) {
//                TextFieldWidget tfw = new TextFieldWidget(LayoutData.DEFAULT, str[i++]);
//                glw.setCell(r, c, tfw);
//                if (c == 0) {
//                    tfw.setTextAlignment(FontContext.TextAlign.LEFT);
//                    tfw.setVerticalTextAlignment(FontContext.VerticalTextAlignment.TOP);
//                } else {
//                    tfw.setTextAlignment(FontContext.TextAlign.RIGHT);
//                    tfw.setVerticalTextAlignment(FontContext.VerticalTextAlignment.TOP);
//                }
//            }
//        }

        backdrop.addWidget(miw);
        miw.addWidget(detailedStatsWidget);
        detailedStatsWidget.addWidget(glw);

        return detailedStatsWidget;
    }
}
