package com.barelyconscious.components

import com.barelyconscious.entities.EntityAttribute

/**
 * Attribute modifier component found on equipped gear.
 */
class EquippedAttributeComponent(
    entityAttribute: EntityAttribute,
    value: Double
) : AAttributeComponent(entityAttribute, value)
