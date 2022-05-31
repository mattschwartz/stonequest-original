package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.game.entity.gui.widgets.ProgressBarWidget;
import com.barelyconscious.game.entity.gui.widgets.SpriteWidget;
import com.barelyconscious.game.entity.gui.widgets.TextFieldWidget;
import com.barelyconscious.game.entity.resources.ResourceSprite;

import java.awt.*;

public class HeroQuickbarPanel extends Widget {

    public HeroQuickbarPanel(final Anchor anchor, final Hero hero) {
        super(anchor);

        addWidget(new BackgroundPanelWidget(Anchor.builder()
            .width(anchor.width)
            .height(anchor.height)
            .build(),
            new Color(55, 55, 55)));
        addWidget(new SpriteWidget(Anchor.builder()
            .width(64)
            .height(64)
            .build(),
            ResourceSprite.PLAYER));
        addWidget(new TextFieldWidget(Anchor.builder()
            .alignTop(1)
            .paddingTop(-10)
                .paddingLeft(6)
            .height(14)
            .width(64)
            .build(),
            hero.name));
        addWidget(new ProgressBarWidget(Anchor.builder()
            .paddingTop(64)
            .height(8)
            .width(64)
            .build(),
            hero.getComponent(HealthComponent.class),
            Color.black,
            Color.GREEN));
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {

    }
}
