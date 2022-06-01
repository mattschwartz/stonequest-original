package com.barelyconscious.game.entity.resources;

public enum ResourceGUI {

    HERO_UNIT_FRAME("GUI/hero_unit_frame.png", 160, 184),
    HERO_UNIT_FRAME_SELECTED("GUI/hero_unit_frame_selected.png", 160, 184);

    public final String filepath;
    public final int width;
    public final int height;

    ResourceGUI(final String filepath, final int width, final int height) {
        this.filepath = filepath;
        this.width = width;
        this.height = height;
    }
}