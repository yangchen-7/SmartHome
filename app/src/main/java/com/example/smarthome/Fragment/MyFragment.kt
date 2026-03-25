package com.example.smarthome.Fragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.smarthome.R
import com.example.smarthome.databinding.FragmentMyBinding

class MyFragment : Fragment() {

    private lateinit var binding : FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentMyBinding.inflate(inflater,container,false)
        return binding.root
    }


}