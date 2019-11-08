package com.barelyconscious.systems.messaging.data

class MessageResponse(val responseData: Any?, val responseCode: ResponseCode) {

    companion object {
        fun ok() = MessageResponse(null, ResponseCode.OK)

        fun ok(responseData: Any?) = MessageResponse(responseData, ResponseCode.OK)

        fun ok(message: String) = MessageResponse(message, ResponseCode.OK)

        fun failed(e: Exception) = MessageResponse(e, ResponseCode.FAILED)

        fun failed(msg: String) = MessageResponse(msg, ResponseCode.FAILED)
    }

    override fun toString(): String = "[Code=$responseCode, Data=$responseData]"
}

