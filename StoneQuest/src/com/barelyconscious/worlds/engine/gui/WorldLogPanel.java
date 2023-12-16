package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.worlds.engine.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TextFieldWidget;
import software.amazon.awssdk.core.retry.backoff.BackoffStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorldLogPanel extends Widget {

    private final List<String> logEntries = new ArrayList<>();
    private final BackgroundPanelWidget container;
    private final TextFieldWidget logWidget = new TextFieldWidget(LayoutData.builder()
        .anchor(0.5, 1, -350, -324)
        .size(0, 0, 700, 160)
        .build(), "Test1\nTest2\nTest3\nTest4\nTexzt5");
    private final TextFieldWidget notificationsWidget = new TextFieldWidget(LayoutData.builder()
        .anchor(0.5, 1, -475, -10)
        .size(0, 0, 100, 30)
        .build(), "0 new messages");

    public WorldLogPanel() {
        super(LayoutData.builder()
            .anchor(0.5, 1, -360, -310)
            .size(0, 0, 720, 180)
            .build());

        container = new BackgroundPanelWidget(LayoutData.DEFAULT, new Color(0, 0, 0, 0.5f));
        container.addWidget(logWidget);
        logWidget.setTextAlignment(FontContext.TextAlign.LEFT);
        logWidget.setVerticalTextAlignment(FontContext.VerticalTextAlignment.BOTTOM);

        notificationsWidget.setTextAlignment(FontContext.TextAlign.LEFT);
        notificationsWidget.setVerticalTextAlignment(FontContext.VerticalTextAlignment.TOP);
        notificationsWidget.setEnabled(false);

        addWidget(container);
        addWidget(new ButtonWidget(LayoutData.builder()
            .anchor(0.5, 1, -475, 0)
            .size(0, 0, 100, 30)
            .build(), "Toggle Log",
            () -> {
                container.setEnabled(!container.isEnabled());
                if (container.isEnabled()) {
                    numNotifications = 0;
                }
                return null;
            }));
        addWidget(notificationsWidget);
    }

    // todo implement scrolling
    private int scrollLinesOffset = 0;

    private int numNotifications = 0;

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (numNotifications > 0) {
            notificationsWidget.setText("New messages");
            notificationsWidget.setEnabled(true);
        } else {
            notificationsWidget.setEnabled(false);
        }
    }

    // todo - support formatting/colors within individual log entries
    public void addLogEntry(String entry) {
        // add a little alert to let the player know that there are new messages
        if (!container.isEnabled()) {
            numNotifications++;
        }

        logEntries.add(entry);

        int backOfTheLine = Math.max(logEntries.size(), 10);
        int start = Math.max(backOfTheLine - 10, 0);

        StringBuilder sb = new StringBuilder();
        for (int i = start; i < backOfTheLine; i++) {
            if (logEntries.size() <= i) {
                sb.append("\n");
            } else {
                sb.append(logEntries.get(i)).append("\n");
            }
        }
        logWidget.setText(sb.toString());
    }
}
