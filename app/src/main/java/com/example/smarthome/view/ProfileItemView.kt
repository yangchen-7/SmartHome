package com.example.smarthome.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.example.smarthome.R
import com.example.smarthome.databinding.ViewProfileItemBinding

class ProfileItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding =
        ViewProfileItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        radius = 15f
        cardElevation = 0f
        setCardBackgroundColor(0x1B242D)
        useCompatPadding = true

        // 读取自定义属性
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.ProfileItemView)

            val text = ta.getString(R.styleable.ProfileItemView_itemText)
            val iconRes = ta.getResourceId(R.styleable.ProfileItemView_itemIcon, 0)

            binding.title.text = text

            if (iconRes != 0) {
                binding.icon.setImageResource(iconRes)
            }

            ta.recycle()
        }
    }

    // ================= 对外 API =================

    fun setText(text: String) {
        binding.title.text = text
    }

    fun setIcon(resId: Int) {
        binding.icon.setImageResource(resId)
    }

}