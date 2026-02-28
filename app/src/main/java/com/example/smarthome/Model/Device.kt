package com.example.smarthome.Model

data class Device(
    val id: Int,
    val name: String,
    val type: String,
    val roomId: Int,
    val roomName: String,
    val status: Boolean
)