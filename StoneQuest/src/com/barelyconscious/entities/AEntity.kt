package com.barelyconscious.entities

import com.barelyconscious.components.AComponent

abstract class AEntity(
    val entityName: String
) {
    val components: MutableList<AComponent> = mutableListOf()
}
