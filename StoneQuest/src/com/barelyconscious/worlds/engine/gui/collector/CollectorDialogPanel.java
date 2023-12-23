package com.barelyconscious.worlds.engine.gui.collector;

import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.engine.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.engine.gui.widgets.CloseButtonWidget;
import com.barelyconscious.worlds.engine.gui.widgets.SpriteWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;

import java.util.ArrayList;
import java.util.List;

public class CollectorDialogPanel extends Widget {

    private TextFieldWidget dialogText;
    private TextFieldWidget collectionHintText;
    private final List<ButtonWidget> options = new ArrayList<>(4);

    private enum DialogState {
        WHO_ARE_YOU,
        WHAT_DO_YOU_COLLECT,
        WHAT_DO_YOU_HAVE,
        WHAT_DO_YOU_WANT
    }

    public CollectorDialogPanel() {
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
        collectionHintText = new TextFieldWidget(
            "{COLOR=GRAY}{STYLE=ITALIC}Drag an item from your\n{COLOR=GRAY}{STYLE=ITALIC}inventory here to sell it",
            LayoutData.builder()
                .anchor(0.5, 1, -15, -80)
                .size(1, 1, 0, 0)
                .build());
        collectionHintText.setTextAlignment(FontContext.TextAlign.LEFT);
        collectionHintText.setShowShadow(false);

        addWidget(new SpriteWidget(new BetterSpriteResource("gui::collector_panel").load()));
        addWidget(new CloseButtonWidget(this));
        addWidget(dialogText);
        addWidget(collectionHintText);

        addWidget(new WantedItemsWidget());
        addWidget(new CollectionInventory());
        addOptionSelectWidgets();
    }

    public void show() {
        setEnabled(true);
    }

    private void addOptionSelectWidgets() {
        int xOffs = 1;
        int yOffs = -135;
        int width = 380;
        int height = 24;
        int gutter = 26;

        var button = new ButtonWidget(LayoutData.builder()
            .anchor(0, 1, xOffs, yOffs)
            .size(0, 0, width, height)
            .build(),
            "What do you collect?", () -> {
            setDialogState(DialogState.WHAT_DO_YOU_COLLECT);
            return null;
        });
        options.add(button);
        addWidget(button);

        button = new ButtonWidget(LayoutData.builder()
            .anchor(0, 1, xOffs, yOffs + gutter)
            .size(0, 0, width, height)
            .build(),
            "What would you give me?", () -> {
            setDialogState(DialogState.WHAT_DO_YOU_HAVE);
            return null;
        });
        options.add(button);
        addWidget(button);

        button = new ButtonWidget(LayoutData.builder()
            .anchor(0, 1, xOffs, yOffs + gutter * 2)
            .size(0, 0, width, height)
            .build(),
            "What do you do with this junk?", () -> {
            setDialogState(DialogState.WHO_ARE_YOU);
            return null;
        });
        options.add(button);
        addWidget(button);

        button = new ButtonWidget(LayoutData.builder()
            .anchor(0, 1, xOffs, yOffs + gutter * 3)
            .size(0, 0, width, height)
            .build(),
            "Goodbye", () -> {
            setEnabled(false);
            return null;
        });
        options.add(button);
        addWidget(button);
    }

    private void setDialogState(DialogState state) {
        switch (state) {
            case WHO_ARE_YOU:
                dialogText.setText(
                    "Ah, the wonders of technology! I collect them for\n" +
                        "various reasons – curiosity, nostalgia, and, of\n" +
                        "course, the thrill of the trade. Plus, there are\n" +
                        "folks out there who value these artifacts. You\n" +
                        "never know when an old text document or a\n" +
                        "vintage photo might be worth a small fortune.\n" +
                        "I keep tabs on what I purchase and what I'm\n" +
                        "very interested in if you want to keep an\n" +
                        "eye out for the high-dollar items.");
                break;
            case WHAT_DO_YOU_COLLECT:
                dialogText.setText(
                    "Oh, a bit of this, a bit of that! I'm after anything\n" +
                        "with circuits and screens. Cell phones, computers,\n" +
                        "laptops – you name it. Pictures, videos, text\n" +
                        "documents, the whole digital shebang! Bring me\n" +
                        "your electronic wonders, and I'll trade you some\n" +
                        "legally-acquired tender.");
                break;
            case WHAT_DO_YOU_HAVE:
                dialogText.setText(
                    "Well, it depends on the rarity and condition, you\n" +
                        "see. Pristine gadgets fetch a better price, but I'm\n" +
                        "not picky. A fair deal is what I aim for. Show me\n" +
                        "what you've got, and we can talk numbers.");
                break;
            case WHAT_DO_YOU_WANT:
                dialogText.setText("I'm always looking for new items to add to my collection.");
                break;
        }
    }
}
