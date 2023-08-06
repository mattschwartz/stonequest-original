package com.barelyconscious.worlds.game.item;

import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.item.tags.ConsumableItemTag;
import com.barelyconscious.worlds.game.item.tags.EquipmentItemTag;
import com.barelyconscious.worlds.game.item.tags.RelatedSkillItemTag;
import com.barelyconscious.worlds.game.item.tags.ResourceItemTag;
import com.barelyconscious.worlds.game.item.tags.StackableItemTag;
import com.barelyconscious.worlds.game.resources.Resources;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum GameItems {
    WILLOW_BARK(0, 1, "Willow Bark", "Bark from the willow tree. Has minimal healing properties.",
        new Resources.BetterSpriteResource("items::herbs_bundle"),
        Set.of(ResourceItemTag.HERB, RelatedSkillItemTag.MEDICINE, RelatedSkillItemTag.COOKING, StackableItemTag.STACKABLE, ConsumableItemTag.EDIBLE),
        new ArrayList<>(),
        new ArrayList<>()),
    CURED_LEATHER(1, 1,
        "Cured Leather", "What ails the leather that it needs curing?",
        new Resources.BetterSpriteResource("items::cured_leather"),
        Set.of(ResourceItemTag.LEATHER, RelatedSkillItemTag.ANIMAL_HANDLING, RelatedSkillItemTag.TAILORING, StackableItemTag.STACKABLE),
        new ArrayList<>(), new ArrayList<>()),
    IRON_ORE(2, 1,
        "Iron Ore", "Unrefined iron ore.",
        new Resources.BetterSpriteResource("items::hematite_ore"),
        Set.of(ResourceItemTag.ORE, RelatedSkillItemTag.METALWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    ELDRITCH_CIRCUIT(3, 1,
        "Eldritch Circuitry", "Electronic circuitry imbued with eldritch fluid.",
        new Resources.BetterSpriteResource("items::eldritch_circuit"),
        Set.of(ResourceItemTag.TECH, RelatedSkillItemTag.TECHSMITHING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    IRON_SHIELD(4, 1,
        "Iron Shield", "A shield made of iron.",
        new Resources.BetterSpriteResource("items::shield"),
        Set.of(EquipmentItemTag.EQUIPMENT_LEFT_HAND, RelatedSkillItemTag.METALWORKING),
        Lists.newArrayList(new ItemRequirement.TraitItemRequirement(TraitName.STRENGTH, 10)),
        new ArrayList<>()),
    CLOTH_ROBE(5, 1,
        "Cloth Robe", "Little more than a bath robe.",
        new Resources.BetterSpriteResource("items::blue_robe"),
        Set.of(EquipmentItemTag.EQUIPMENT_CHEST, RelatedSkillItemTag.TAILORING),
        new ArrayList<>(),
        new ArrayList<>()),
    RECURVE_BOW(6, 1,
        "Recurve Bow", "A bow with a recurved shape.",
        new Resources.BetterSpriteResource("items::recurve_bow"),
        Set.of(EquipmentItemTag.EQUIPMENT_TWO_HANDED, RelatedSkillItemTag.FLETCHING),
        new ArrayList<>(),
        Lists.newArrayList(new ItemProperty.WeaponDamageProperty(3, 7, 2.3f))),
    ;

    private final int itemId;
    private final int itemLevel;
    private final String name;
    private final String description;
    private final Resources.BetterSpriteResource sprite;
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
