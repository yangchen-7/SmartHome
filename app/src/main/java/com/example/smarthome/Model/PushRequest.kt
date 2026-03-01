package com.example.smarthome.Model

data class PushRequest(
    val uid: String,
    val topic: String,
    val type: Int,   // 1=MQTT 3=TCP
    val msg: String,
    val share: Boolean? = null,
    val wemsg: String? = null
)
