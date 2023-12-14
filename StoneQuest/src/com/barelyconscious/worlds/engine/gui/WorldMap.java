package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.engine.gui.widgets.WorldMapTerritoryTileWidget;
import com.barelyconscious.worlds.entity.wilderness.Territory;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.WildernessSystem;

import java.awt.Color;

public class WorldMap extends Widget {

    private final int numMapCols = 25;
    private final int numMapRows = 15;
    private final int centerCol = 13;
    private final int centerRow = 7;
    private final WorldMapTerritoryTileWidget[][] mapTiles =
        new WorldMapTerritoryTileWidget[numMapCols][numMapRows];
    private final TextFieldWidget selectedTerritoryName;

    public WorldMap() {
        super(LayoutData.builder()
            .anchor(0.145, 0.145, 0, 0)
            .size(.75, .75, 0, 0)
            .build());
        addWidget(new BackgroundPanelWidget(LayoutData.builder()
            .anchor(0, 0, 0, 0)
            .size(LayoutData.SIZE_FILL)
            .build(),
            new Color(0, 0, 0, 0.93f)));
        addWidget(new BackgroundPanelWidget(LayoutData.builder()
            .anchor(0, 0, 0, 0)
            .size(LayoutData.SIZE_FILL)
            .build(),
            Color.YELLOW,
            false));
        addWidget(new TextFieldWidget("World Map",
            LayoutData.builder()
                .anchor(0, 0, 0, -12)
                .size(LayoutData.SIZE_FILL)
                .build()));
        addWidget(new DrawMapComponent());
        addWidget(new WildernessInfoPanel(LayoutData.builder()
            .anchor(0, 1, 0, -70)
            .size(0, 0, 200, 80)
            .build()));
        selectedTerritoryName = new TextFieldWidget("None",
            LayoutData.builder()
                .anchor(1, 0, -200, 20)
                .size(0, 0, 200, 20)
                .build());
        selectedTerritoryName.setEnabled(false);
        addWidget(selectedTerritoryName);

        for (int x = 0; x < numMapCols; ++x) {
            for (int y = 0; y < numMapRows; ++y) {
                mapTiles[x][y] = new WorldMapTerritoryTileWidget(LayoutData.builder()
                    .anchor(0, 0, x * 32, y * 32)
                    .size(0, 0, 32, 32)
                    .build(),
                    null,
                    this::setSelectedTerritory);
                addWidget(mapTiles[x][y]);
            }
        }
    }

    public Void setSelectedTerritory(Territory territory) {
        if (territory != null) {
            selectedTerritoryName.setText(territory.getName());
            selectedTerritoryName.setEnabled(true);
        } else {
            selectedTerritoryName.setEnabled(false);
        }
        return null;
    }

    private class DrawMapComponent extends Widget {

        public DrawMapComponent() {
            super(LayoutData.builder()
                .anchor(LayoutData.ANCHOR_TOP_LEFT)
                .size(LayoutData.SIZE_FILL)
                .build());
        }

        @Override
        protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
            super.onRender(eventArgs, renderContext);
            var wild = GameInstance.instance().getSystem(WildernessSystem.class);
            var currentTerritory = GameInstance.instance()
                .getWorld().getWildernessLevel().getTerritory();

            var currentPos = currentTerritory.getTransform();

            int startX = (int) currentPos.x - centerCol;
            int startY = (int) currentPos.y - centerRow;

            for (int x = 0; x < mapTiles.length; ++x) {
                for (int y = 0; y < mapTiles[0].length; ++y) {
                    var tile = mapTiles[x][y];
                    var pos = new Vector(startX + x, startY + y);
                    var territory = wild.getWorldMap().get(pos);
                    tile.setTerritory(territory);
                }
            }
        }
    }
}
