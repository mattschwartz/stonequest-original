package com.barelyconscious.services

import com.barelyconscious.entities.AEntity
import com.barelyconscious.systems.EntitySystem
import com.barelyconscious.systems.messaging.IMessageObserver
import com.barelyconscious.systems.messaging.data.IMessageData
import com.barelyconscious.systems.messaging.data.MessageResponse

class ApplyDamageMessageData(
    val fromEntity: AEntity,
    val toEntity: AEntity,
    val damageDealt: Double
) : IMessageData

class DamageService(
    private val entitySystem: EntitySystem
) : IMessageObserver {

    companion object {
        const val APPLY_DAMAGE = "stonequest/DamageService/APPLY_DAMAGE"
    }

    override fun alert(eventCode: String, data: IMessageData, sender: Any): MessageResponse {
        return MessageResponse.ok()
    }
}
