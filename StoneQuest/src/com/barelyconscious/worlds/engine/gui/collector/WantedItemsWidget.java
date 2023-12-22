package com.barelyconscious.worlds.engine.gui.collector;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.engine.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.engine.gui.widgets.SpriteWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.game.GameInstance;

import java.util.ArrayList;
import java.util.List;

public class WantedItemsWidget extends Widget {

    private final List<SpriteWidget> wantedItemSprites = new ArrayList<>(3);
    private final List<ButtonWidget> wantedItemTexts = new ArrayList<>(3);
    private final TextFieldWidget wantedItemDetailsText;

    public WantedItemsWidget() {
        super(LayoutData.builder()
            .anchor(1, 0, -230, 53)
            .size(0, 0, 200, 500)
            .build());

        int xOffs = 0;
        int yOffs = 0;

        var wantedItem1 = new SpriteWidget(LayoutData.builder()
            .anchor(0, 0, xOffs, yOffs)
            .size(0, 0, 40, 40)
            .build(),
            null);
        var tfw1 = new ButtonWidget(LayoutData.builder()
            .anchor(0, 0, xOffs + 41, yOffs)
            .size(0, 0, 190, 40)
            .build(),
            "",
            () -> {
                setDetails(0);
                return null;
            });

        var wantedItem2 = new SpriteWidget(LayoutData.builder()
            .anchor(0, 0, xOffs, yOffs + 42)
            .size(0, 0, 40, 40)
            .build(),
            null);
        var tfw2 = new ButtonWidget(LayoutData.builder()
            .anchor(0, 0, xOffs + 41, yOffs + 42)
            .size(0, 0, 190, 40)
            .build(),
            "",
            () -> {
                setDetails(1);
                return null;
            });

        var wantedItem3 = new SpriteWidget(LayoutData.builder()
            .anchor(0, 0, xOffs, yOffs + 84)
            .size(0, 0, 40, 40)
            .build(),
            null);
        var tfw3 = new ButtonWidget(LayoutData.builder()
            .anchor(0, 0, xOffs + 41, yOffs + 84)
            .size(0, 0, 190, 40)
            .build(),
            "",
            () -> {
                setDetails(2);
                return null;
            });

        wantedItemDetailsText = new TextFieldWidget("Details", LayoutData.builder()
            .anchor(0, 0, xOffs, yOffs + 126)
            .size(0, 0, 230, 40)
            .build());

        wantedItemSprites.add(wantedItem1);
        wantedItemSprites.add(wantedItem2);
        wantedItemSprites.add(wantedItem3);

        wantedItemTexts.add(tfw1);
        wantedItemTexts.add(tfw2);
        wantedItemTexts.add(tfw3);

        addWidget(wantedItem1);
        addWidget(wantedItem2);
        addWidget(wantedItem3);
        addWidget(wantedItemDetailsText);

        addWidget(tfw1);
        addWidget(tfw2);
        addWidget(tfw3);
    }

    private void setDetails(int index) {
        var text = GameInstance.instance().getGameState()
            .getCollectorState()
            .getWantedItemsDetails()
            .get(index);
        wantedItemDetailsText.setText(text);
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        var items = GameInstance.instance().getGameState()
            .getCollectorState()
            .getWantedItems();

        for (int i = 0; i < items.size(); i++) {
            var sprite = wantedItemSprites.get(i);
            var itemName = wantedItemTexts.get(i);
            var item = items.get(i);

            if (item == null || item.getSprite() == null) {
                sprite.setSprite(null);
                itemName.setText("");
            } else {
                sprite.setSprite(item.getSprite().load());
                itemName.setText(item.getName());
            }
        }
    }
}
