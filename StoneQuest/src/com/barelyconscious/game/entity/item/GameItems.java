package com.barelyconscious.game.entity.item;

import com.barelyconscious.game.entity.resources.ItemsSpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameItems {
    WILLOW_BARK(0, 1, ItemClassType.MATERIAL, false, true, "Willow Bark", "Bark from the willow tree. Has minimal healing properties.", ItemsSpriteSheet.Resources.ITEM_WILLOW_BARK),
    CURED_LEATHER(1, 1, ItemClassType.MATERIAL, false, true, "Cured Leather", "What ails the leather that it needs curing?", ItemsSpriteSheet.Resources.ITEM_CURED_LEATHER),
    IRON_ORE(2, 1, ItemClassType.MATERIAL, false, true, "Iron Ore", "Unrefined iron ore.", ItemsSpriteSheet.Resources.ITEM_IRON_ORE),
    STREAM_DRIVE(3, 1, ItemClassType.MATERIAL, true, true, "Stream Drive", "A stream drive.", ItemsSpriteSheet.Resources.ITEM_STREAM_DRIVE),
    IRON_SHIELD(4, 1, ItemClassType.EQUIPMENT_LEFT_HAND, false, false, "Iron Shield", "A shield made of iron.", ItemsSpriteSheet.Resources.ITEM_IRON_SHIELD),
    CLOTH_ROBE(5, 1, ItemClassType.EQUIPMENT_CHEST, false, false, "Cloth Robe", "Little more than a bath robe.", ItemsSpriteSheet.Resources.ITEM_CLOTH_ROBE),

    ;

    private final int itemId;
    private final int itemLevel;
    private final ItemClassType itemClassType;
    private final boolean isConsumable;
    private final boolean isStackable;
    private final String itemName;
    private final String itemDescription;
    private final ItemsSpriteSheet.Resources itemSprite;

    public Item toItem() {
        return Item.builder()
            .itemId(itemId)
            .itemLevel(itemLevel)
            .itemClassType(itemClassType)
            .isConsumable(isConsumable)
            .isStackable(isStackable)
            .name(itemName)
            .description(itemDescription)
            .sprite(Resources.instance().getSprite(itemSprite))
            .build();
    }
}
