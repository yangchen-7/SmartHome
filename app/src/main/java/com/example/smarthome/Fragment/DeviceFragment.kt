package com.example.smarthome.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.example.smarthome.Model.DeviceType
import com.example.smarthome.databinding.FragmentDeviceBinding
import com.example.smarthome.view.DeviceCardView
import com.example.smarthome.viewModel.DeviceViewModel
import kotlinx.coroutines.launch

class DeviceFragment : Fragment() {

    private var _binding: FragmentDeviceBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DeviceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DeviceViewModel::class.java]

        // 初始状态（加载中）
        initLoadingState()

        // 监听在线状态
        observeDeviceOnline()
    }

    /**
     * 初始化：全部显示“加载中...”
     */
    private fun initLoadingState() {
        binding.cvAirConditioner.setState("加载中...")
        binding.cvTv.setState("加载中...")
        binding.cvLivingLamp.setState("加载中...")
        binding.cvWaterHeater.setState("加载中...")
        binding.cvBedroomLamp.setState("加载中...")
        binding.cvCurtain.setState("加载中...")
    }

    /**
     * 监听设备在线状态
     */
    private fun observeDeviceOnline() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deviceOnlineMap.collect { map ->
                    updateCard(binding.cvTempSensor,map[DeviceType.TEMP])
                    updateCard(binding.cvHumiditySensor,map[DeviceType.HUMI])
                    updateCard(binding.cvAirConditioner, map[DeviceType.AIR_CONDITIONER])
                    updateCard(binding.cvTv, map[DeviceType.TV])
                    updateCard(binding.cvLivingLamp, map[DeviceType.LIVING_LAMP])
                    updateCard(binding.cvWaterHeater, map[DeviceType.WATER_HEATER])
                    updateCard(binding.cvBedroomLamp, map[DeviceType.BEDROOM_LAMP])
                    updateCard(binding.cvCurtain, map[DeviceType.CURTAIN])

                }
            }
        }
    }

    /**
     * 更新卡片状态
     */
    private fun updateCard(view: DeviceCardView, isOnline: Boolean?) {
        val stateText = when (isOnline) {
            true -> "在线"
            false -> "离线"
            null -> "加载中..."
        }
        view.setState(stateText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}