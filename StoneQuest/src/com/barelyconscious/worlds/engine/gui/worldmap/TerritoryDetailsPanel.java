package com.barelyconscious.worlds.engine.gui.worldmap;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.entity.wilderness.Territory;

public class TerritoryDetailsPanel extends Widget {

    private final TextFieldWidget territoryName;
    private final TextFieldWidget territoryDescription;
    private final TextFieldWidget territoryLevel;
    private final TextFieldWidget territoryHostility;
    private final TextFieldWidget territoryCorruption;
    private final TextFieldWidget territoryResources;

    public TerritoryDetailsPanel(LayoutData layout) {
        super(layout);

        territoryName = new TextFieldWidget("Hover over a territory", LayoutData.builder()
            .anchor(0, 0, 0, 0)
            .size(LayoutData.SIZE_FILL)
            .build());
        territoryDescription = new TextFieldWidget("", LayoutData.builder()
            .anchor(0, 0, 0, 20)
            .size(LayoutData.SIZE_FILL)
            .build());
        territoryLevel = new TextFieldWidget("", LayoutData.builder()
            .anchor(0, 0, 0, 40)
            .size(LayoutData.SIZE_FILL)
            .build());
        territoryHostility = new TextFieldWidget("", LayoutData.builder()
            .anchor(0, 0, 0, 60)
            .size(LayoutData.SIZE_FILL)
            .build());
        territoryCorruption = new TextFieldWidget("", LayoutData.builder()
            .anchor(0, 0, 0, 80)
            .size(LayoutData.SIZE_FILL)
            .build());
        territoryResources = new TextFieldWidget("", LayoutData.builder()
            .anchor(0, 0, 0, 100)
            .size(LayoutData.SIZE_FILL)
            .build());
        addWidget(territoryName);
        addWidget(territoryDescription);
        addWidget(territoryLevel);
        addWidget(territoryHostility);
        addWidget(territoryCorruption);
        addWidget(territoryResources);
    }

    public void setTerritory(Territory territory) {
        if (territory == null) {
            return;
        }

        territoryName.setText(territory.getName());
        territoryDescription.setText(territory.getClimate() + " " + territory.getBiome());
        territoryLevel.setText("Level " + territory.getTerritoryLevel());
        territoryHostility.setText("Hostility: " + (int) (territory.getHostility() * 100) + "%");
        territoryCorruption.setText("Corruption: " + (int) (territory.getCorruption() * 100) + "%");

        StringBuilder sb = new StringBuilder();
        sb.append("Resources: ");
        for (int i = 0; i < territory.getAvailableResources().size(); i++) {
            sb.append(territory.getAvailableResources().get(i).item.getName());
            if (i < territory.getAvailableResources().size() - 1) {
                sb.append(", ");
            }
        }
        territoryResources.setText(sb.toString());
    }
}
