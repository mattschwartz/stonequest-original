package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.entity.WildernessLevel;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.World;

import java.awt.*;

public class WildernessInfoPanel extends Widget {

    private WildernessLevel wilderness;
    private final TextFieldWidget territoryName;
    private final TextFieldWidget territoryLevel;
    private final TextFieldWidget territoryPosition;
    private final TextFieldWidget territoryBiome;
    private final TextFieldWidget territoryClimate;
    private final TextFieldWidget numEnemies;

    public WildernessInfoPanel() {
        super(LayoutData.builder()
            .anchor(0, 1, 0, -100)
            .size(0, 0, 200, 100)
            .build());

        GameInstance.instance().getWorld().delegateOnWorldLoaded
            .bindDelegate(this::onWorldLoaded);

        addWidget(new BackgroundPanelWidget(LayoutData.builder()
            .anchor(0, 0, 0, 0)
            .size(LayoutData.SIZE_FILL)
            .build(),
            Color.DARK_GRAY));

        territoryName = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, 1, 5 + 0)
                .size(0, 0, 100, 20)
                .build());
        addWidget(territoryName);

        territoryLevel = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, 1, 5 + 20)
                .size(0, 0, 100, 20)
                .build());
        addWidget(territoryLevel);

        territoryPosition = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, 1, 5 + 40)
                .size(0, 0, 100, 20)
                .build());
        addWidget(territoryPosition);

        territoryBiome = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, 1, 5 + 60)
                .size(0, 0, 100, 20)
                .build());
        addWidget(territoryBiome);

        territoryClimate = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, 1, 5 + 80)
                .size(0, 0, 100, 20)
                .build());
        addWidget(territoryClimate);

        numEnemies = new TextFieldWidget(
            null,
            LayoutData.builder()
                .anchor(0, 0, 1, 5 + 100)
                .size(0, 0, 100, 20)
                .build());
        addWidget(numEnemies);
    }

    private Void onWorldLoaded(World.OnWorldLoadArgs args) {
        this.wilderness = args.newLevel;
        var territory = wilderness.getTerritory();

        territoryName.setText(territory.getName());
        territoryLevel.setText("Level " + territory.getTerritoryLevel());
        territoryPosition.setText("Position: " + territory.getTransform());
        territoryBiome.setText("Biome: " + territory.getBiome().name());
        territoryClimate.setText("Climate: " + territory.getClimate().name());

        return null;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        numEnemies.setText("Enemies: " + (wilderness.getEntities().size()));
    }
}
