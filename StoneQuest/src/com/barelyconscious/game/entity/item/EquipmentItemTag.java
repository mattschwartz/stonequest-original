package com.barelyconscious.game.entity.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EquipmentItemTag implements ItemTag {

    EQUIPMENT_HEAD("Head"),
    EQUIPMENT_NECK("Neck"),
    EQUIPMENT_CHEST("Chest"),
    EQUIPMENT_GLOVES("Gloves"),
    EQUIPMENT_LEGS("Legs"),
    EQUIPMENT_FEET("Feet"),

    EQUIPMENT_LEFT_HAND("Left hand"),
    EQUIPMENT_RIGHT_HAND("Right hand"),
    ;

    private final String tagName;
}
