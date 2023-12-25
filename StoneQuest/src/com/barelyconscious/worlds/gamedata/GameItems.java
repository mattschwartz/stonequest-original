package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.game.Requirement;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.ItemTag;
import com.barelyconscious.worlds.game.item.tags.*;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Like item templates
 */
@Getter
public class GameItems {

    private static int nextItemId = 0;
    public static final List<GameItems> ALL_GAME_ITEMS = new CopyOnWriteArrayList<>();

    public static GameItems WILLOW_BARK = new GameItems(1, "Willow Bark", "Bark from the willow tree. Has minimal healing properties.",
        new BetterSpriteResource("items::herbs_bundle"),
        Set.of(ResourceItemTag.HERB, RelatedSkillItemTag.MEDICINE, RelatedSkillItemTag.COOKING, StackableItemTag.STACKABLE, ConsumableItemTag.EDIBLE),
        new ArrayList<>(),
        Lists.newArrayList(new ItemProperty.HealthItemProperty(5, false)));
    public static GameItems CURED_LEATHER = new GameItems(1,
        "Cured Leather", "What ails the leather that it needs curing?",
        new BetterSpriteResource("items::cured_leather"),
        Set.of(ResourceItemTag.LEATHER, RelatedSkillItemTag.ANIMAL_HANDLING, RelatedSkillItemTag.TAILORING, StackableItemTag.STACKABLE),
        new ArrayList<>(), new ArrayList<>());
    public static GameItems IRON_ORE = new GameItems(1,
        "Iron Ore", "Unrefined iron ore.",
        new BetterSpriteResource("items::hematite_ore"),
        Set.of(ResourceItemTag.ORE, RelatedSkillItemTag.METALWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems IRON_INGOT = new GameItems(1,
        "Iron Ingot", "A refined iron ingot.",
        new BetterSpriteResource("items::iron_ingot"),
        Set.of(ResourceItemTag.METAL, RelatedSkillItemTag.METALWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems ELDRITCH_CIRCUIT = new GameItems(1,
        "Eldritch Circuitry", "Electronic circuitry imbued with eldritch fluid.",
        new BetterSpriteResource("items::eldritch_circuit"),
        Set.of(ResourceItemTag.TECH, RelatedSkillItemTag.TECHSMITHING, StackableItemTag.STACKABLE, ExtractableItemTag.EXTRACTABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems IRON_SHIELD = new GameItems(1,
        "Iron Shield", "A shield made of iron.",
        new BetterSpriteResource("items::shield"),
        Set.of(EquipmentItemTag.EQUIPMENT_LEFT_HAND, RelatedSkillItemTag.METALWORKING),
        Lists.newArrayList(new Requirement.TraitRequirement(TraitName.STRENGTH, 10)),
        Lists.newArrayList(new ItemProperty.StatItemProperty(StatName.ARMOR, 5)));
    public static GameItems CLOTH_ROBE = new GameItems(1,
        "Cloth Robe", "Little more than a bath robe.",
        new BetterSpriteResource("items::blue_robe"),
        Set.of(EquipmentItemTag.EQUIPMENT_CHEST, RelatedSkillItemTag.TAILORING),
        new ArrayList<>(),
        Lists.newArrayList(
            new ItemProperty.StatItemProperty(StatName.ARMOR, 1),
            new ItemProperty.TraitItemProperty(TraitName.INTELLIGENCE, 1)
        ));
    public static GameItems RECURVE_BOW = new GameItems(1,
        "Recurve Bow", "A bow with a recurved shape.",
        new BetterSpriteResource("items::recurve_bow"),
        Set.of(EquipmentItemTag.EQUIPMENT_TWO_HANDED, RelatedSkillItemTag.FLETCHING),
        Lists.newArrayList(new Requirement.TraitRequirement(TraitName.DEXTERITY, 10)),
        Lists.newArrayList(new ItemProperty.WeaponDamageProperty(3, 7, 2.3f)));
    public static GameItems LUMBER = new GameItems(1,
        "Lumber", "A piece of lumber.",
        new BetterSpriteResource("items::lumber"),
        Set.of(ResourceItemTag.WOOD, RelatedSkillItemTag.WOODWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems CHAMOMILE = new GameItems(1,
        "Chamomile", "A flower with a pleasant aroma.",
        new BetterSpriteResource("items::chamomile_flower"),
        Set.of(ResourceItemTag.HERB, RelatedSkillItemTag.MEDICINE, RelatedSkillItemTag.COOKING, StackableItemTag.STACKABLE, ConsumableItemTag.EDIBLE),
        new ArrayList<>(),
        Lists.newArrayList(new ItemProperty.HealthItemProperty(5, false)));
    public static GameItems IRON_SWORD = new GameItems(1,
        "Iron Sword", "A sword made of iron.",
        new BetterSpriteResource("items::iron_sword"),
        Set.of(EquipmentItemTag.EQUIPMENT_RIGHT_HAND, RelatedSkillItemTag.METALWORKING),
        Lists.newArrayList(new Requirement.TraitRequirement(TraitName.STRENGTH, 10)),
        Lists.newArrayList(new ItemProperty.WeaponDamageProperty(5, 10, 2.0f)));
    public static GameItems WOOD = new GameItems(1,
        "Wood", "A plank of wood.",
        new BetterSpriteResource("items::wood"),
        Set.of(ResourceItemTag.WOOD, RelatedSkillItemTag.WOODWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems STONE = new GameItems(1,
        "Stone", "Rock flesh",
        new BetterSpriteResource("items::stone"),
        Set.of(ResourceItemTag.STONE, RelatedSkillItemTag.STONEWORKING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems GLOWING_ESSENCE = new GameItems(1,
        "Glowing Essence", "A glowing essence.",
        new BetterSpriteResource("items::glowing_essence"),
        Set.of(StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems CORRUPTED_IMAGE = new GameItems(1,
        "Corrupted Image", "A corrupted image. Take to an extractor.",
        new BetterSpriteResource("items::digital_image_corrupt"),
        Set.of(StackableItemTag.STACKABLE, ExtractableItemTag.EXTRACTABLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems RECOVERED_IMAGE = new GameItems(1,
        "Recovered Image", "A recovered image. Take to a collector.",
        new BetterSpriteResource("items::recovered_digital_image"),
        Set.of(StackableItemTag.STACKABLE, CollectibleItemTag.COLLECTIBLE),
        new ArrayList<>(),
        new ArrayList<>());
    public static GameItems ELDRITCH_DRIVE = new GameItems(1,
        "Eldritch Drive", "A drive imbued with eldritch fluid.",
        new BetterSpriteResource("items::eldritch_drive"),
        Set.of(ResourceItemTag.TECH, RelatedSkillItemTag.TECHSMITHING, StackableItemTag.STACKABLE),
        new ArrayList<>(),
        new ArrayList<>());

    private final int itemId;
    private final int itemLevel;
    private final String name;
    private final String description;
    private final BetterSpriteResource sprite;
    private final Set<ItemTag> tags;
    private final List<Requirement> requirements;
    private final List<ItemProperty> properties;

    private GameItems(
        int itemLevel,
        String name,
        String description,
        BetterSpriteResource sprite,
        Set<ItemTag> tags,
        List<Requirement> requirements,
        List<ItemProperty> properties
    ) {
        this.itemId = nextItemId++;
        this.itemLevel = itemLevel;
        this.name = name;
        this.description = description;
        this.sprite = sprite;
        this.tags = tags;
        this.requirements = requirements;
        this.properties = properties;

        ALL_GAME_ITEMS.add(this);
    }

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
