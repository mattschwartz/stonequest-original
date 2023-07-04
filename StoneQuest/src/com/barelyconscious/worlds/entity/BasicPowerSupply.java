package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.game.item.tags.TechItemTag;

public class BasicPowerSupply extends ItemActor {

    public BasicPowerSupply() {
        tags.add(TechItemTag.POWER_TAG);
    }
}
