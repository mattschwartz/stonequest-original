package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.Physics;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.entity.wilderness.Territory;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.types.Biome;
import lombok.Getter;

import java.awt.*;
import java.util.Map;

public class WorldMapTerritoryTileWidget extends Widget {

    private static final Map<Biome, Color> biomeColors = Map.of(
        Biome.DESERT, new Color(193, 181, 103),
        Biome.FOREST, new Color(7, 91, 7),
        Biome.MOUNTAINOUS, new Color(114, 129, 136),
        Biome.PLAINS, new Color(210, 188, 46),
        Biome.SWAMP, new Color(62, 147, 106),
        Biome.TUNDRA, new Color(114, 227, 232)
    );

    @Getter
    private Territory territory;

    private final BackgroundPanelWidget biomeBackground;
    private final BackgroundPanelWidget territoryHighlight;

    public WorldMapTerritoryTileWidget(final LayoutData layoutData, final Territory territory) {
        super(layoutData);

        this.biomeBackground = new BackgroundPanelWidget(LayoutData.builder()
            .anchor(LayoutData.ANCHOR_TOP_LEFT)
            .size(0, 0, 32, 32)
            .build(),
            new Color(0, 0, 0, 0),
            true);
        addWidget(biomeBackground);

        this.territoryHighlight = new BackgroundPanelWidget(LayoutData.builder()
            .anchor(LayoutData.ANCHOR_TOP_LEFT)
            .size(0, 0, 32, 32)
            .build(),
            new Color(0, 0, 0, 0),
            false);
        addWidget(territoryHighlight);

        setTerritory(territory);
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;

        Color color;
        if (territory != null) {
            if (territory.getBiome() == null) {
                color = Color.WHITE;
            } else {
                color = biomeColors.get(territory.getBiome());
            }
            biomeBackground.setFillColor(color);
        } else {
            biomeBackground.setEnabled(false);
        }

        var wild = GameInstance.instance()
            .getWorld().getWildernessLevel();
        if (wild != null) {
            var currentTerritory = wild.getTerritory();
            if (territory != currentTerritory) {
                territoryHighlight.setEnabled(false);
            } else {
                territoryHighlight.setEnabled(true);
                territoryHighlight.setFillColor(Color.YELLOW);
            }
        } else {
            territoryHighlight.setEnabled(false);
        }
    }
}
