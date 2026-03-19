package com.example.smarthome.viewmodel

import androidx.lifecycle.*
import com.example.smarthome.Model.Repository.BemfaRepository
import kotlinx.coroutines.launch

class LivingRoomViewModel : ViewModel() {

    private val repository = BemfaRepository()

    // ================== 设备类型 ==================
    enum class DeviceType {
        AIR_CONDITIONER,
        TV,
        LIVING_LAMP
    }

    // ================== UI状态 ==================
    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val msg: String) : UiState()
        data class Error(val error: String) : UiState()
    }

    private val _uiState = MutableLiveData<UiState>(UiState.Idle)
    val uiState: LiveData<UiState> = _uiState

    // ================== 设备状态（核心） ==================
    private val _deviceStateMap = MutableLiveData<Map<DeviceType, Boolean>>()
    val deviceStateMap: LiveData<Map<DeviceType, Boolean>> = _deviceStateMap

    // 内部可变Map
    private val currentStateMap = mutableMapOf<DeviceType, Boolean>()

    // ================== 控制设备 ==================
    fun controlDevice(device: DeviceType, isOn: Boolean) {

        viewModelScope.launch {

            _uiState.value = UiState.Loading

            val status = if (isOn) "on" else "off"

            val result = when (device) {
                DeviceType.AIR_CONDITIONER -> repository.sendAirConditionerCommend(status)
                DeviceType.TV -> repository.sendTvCommend(status)
                DeviceType.LIVING_LAMP -> repository.sendLivingLampCommend(status)
            }

            result.onSuccess {
                _uiState.value = UiState.Success(it)

                // 更新本地状态
                currentStateMap[device] = isOn
                _deviceStateMap.value = currentStateMap.toMap()
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "未知错误")
            }
        }
    }

    // ================== 初始化加载 ==================
    fun loadDeviceStatus() {
        loadAirConditioner()
        loadTv()
        loadLamp()
    }

    private fun loadAirConditioner() {
        viewModelScope.launch {
            val result = repository.getAirConditionerMessage()
            result.onSuccess {
                if (it.isNotEmpty()) {
                    val isOn = it[0].msg == "on"
                    updateState(DeviceType.AIR_CONDITIONER, isOn)
                }
            }
        }
    }

    private fun loadTv() {
        viewModelScope.launch {
            val result = repository.getTVMessage()
            result.onSuccess {
                if (it.isNotEmpty()) {
                    val isOn = it[0].msg == "on"
                    updateState(DeviceType.TV, isOn)
                }
            }
        }
    }

    private fun loadLamp() {
        viewModelScope.launch {
            val result = repository.getLivingLampMessage()
            result.onSuccess {
                if (it.isNotEmpty()) {
                    val isOn = it[0].msg == "on"
                    updateState(DeviceType.LIVING_LAMP, isOn)
                }
            }
        }
    }

    // ================== 更新状态统一入口 ==================
    private fun updateState(device: DeviceType, isOn: Boolean) {
        currentStateMap[device] = isOn
        _deviceStateMap.value = currentStateMap.toMap()
    }
}