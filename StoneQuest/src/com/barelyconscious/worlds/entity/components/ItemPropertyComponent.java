package com.barelyconscious.worlds.entity.components;

import lombok.Getter;

@Getter
public class ItemPropertyComponent extends Component {

    private final String propertyDescription;

    public ItemPropertyComponent(final String propertyDescription) {
        super(null);
        this.propertyDescription = propertyDescription;
    }
}
