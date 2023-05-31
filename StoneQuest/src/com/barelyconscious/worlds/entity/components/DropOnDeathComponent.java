package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.ItemLootActor;
import com.barelyconscious.worlds.game.item.Item;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class DropOnDeathComponent extends OnDeathComponent {

    private final List<Item> itemsOnDrop;

    public DropOnDeathComponent(Actor parent, final Item... items) {
        super(parent, parent.getComponent(HealthComponent.class));
        if (items != null) {
            itemsOnDrop = Lists.newArrayList(items);
        } else {
            itemsOnDrop = new ArrayList<>();
        }
    }

    @Override
    protected void onDeath(DynamicValueComponent.DynamicValueChanged dynamicValueChanged) {
        onNextUpdate(e -> {
            if (isRemoveOnNextUpdate()) {
                return null;
            }

            itemsOnDrop.forEach(droppedItem -> e.getWorldContext().addActor(new ItemLootActor(
                getParent().transform, droppedItem
            )));
            itemsOnDrop.clear();
            setRemoveOnNextUpdate(true);

            return null;
        });
    }
}
