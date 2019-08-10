package com.barelyconscious.entities

import com.barelyconscious.components.IComponent

class Entity {

    val components: MutableList<IComponent> = mutableListOf()
}

class PlayerEntity(
    val moveComponent: IComponent,
    val interactableComponent: IComponent,
    val inventoryComponent: IComponent

) {
}
