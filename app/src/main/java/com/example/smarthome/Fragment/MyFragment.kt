package com.example.smarthome.Fragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.smarthome.R

class MyFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val textView = TextView(requireContext())
        textView.text = "我的页面"
        textView.textSize = 24f
        textView.gravity = Gravity.CENTER
        return textView
    }


}