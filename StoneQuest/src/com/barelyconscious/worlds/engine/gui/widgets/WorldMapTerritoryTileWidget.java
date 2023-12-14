package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.MouseInputWidget;
import com.barelyconscious.worlds.entity.wilderness.Territory;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.types.Biome;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.function.Function;

public class WorldMapTerritoryTileWidget extends MouseInputWidget {

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
    private final SpriteWidget territoryHoverMarker;
    private final Function<Territory, Void> setSelectedTerritory;

    public WorldMapTerritoryTileWidget(
        final LayoutData layoutData,
        final Territory territory,
        final Function<Territory, Void> setSelectedTerritory
    ) {
        super(layoutData);
        this.setSelectedTerritory = setSelectedTerritory;

        this.biomeBackground = new BackgroundPanelWidget(LayoutData.builder()
            .anchor(0, 0, 1, 1)
            .size(0, 0, 31, 31)
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

        this.territoryHoverMarker = new SpriteWidget(LayoutData.builder()
            .anchor(LayoutData.ANCHOR_TOP_LEFT)
            .size(0, 0, 32, 32)
            .build(),
            new BetterSpriteResource("gui::territory_selector").load());
        addWidget(territoryHoverMarker);
        territoryHoverMarker.setEnabled(false);

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
            biomeBackground.setEnabled(true);
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

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        if (territory == null) {
            return super.onMouseEntered(e);
        }
        setSelectedTerritory.apply(territory);
        territoryHoverMarker.setEnabled(true);
        return super.onMouseEntered(e);
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        setSelectedTerritory.apply(null);
        territoryHoverMarker.setEnabled(false);
        return super.onMouseExited(e);
    }
}
