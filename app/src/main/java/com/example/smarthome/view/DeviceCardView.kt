package com.example.smarthome.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.example.smarthome.R
import com.example.smarthome.databinding.ViewDeviceCardBinding

class DeviceCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private val binding =
        ViewDeviceCardBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        radius = 20f
        cardElevation = 0f
        setCardBackgroundColor(0xFF25313D.toInt())

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.DeviceCardView)

            val icon = ta.getResourceId(R.styleable.DeviceCardView_deviceIcon, 0)
            val title = ta.getString(R.styleable.DeviceCardView_deviceTitle)
            val subTitle = ta.getString(R.styleable.DeviceCardView_deviceSubTitle)

            if (icon != 0) binding.icon.setImageResource(icon)
            binding.title.text = title
            binding.subTitle.text = subTitle

            ta.recycle()
        }
    }

    // 对外API
    fun setIcon(icon: Int) {
        binding.icon.setImageResource(icon)
    }

    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun setState(sub: String) {
        binding.subTitle.text = sub
    }
}