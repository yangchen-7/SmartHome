package com.example.smarthome.Model

data class Room(
    val id: Int,
    val name: String,
    val iconResId: Int,
    val devices: MutableList<Device> = mutableListOf()
) {
    val deviceCount: Int
        get() = devices.size
}