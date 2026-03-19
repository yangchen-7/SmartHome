package com.example.smarthome.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.Model.MessageItem
import com.example.smarthome.Model.Repository.BemfaRepository
import kotlinx.coroutines.launch

class DeviceViewModel : ViewModel() {

    private val repository = BemfaRepository()

    // UI状态
    private val _uiState = MutableLiveData<String>()
    val uiState: LiveData<String> = _uiState

    // 设备状态
    private val _airConditionerStatus = MutableLiveData<Boolean>()
    val airConditionerStatus: LiveData<Boolean> = _airConditionerStatus

    private val _tvStatus = MutableLiveData<Boolean>()
    val tvStatus: LiveData<Boolean> = _tvStatus

    private val _livingLampStatus = MutableLiveData<Boolean>()
    val livingLampStatus: LiveData<Boolean> = _livingLampStatus

    // 控制设备
    fun controlDevice(deviceType: String, status: String) {
        viewModelScope.launch {
            _uiState.value = "发送中..."

            val result = when (deviceType) {
                "airConditioner" -> repository.sendAirConditionerCommend(status)
                "tv" -> repository.sendTvCommend(status)
                "livingLamp" -> repository.sendLivingLampCommend(status)
                else -> null
            }

            result?.onSuccess {
                _uiState.value = it
                // 更新本地状态
                when (deviceType) {
                    "airConditioner" -> _airConditionerStatus.value = status == "on"
                    "tv" -> _tvStatus.value = status == "on"
                    "livingLamp" -> _livingLampStatus.value = status == "on"
                }
            }?.onFailure {
                _uiState.value = "失败：${it.message}"
            }
        }
    }

    // 加载设备状态
    fun loadDeviceStatus() {
        loadAirConditionerStatus()
        loadTvStatus()
        loadLivingLampStatus()
    }

    // 加载空调状态
    private fun loadAirConditionerStatus() {
        viewModelScope.launch {
            val result = repository.getAirConditionerMessage()
            result.onSuccess {
                if (it.isNotEmpty()) {
                    _airConditionerStatus.value = it[0].msg == "on"
                }
            }
        }
    }

    // 加载电视状态
    private fun loadTvStatus() {
        viewModelScope.launch {
            val result = repository.getTVMessage()
            result.onSuccess {
                if (it.isNotEmpty()) {
                    _tvStatus.value = it[0].msg == "on"
                }
            }
        }
    }

    // 加载客厅灯状态
    private fun loadLivingLampStatus() {
        viewModelScope.launch {
            val result = repository.getLivingLampMessage()
            result.onSuccess {
                if (it.isNotEmpty()) {
                    _livingLampStatus.value = it[0].msg == "on"
                }
            }
        }
    }
}