package com.barelyconscious.game.entity;


public class Item {

    private final int id;
    private final String name;
    private final String description;
    private final Sprite sprite;

    public Item(int id, String name, String description, Sprite sprite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
