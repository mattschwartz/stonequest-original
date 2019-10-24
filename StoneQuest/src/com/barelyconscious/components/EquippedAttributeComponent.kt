package com.barelyconscious.components

import com.barelyconscious.entities.Attribute

/**
 * Attribute modifier component found on equipped gear.
 */
class EquippedAttributeComponent(
    attribute: Attribute,
    value: Double
) : AAttributeComponent(attribute, value)
