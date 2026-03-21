package com.example.smarthome.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.Model.DeviceType
import com.example.smarthome.Model.Repository.BemfaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LivingRoomViewModel : ViewModel() {

    private val repository = BemfaRepository()
    private val TAG = "LivingRoomViewModel"

    // ================== 设备类型 ==================

    // ================== UI 状态 ==================
    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val msg: String) : UiState()
        data class Error(val error: String) : UiState()
    }

    // **UI 状态 Flow**
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // ================== 设备状态（Flow，不会丢状态） ==================
    private val currentStateMap = mutableMapOf<DeviceType, Boolean>()

    private val _deviceStateMap = MutableStateFlow<Map<DeviceType, Boolean>>(emptyMap())
    val deviceStateMap: StateFlow<Map<DeviceType, Boolean>> = _deviceStateMap.asStateFlow()


    //ViewModel 初始化时自动加载一次状态
    init {
        loadDeviceStatus()
    }


    // ================== 统一请求控制设备 ==================
    fun controlDevice(device: DeviceType, isOn: Boolean) {
        viewModelScope.launch {

            _uiState.value = UiState.Loading
            val status = if (isOn) "on" else "off"

            val result = when (device) {
                DeviceType.AIR_CONDITIONER -> repository.sendAirConditionerCommend(status)
                DeviceType.TV -> repository.sendTvCommend(status)
                DeviceType.LIVING_LAMP -> repository.sendLivingLampCommend(status)
                // 新增 else 分支：兜底非客厅设备，抛明确异常（避免误调用）
                else -> {
                    Result.failure(IllegalArgumentException("LivingRoomViewModel 不支持控制设备：$device"))
                }
            }

            result.onSuccess {
                _uiState.value = UiState.Success(it)
                updateState(device, isOn)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "未知错误")
            }
        }
    }


    // ================== 加载设备状态（从服务器） ==================
    fun loadDeviceStatus() {
        currentStateMap.clear()  // 清空旧状态
        _deviceStateMap.value = emptyMap()
        loadSingle(DeviceType.AIR_CONDITIONER) { repository.getAirConditionerMessage() }
        loadSingle(DeviceType.TV) { repository.getTVMessage() }
        loadSingle(DeviceType.LIVING_LAMP) { repository.getLivingLampMessage() }
    }

    private fun loadSingle(
        device: DeviceType,
        request: suspend () -> Result<List<com.example.smarthome.Model.MessageItem>>
    ) {
        viewModelScope.launch {
            val result = request()
            result.onSuccess { messages ->
                Log.d(TAG, "设备 $device 获取到 ${messages.size} 条消息")
                if (messages.isNotEmpty()) {
                    val message = messages[0]
                    Log.d(TAG, "设备 $device 信息: msg=${message.msg}, time=${message.time}, unix=${message.unix}")
                    val isOn = message.msg == "on"
                    updateState(device, isOn)
                } else {
                    Log.w(TAG, "设备 $device 没有消息数据")
                }
            }.onFailure {
                Log.e(TAG, "设备 $device 获取失败: ${it.message}", it)
            }
        }
    }

    // ================== 统一更新内部状态并通知UI ==================
    private fun updateState(device: DeviceType, isOn: Boolean) {
        currentStateMap[device] = isOn
        _deviceStateMap.value = currentStateMap.toMap()
    }
}