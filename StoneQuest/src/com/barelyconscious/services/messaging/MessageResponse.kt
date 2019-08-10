package com.barelyconscious.services.messaging

enum class ResponseCode {
    OK,
    FAILED,
}

class MessageResponse(val responseData: Any?, val responseCode: ResponseCode) {

    companion object {
        fun ok() = MessageResponse(null, ResponseCode.OK)

        fun failed(e: Exception) = MessageResponse(e, ResponseCode.FAILED)
    }
}

