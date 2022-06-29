package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.ItemLootActor;
import com.barelyconscious.game.entity.item.Item;
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
    protected void onDeath(StatValueComponent.StatValueChanged statValueChanged) {
        itemsOnDrop.forEach(droppedItem -> GameInstance.getInstance().getWorld().spawnActor(new ItemLootActor(
            getParent().transform, droppedItem
        )));
        itemsOnDrop.clear();
        setRemoveOnNextUpdate(true);
    }
}
