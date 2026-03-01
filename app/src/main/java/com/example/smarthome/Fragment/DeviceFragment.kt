package com.example.smarthome.Fragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.smarthome.databinding.FragmentDeviceBinding
import com.example.smarthome.viewModel.DeviceViewModel

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

        // 开空调
        binding.btnOn.setOnClickListener {
            viewModel.controlDevice("on")
        }

        // 关空调
        binding.btnOff.setOnClickListener {
            viewModel.controlDevice("off")
        }

        // 获取历史
        binding.btnHistory.setOnClickListener {
            viewModel.loadHistory()
        }

        // 观察状态
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.tvResult.text = it
        }

        //TODO 返回数据为空
        // 观察历史消息
        viewModel.messages.observe(viewLifecycleOwner) { list ->
            val text = list.joinToString("\n") {
                "消息: ${it.msg} 时间: ${it.time}"
            }
            binding.tvHistory.text = text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}