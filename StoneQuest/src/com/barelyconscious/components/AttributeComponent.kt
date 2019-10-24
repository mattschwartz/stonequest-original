package com.barelyconscious.components

import com.barelyconscious.entities.Attribute

abstract class AAttributeComponent(
    val attribute: Attribute,
    val value: Double
) : IComponent

class HitpointsAttributeComponent(value: Double) : AAttributeComponent(Attribute.HITPOINTS, value)
class AgilityAttributeComponent(value: Double) : AAttributeComponent(Attribute.AGILITY, value)
class AccuracyAttributeComponent(value: Double) : AAttributeComponent(Attribute.ACCURACY, value)
class DefenseAttributeComponent(value: Double) : AAttributeComponent(Attribute.DEFENSE, value)
class StrengthAttributeComponent(value: Double) : AAttributeComponent(Attribute.STRENGTH, value)
class FireMagicAttributeComponent(value: Double) : AAttributeComponent(Attribute.FIRE_MAGIC, value)
class FrostMagicAttributeComponent(value: Double) : AAttributeComponent(Attribute.FROST_MAGIC, value)
class HolyMagicAttributeComponent(value: Double) : AAttributeComponent(Attribute.HOLY_MAGIC, value)
class ChaosMagicAttributeComponent(value: Double) : AAttributeComponent(Attribute.CHAOS_MAGIC, value)
class SpellMagicAttributeComponent(value: Double) : AAttributeComponent(Attribute.SPELL_MAGIC, value)
