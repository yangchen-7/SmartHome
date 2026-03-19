package com.example.smarthome.Fragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.smarthome.Model.Repository.BemfaRepository
import com.example.smarthome.databinding.FragmentDeviceBinding
import com.example.smarthome.viewModel.DeviceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeviceFragment : Fragment() {

    private var _binding: FragmentDeviceBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DeviceViewModel
    private val bemfaRepository = BemfaRepository()

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

        // 开空调
        binding.btnOn.setOnClickListener {
            viewModel.controlDevice("airConditioner", "on")
        }

        // 关空调
        binding.btnOff.setOnClickListener {
            viewModel.controlDevice("airConditioner", "off")
        }

        // 获取历史
        binding.btnHistory.setOnClickListener {
            loadAirConditionerHistory()
        }

        // 观察状态
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.tvResult.text = it
        }

        // 观察空调状态
        viewModel.airConditionerStatus.observe(viewLifecycleOwner) {
            binding.tvResult.text = if (it) "空调已开启" else "空调已关闭"
        }
    }

    // 加载空调历史记录
    private fun loadAirConditionerHistory() {
        CoroutineScope(Dispatchers.Main).launch {
            bemfaRepository.getAirConditionerMessage().onSuccess { messages ->
                if (messages.isNotEmpty()) {
                    val text = messages.joinToString("\n") {
                        "消息: ${it.msg} 时间: ${it.time}"
                    }
                    binding.tvHistory.text = text
                } else {
                    binding.tvHistory.text = "暂无历史记录"
                }
            }.onFailure {
                binding.tvHistory.text = "获取历史记录失败"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}