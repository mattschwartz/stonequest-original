package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.VDim;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.tech.MotherboardSocket;

import java.awt.*;
import java.util.List;

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

    private void addSocketWidgets(PlayerPersonalDevice ppd) {
        List<MotherboardSocket> sockets = ppd.getMotherboard().getSockets();

        for (int i = 0; i < sockets.size(); i++) {
            MotherboardSocket socket = sockets.get(i);

            addWidget(new ButtonWidget())
        }
    }


}
