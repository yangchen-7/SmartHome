package com.example.smarthome.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.smarthome.R

class LivingRoomActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_living_room)
        setRoomTitle(getString(R.string.living_room_title));
    }

}