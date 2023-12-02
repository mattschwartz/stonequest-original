package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.game.Requirement;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.ItemTag;
import com.barelyconscious.worlds.game.item.tags.ConsumableItemTag;
import com.barelyconscious.worlds.game.item.tags.EquipmentItemTag;
import com.barelyconscious.worlds.game.item.tags.RelatedSkillItemTag;
import com.barelyconscious.worlds.game.item.tags.ResourceItemTag;
import com.barelyconscious.worlds.game.item.tags.StackableItemTag;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum GameItems {
    WILLOW_BARK(0, 1, "Willow Bark", "Bark from the willow tree. Has minimal healing properties.",
        new BetterSpriteResource("items::herbs_bundle"),
        Set.of(ResourceItemTag.HERB, RelatedSkillItemTag.MEDICINE, RelatedSkillItemTag.COOKING, StackableItemTag.STACKABLE, ConsumableItemTag.EDIBLE),
        new ArrayList<>(),
        Lists.newArrayList(new ItemProperty.HealthItemProperty(5, false))),
    CURED_LEATHER(1, 1,
        "Cured Leather", "What ails the leather that it needs curing?",
        new BetterSpriteResource("items::cured_leather"),
        Set.of(ResourceItemTag.LEATHER, RelatedSkillItemTag.ANIMAL_HANDLING, RelatedSkillItemTag.TAILORING, StackableItemTag.STACKABLE),
        new ArrayList<>(), new ArrayList<>()),
    IRON_ORE(2, 1,
        "Iron Ore", "Unrefined iron ore.",
        new BetterSpriteResource("items::hematite_ore"),
        Set.of(ResourceItemTag.ORE, RelatedSkillItemTag.METALWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    IRON_INGOT(3, 1,
        "Iron Ingot", "A refined iron ingot.",
        new BetterSpriteResource("items::iron_ingot"),
        Set.of(ResourceItemTag.METAL, RelatedSkillItemTag.METALWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    ELDRITCH_CIRCUIT(4, 1,
        "Eldritch Circuitry", "Electronic circuitry imbued with eldritch fluid.",
        new BetterSpriteResource("items::eldritch_circuit"),
        Set.of(ResourceItemTag.TECH, RelatedSkillItemTag.TECHSMITHING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    IRON_SHIELD(5, 1,
        "Iron Shield", "A shield made of iron.",
        new BetterSpriteResource("items::shield"),
        Set.of(EquipmentItemTag.EQUIPMENT_LEFT_HAND, RelatedSkillItemTag.METALWORKING),
        Lists.newArrayList(new Requirement.TraitRequirement(TraitName.STRENGTH, 10)),
        Lists.newArrayList(new ItemProperty.StatItemProperty(StatName.ARMOR, 5))),
    CLOTH_ROBE(6, 1,
        "Cloth Robe", "Little more than a bath robe.",
        new BetterSpriteResource("items::blue_robe"),
        Set.of(EquipmentItemTag.EQUIPMENT_CHEST, RelatedSkillItemTag.TAILORING),
        new ArrayList<>(),
        Lists.newArrayList(
            new ItemProperty.StatItemProperty(StatName.ARMOR, 1),
            new ItemProperty.TraitItemProperty(TraitName.INTELLIGENCE, 1)
        )),
    RECURVE_BOW(7, 1,
        "Recurve Bow", "A bow with a recurved shape.",
        new BetterSpriteResource("items::recurve_bow"),
        Set.of(EquipmentItemTag.EQUIPMENT_TWO_HANDED, RelatedSkillItemTag.FLETCHING),
        Lists.newArrayList(new Requirement.TraitRequirement(TraitName.DEXTERITY, 10)),
        Lists.newArrayList(new ItemProperty.WeaponDamageProperty(3, 7, 2.3f))),
    LUMBER(8, 1,
        "Lumber", "A piece of lumber.",
        new BetterSpriteResource("items::lumber"),
        Set.of(ResourceItemTag.WOOD, RelatedSkillItemTag.WOODWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    CHAMOMILE(9, 1,
        "Chamomile", "A flower with a pleasant aroma.",
        new BetterSpriteResource("items::chamomile"),
        Set.of(ResourceItemTag.HERB, RelatedSkillItemTag.MEDICINE, RelatedSkillItemTag.COOKING, StackableItemTag.STACKABLE, ConsumableItemTag.EDIBLE),
        new ArrayList<>(),
        Lists.newArrayList(new ItemProperty.HealthItemProperty(5, false))),
    IRON_SWORD(10, 1,
        "Iron Sword", "A sword made of iron.",
        new BetterSpriteResource("items::iron_sword"),
        Set.of(EquipmentItemTag.EQUIPMENT_RIGHT_HAND, RelatedSkillItemTag.METALWORKING),
        Lists.newArrayList(new Requirement.TraitRequirement(TraitName.STRENGTH, 10)),
        Lists.newArrayList(new ItemProperty.WeaponDamageProperty(5, 10, 2.0f))),
    WOOD(11, 1,
        "Wood", "A plank of wood.",
        new BetterSpriteResource("items::wood"),
        Set.of(ResourceItemTag.WOOD, RelatedSkillItemTag.WOODWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    STONE(12, 1,
        "Stone", "Rock flesh",
        new BetterSpriteResource("items::stone"),
        Set.of(ResourceItemTag.STONE, RelatedSkillItemTag.STONEWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>()),
    ;

    private final int itemId;
    private final int itemLevel;
    private final String name;
    private final String description;
    private final BetterSpriteResource sprite;
    private final Set<ItemTag> tags;
    private final List<Requirement> requirements;
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
