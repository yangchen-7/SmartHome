package com.example.smarthome.Model

data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T?
)
