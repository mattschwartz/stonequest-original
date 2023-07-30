package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.VDim;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.game.GameInstance;

import java.awt.*;

public class PlayerPersonalDeviceWidget extends Widget {

    public PlayerPersonalDeviceWidget() {
        super(LayoutData.builder()
            .size(new VDim(0, 0, 200, 200))
            .anchor(new VDim(1, 0, -200, 120))
            .build());


        var ppd = GameInstance.instance()
            .getPlayerPersonalDevice();

        addWidget(new BackgroundPanelWidget(LayoutData.DEFAULT,
            new Color(33, 33, 33, 255)));
        addWidget(new TextFieldWidget(LayoutData.DEFAULT,
            ppd.name));
    }


}
