package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.engine.gui.widgets.WorldMapTerritoryTileWidget;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.WildernessSystem;
import com.barelyconscious.worlds.game.types.Biome;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

public class WorldMap extends Widget {

    private final int numMapCols = 30;
    private final int numMapRows = 18;
    private final WorldMapTerritoryTileWidget[][] mapTiles =
        new WorldMapTerritoryTileWidget[numMapCols][numMapRows];

    private int mapStartX = Integer.MAX_VALUE;
    private int mapStartY = Integer.MAX_VALUE;
    private int mapEndX = 0;
    private int mapEndY = 0;

    public WorldMap() {
        super(LayoutData.builder()
            .anchor(0.145, 0.145, 0, 0)
            .size(.75, .75, 0, 0)
            .build());
        addWidget(new BackgroundPanelWidget(LayoutData.builder()
            .anchor(0, 0, 0, 0)
            .size(LayoutData.SIZE_FILL)
            .build(),
            new Color(0, 0, 0, 0.5f)));
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
        var wild = GameInstance.instance()
            .getSystem(WildernessSystem.class);
        wild.delegateOnTerritoryAdded.bindDelegate(this::onTerritoryAdded);

        addWidget(new DrawMapComponent());
        addWidget(new WildernessInfoPanel(LayoutData.builder()
            .anchor(0, 1, 0, -70)
            .size(0, 0, 200, 80)
            .build()));

        for (Vector location : wild.getWorldMap().keySet()) {
            mapStartY = (int) Math.min(mapStartY, location.y);
            mapStartX = (int) Math.min(mapStartX, location.x);

            mapEndY = (int) Math.max(mapEndY, location.y);
            mapEndX = (int) Math.max(mapEndX, location.x);
        }

        for (int x = 0; x < numMapCols; ++x) {
            for (int y = 0; y < numMapRows; ++y) {
                mapTiles[x][y] = new WorldMapTerritoryTileWidget(LayoutData.builder()
                    .anchor(0, 0, x * 32, y * 32)
                    .size(0, 0, 32, 32)
                    .build(),
                    null);
                addWidget(mapTiles[x][y]);
            }
        }
    }

    private Void onTerritoryAdded(WildernessSystem.TerritoryAdded territoryAdded) {
        if (territoryAdded.location.x < mapStartX) {
            mapStartX = (int) territoryAdded.location.x;
        }
        if (territoryAdded.location.y < mapStartY) {
            mapStartY = (int) territoryAdded.location.y;
        }
        if (territoryAdded.location.x > mapEndX) {
            mapEndX = (int) territoryAdded.location.x;
        }
        if (territoryAdded.location.y > mapEndY) {
            mapEndY = (int) territoryAdded.location.y;
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

            final Set<Vector> discoveredLocations = wild.getWorldMap().keySet();

            var currentTerritory = GameInstance.instance()
                .getWorld().getWildernessLevel().getTerritory();

            for (int x = mapStartX; x <= mapEndX; x++) {
                for (int y = mapStartY; y <= mapEndY; y++) {
                    var territory = wild.getWorldMap().get(new Vector(x, y));
                    if (territory == null || !discoveredLocations.contains(new Vector(x, y))) {
                        continue;
                    }

                    Color color;
                    if (territory.getBiome() == null) {
                        color = Color.WHITE;
                    } else {
                        color = biomeColors.get(territory.getBiome());
                    }

                    var pos = new Vector((x + Math.abs(mapStartX)) * 32, (y + Math.abs(mapStartY)) * 32);
                    pos = pos.plus(screenBounds.left, screenBounds.top);

                    renderContext.renderGuiRect(color, true,
                        (int) pos.x + 1, (int) pos.y + 1, 31, 31);

                    if (territory == currentTerritory) {
                        renderContext.renderGuiRect(
                            Color.YELLOW,
                            false,
                            (int) pos.x,
                            (int) pos.y,
                            32,
                            32);
                    }
                }
            }
        }
    }

    private static final Map<Biome, Color> biomeColors = Map.of(
        Biome.DESERT, new Color(193, 181, 103),
        Biome.FOREST, new Color(7, 91, 7),
        Biome.MOUNTAINOUS, new Color(114, 129, 136),
        Biome.PLAINS, new Color(210, 188, 46),
        Biome.SWAMP, new Color(62, 147, 106),
        Biome.TUNDRA, new Color(114, 227, 232)
    );
}
