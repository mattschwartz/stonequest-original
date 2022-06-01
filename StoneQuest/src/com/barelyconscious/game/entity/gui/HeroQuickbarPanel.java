package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.PowerComponent;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.widgets.*;
import com.barelyconscious.game.entity.resources.ResourceGUI;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.shape.Vector;

import java.awt.*;
import java.util.Objects;

public class HeroQuickbarPanel extends Widget {

    private final SpriteWidget spriteWidget;
    private final SpriteWidget selectedSpriteWidget;

    private final Hero hero;

    public HeroQuickbarPanel(final Anchor anchor, final Hero hero) {
        super(anchor);
        this.hero = hero;
        this.spriteWidget = new SpriteWidget(Anchor.builder()
            .width((int) (ResourceGUI.HERO_UNIT_FRAME.width * .75))
            .height((int) (ResourceGUI.HERO_UNIT_FRAME.height * .75))
            .build(),
            ResourceGUI.HERO_UNIT_FRAME);
        this.selectedSpriteWidget = new SpriteWidget(Anchor.builder()
            .width((int) (ResourceGUI.HERO_UNIT_FRAME_SELECTED.width * .75))
            .height((int) (ResourceGUI.HERO_UNIT_FRAME_SELECTED.height * .75))
            .build(),
            ResourceGUI.HERO_UNIT_FRAME_SELECTED);

        addWidget(new BackgroundPanelWidget(Anchor.builder()
            .width(anchor.width)
            .height(anchor.height)
            .build(),
            new Color(55, 55, 55)));

        addWidget(new TextFieldWidget(Anchor.builder()
            .alignTop(1)
            .paddingTop(-10)
            .paddingLeft(6)
            .height(14)
            .width(64)
            .build(),
            hero.name));

        addWidget(spriteWidget);
        selectedSpriteWidget.setEnabled(false);
        addWidget(selectedSpriteWidget);

        addWidget(new ProgressBarWidget(Anchor.builder()
            .paddingTop(101)
            .paddingLeft(9)
            .height(12)
            .width(102)
            .build(),
            hero.getComponent(HealthComponent.class),
            new Color(0, 0, 0, 0),
            new Color(106, 190, 48)));
        addWidget(new ProgressBarWidget(Anchor.builder()
            .paddingTop(99 + 17 + 6)
            .paddingLeft(9)
            .height(7)
            .width(102)
            .build(),
            hero.getComponent(PowerComponent.class),
            new Color(0, 0, 0, 0),
            new Color(91, 110, 225)));

        hero.getComponent(HealthComponent.class)
            .delegateOnValueChanged.bindDelegate(e -> {
                final FloatingTextWidget wFtext = new FloatingTextWidget(Anchor.builder()
                    .alignTop(0.67f)
                    .alignLeft(0.4f)
                    .build(),
                    Vector.UP,
                    0.2f,
                    Color.RED);
                spriteWidget.addWidget(wFtext);
                wFtext.resize(spriteWidget.screenBounds);

                wFtext.beginFloating(String.format("%.1f", e.delta));
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
