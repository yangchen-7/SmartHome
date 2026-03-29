package com.example.smarthome.Model

data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T?
)

data class MessageListResponse(
    val code: Int,
    val message: String,
    val data: List<MessageItem>?
)
