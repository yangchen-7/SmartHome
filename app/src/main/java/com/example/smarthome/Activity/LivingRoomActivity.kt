package com.example.smarthome.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.smarthome.R
import com.example.smarthome.databinding.ActivityLivingRoomBinding
import com.example.smarthome.viewmodel.LivingRoomViewModel

class LivingRoomActivity : BaseActivity() {

    private lateinit var binding: ActivityLivingRoomBinding
    private lateinit var viewModel: LivingRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLivingRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRoomTitle(getString(R.string.living_room_title))

        // 初始化 ViewModel
        viewModel = ViewModelProvider(this)[LivingRoomViewModel::class.java]

        initView()
        observeData()

        // 加载设备初始状态（从服务器）
        viewModel.loadDeviceStatus()
    }

    /**
     * 初始化UI + 点击事件
     */
    private fun initView() {

        // 设置名称
        binding.airConditioner.setName(getString(R.string.air_conditioner))
        binding.Tv.setName(getString(R.string.living_room_tv))
        binding.LivingLamp.setName(getString(R.string.living_room_lamp))

        // 空调开关
        binding.airConditioner.setOnCheckedChangeListener {
            viewModel.controlDevice(
                LivingRoomViewModel.DeviceType.AIR_CONDITIONER,
                it
            )
        }

        // 电视开关
        binding.Tv.setOnCheckedChangeListener {
            viewModel.controlDevice(
                LivingRoomViewModel.DeviceType.TV,
                it
            )
        }

        // 客厅灯开关
        binding.LivingLamp.setOnCheckedChangeListener {
            viewModel.controlDevice(
                LivingRoomViewModel.DeviceType.LIVING_LAMP,
                it
            )
        }
    }

    /**
     * 数据驱动UI（核心）
     */
    private fun observeData() {

        // ===== 设备状态 =====
        viewModel.deviceStateMap.observe(this) { map ->

            map[LivingRoomViewModel.DeviceType.AIR_CONDITIONER]?.let {
                binding.airConditioner.setChecked(it)
            }

            map[LivingRoomViewModel.DeviceType.TV]?.let {
                binding.Tv.setChecked(it)
            }

            map[LivingRoomViewModel.DeviceType.LIVING_LAMP]?.let {
                binding.LivingLamp.setChecked(it)
            }
        }

        // ===== UI状态（请求状态）=====
        viewModel.uiState.observe(this) { state ->
            when (state) {

                is LivingRoomViewModel.UiState.Loading -> {
                    // 可以换成 ProgressBar
                    Toast.makeText(this, "发送中...", Toast.LENGTH_SHORT).show()
                }

                is LivingRoomViewModel.UiState.Success -> {
                    Toast.makeText(this, state.msg, Toast.LENGTH_SHORT).show()
                }

                is LivingRoomViewModel.UiState.Error -> {
                    Toast.makeText(this, "失败：${state.error}", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
}