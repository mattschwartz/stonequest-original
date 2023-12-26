package com.barelyconscious.worlds.engine.gui.extractor;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.engine.gui.widgets.CloseButtonWidget;
import com.barelyconscious.worlds.engine.gui.widgets.SpriteWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;

public class ExtractorDialogPanel extends Widget {

    private final TextFieldWidget dialogText;

    public ExtractorDialogPanel() {
        super(LayoutData.builder()
            .anchor(0.5, 0.5, -420, -188)
            .size(0, 0, 840, 376)
            .build());

        dialogText = new TextFieldWidget(
            "Ah, greetings traveler! Fancy parting ways with\n" +
                "some of your tech treasures? I'm in the market\n" +
                "for all kinds of digital delights.\n\nWhat do you say?",
            LayoutData.builder()
                .anchor(0, 0, 8, 0)
                .size(1, 1, 0, 0)
                .build());

        addWidget(new SpriteWidget(new BetterSpriteResource("gui::extractor_panel").load()));
        addWidget(new CloseButtonWidget(this));
        addWidget(dialogText);

    }

    public void show() {
        setEnabled(true);
        widgets.forEach(widget -> widget.setEnabled(true));
    }
}
