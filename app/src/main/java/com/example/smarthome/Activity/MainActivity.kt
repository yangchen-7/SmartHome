package com.example.smarthome.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smarthome.Fragment.DeviceFragment
import com.example.smarthome.Fragment.HomeFragment
import com.example.smarthome.Fragment.MyFragment
import com.example.smarthome.R
import com.example.smarthome.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNav = findViewById(R.id.bottom_nav)
        // 默认显示首页
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_device -> {
                    replaceFragment(DeviceFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(MyFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}