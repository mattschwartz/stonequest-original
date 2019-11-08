package com.barelyconscious.components

import com.barelyconscious.entities.EntityAttribute

abstract class AAttributeComponent(
    val entityAttribute: EntityAttribute,
    val value: Double
) : IComponent

class HitpointsAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.HITPOINTS, value)
class AgilityAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.AGILITY, value)
class AccuracyAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.ACCURACY, value)
class DefenseAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.DEFENSE, value)
class StrengthAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.STRENGTH, value)
class FireMagicAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.FIRE_MAGIC, value)
class FrostMagicAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.FROST_MAGIC, value)
class HolyMagicAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.HOLY_MAGIC, value)
class ChaosMagicAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.CHAOS_MAGIC, value)
class SpellMagicAttributeComponent(value: Double) : AAttributeComponent(EntityAttribute.SPELL_MAGIC, value)
