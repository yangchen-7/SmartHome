package com.example.smarthome.Activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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

        val decorView = getWindow().getDecorView()
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        )
        getWindow().setStatusBarColor(Color.TRANSPARENT)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNav = findViewById(R.id.bottom_nav)
        initDrawerLayout()
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

    private fun initDrawerLayout(){
        val drawerLayout = binding.drawerLayout
        val comebackMenu = findViewById<LinearLayout>(R.id.menu_comeback)
        val leaveMenu = findViewById<LinearLayout>(R.id.menu_leave)

        // 默认选中回家模式
        comebackMenu.setSelected(true);
        leaveMenu.setSelected(false);

        comebackMenu.setOnClickListener {
            comebackMenu.isSelected = true
            leaveMenu.isSelected = false
            // TODO: 执行回家模式逻辑
            // 例如：打开灯、打开空调

            drawerLayout.closeDrawer(GravityCompat.START)
        }

        leaveMenu.setOnClickListener {
            comebackMenu.isSelected = false
            leaveMenu.isSelected = true

            // TODO: 执行离家模式逻辑
            // 例如：关闭所有设备

            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

}