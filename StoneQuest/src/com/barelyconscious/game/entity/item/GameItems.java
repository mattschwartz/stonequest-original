package com.barelyconscious.game.entity.item;

import com.barelyconscious.game.entity.item.tags.ConsumableItemTag;
import com.barelyconscious.game.entity.item.tags.EquipmentItemTag;
import com.barelyconscious.game.entity.item.tags.RelatedSkillItemTag;
import com.barelyconscious.game.entity.item.tags.ResourceItemTag;
import com.barelyconscious.game.entity.item.tags.StackableItemTag;
import com.barelyconscious.game.entity.resources.Resources;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public enum GameItems {
    WILLOW_BARK(0, 1, "Willow Bark", "Bark from the willow tree. Has minimal healing properties.",
        Resources.ITEM_HERBS_BUNDLE,
        Set.of(ResourceItemTag.HERB, RelatedSkillItemTag.MEDICINE, RelatedSkillItemTag.COOKING, StackableItemTag.STACKABLE, ConsumableItemTag.EDIBLE),
        new ArrayList<>(),
        new ArrayList<>()),
    CURED_LEATHER(1, 1,
        "Cured Leather", "What ails the leather that it needs curing?",
        Resources.ITEM_CURED_LEATHER,
        Set.of(ResourceItemTag.LEATHER, RelatedSkillItemTag.ANIMAL_HANDLING, RelatedSkillItemTag.TAILORING, StackableItemTag.STACKABLE),
        new ArrayList<>(), new ArrayList<>()),
    IRON_ORE(2, 1,
        "Iron Ore", "Unrefined iron ore.",
        Resources.ITEM_HEMATITE_ORE,
        Set.of(ResourceItemTag.ORE, RelatedSkillItemTag.METALWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    ELDRITCH_CIRCUIT(3, 1,
        "Eldritch Circuitry", "Electronic circuitry imbued with eldritch fluid.",
        Resources.ITEM_ELDRITCH_CIRCUIT,
        Set.of(ResourceItemTag.TECH, RelatedSkillItemTag.TECHSMITHING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    IRON_SHIELD(4, 1,
        "Iron Shield", "A shield made of iron.",
        Resources.ITEM_SHIELD,
        Set.of(EquipmentItemTag.EQUIPMENT_LEFT_HAND, RelatedSkillItemTag.METALWORKING),
        new ArrayList<>(),
        new ArrayList<>()),
    CLOTH_ROBE(5, 1,
        "Cloth Robe", "Little more than a bath robe.",
        Resources.ITEM_BLUE_ROBE,
        Set.of(EquipmentItemTag.EQUIPMENT_CHEST, RelatedSkillItemTag.TAILORING),
        new ArrayList<>(),
        new ArrayList<>()),
    ;

    private final int itemId;
    private final int itemLevel;
    private final String name;
    private final String description;
    private final Resources.Sprite_Resource sprite;
    private final Set<ItemTag> tags;
    private final List<ItemRequirement> requirements;
    private final List<ItemProperty> properties;

    public Item toItem() {
        return Item.builder()
            .itemId(itemId)
            .itemLevel(itemLevel)
            .name(name)
            .description(description)
            .sprite(sprite)
            .tags(tags)
            .requirements(requirements)
            .properties(properties)
            .build();
    }
}
