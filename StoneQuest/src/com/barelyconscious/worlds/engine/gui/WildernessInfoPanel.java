package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.entity.wilderness.WildernessLevel;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.World;

import java.awt.*;

public class WildernessInfoPanel extends Widget {

    private WildernessLevel wilderness;
    private final TextFieldWidget territoryName;
    private final TextFieldWidget territoryLevel;
    private final TextFieldWidget numEnemies;
    private final TextFieldWidget numRips;

    public WildernessInfoPanel(LayoutData layoutData) {
        super(layoutData);

        GameInstance.instance().getWorld().delegateOnWorldLoaded
            .bindDelegate(this::onWorldLoaded);

        int xOffs = 5;
        territoryName = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, xOffs, 0)
                .size(0, 0, 100, 20)
                .build());
        addWidget(territoryName);

        territoryLevel = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, xOffs, 20)
                .size(0, 0, 100, 20)
                .build());
        addWidget(territoryLevel);

        numEnemies = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, xOffs, 40)
                .size(0, 0, 100, 20)
                .build());
        addWidget(numEnemies);

        numRips = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, xOffs, 60)
                .size(0, 0, 100, 20)
                .build());
        addWidget(numRips);
    }

    private Void onWorldLoaded(World.OnWorldLoadArgs args) {
        this.wilderness = args.newLevel;
        var territory = wilderness.getTerritory();

        territoryName.setText(territory.getName());
        territoryLevel.setText("Level " + territory.getTerritoryLevel());

        return null;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        var text = String.format("Enemies: %d", wilderness.getEntities().size());
        numEnemies.setText(text);

        text = String.format("Rips: %d", 0);
        numRips.setText(text);
    }
}
