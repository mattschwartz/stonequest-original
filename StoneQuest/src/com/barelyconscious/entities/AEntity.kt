package com.barelyconscious.entities

import com.barelyconscious.components.IComponent

abstract class AEntity(
    val entityName: String
) {
    val components: MutableList<IComponent> = mutableListOf()
}
