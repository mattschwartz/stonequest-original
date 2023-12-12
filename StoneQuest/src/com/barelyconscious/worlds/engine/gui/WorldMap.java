package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.WildernessSystem;
import com.barelyconscious.worlds.game.types.Biome;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

public class WorldMap extends Widget {

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
    }

    private Void onTerritoryAdded(WildernessSystem.TerritoryAdded territoryAdded) {
        // update worldmap cache(s)
        return null;
    }

    private static class DrawMapComponent extends Widget {

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

            int minRows = Integer.MAX_VALUE;
            int minCols = 0;
            int maxRows = 0;
            int maxCols = 0;
            for (Vector location : discoveredLocations) {
                minRows = (int) Math.min(minRows, location.y);
                minCols = (int) Math.min(minCols, location.x);

                maxRows = (int) Math.max(maxRows, location.y);
                maxCols = (int) Math.max(maxCols, location.x);
            }

            var currentTerritory = GameInstance.instance()
                .getWorld().getWildernessLevel().getTerritory();

            for (int x = minCols; x <= maxCols; x++) {
                for (int y = minRows; y <= maxRows; y++) {
                    var territory = wild.getWorldMap().get(new Vector(x, y));
                    if (territory == null || !discoveredLocations.contains(new Vector(x, y))) {
                        continue;
                    }

                    Color color;
                    if  (territory.getBiome() == null) {
                        color = Color.WHITE;
                    } else {
                        color = biomeColors.get(territory.getBiome());
                    }

                    var pos = new Vector((x + Math.abs(minCols)) * 32, (y + Math.abs(minRows)) * 32);
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
