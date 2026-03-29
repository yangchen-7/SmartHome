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

class DeviceViewModel : ViewModel() {

    private val repository = BemfaRepository()
    private val TAG = "DeviceViewModel"

    private val currentOnlineMap = mutableMapOf<DeviceType, Boolean>()
    private val _deviceOnlineMap = MutableStateFlow<Map<DeviceType, Boolean>>(emptyMap())
    val deviceOnlineMap: StateFlow<Map<DeviceType, Boolean>> = _deviceOnlineMap.asStateFlow()

    init {
        loadDeviceOnlineStatus()
    }

    fun loadDeviceOnlineStatus() {
        currentOnlineMap.clear()
        _deviceOnlineMap.value = emptyMap()
        loadOnlineSingle(DeviceType.AIR_CONDITIONER) { repository.getAirConditionerOnline() }
        loadOnlineSingle(DeviceType.TV) { repository.getTVOnline() }
        loadOnlineSingle(DeviceType.LIVING_LAMP) { repository.getLivingLampOnline() }
        loadOnlineSingle(DeviceType.WATER_HEATER) { repository.getWaterHeaterOnline() }
        loadOnlineSingle(DeviceType.BEDROOM_LAMP) { repository.getBadRoomLampOnline() }
        loadOnlineSingle(DeviceType.CURTAIN) { repository.getCurtainOnline() }
    }


    private fun loadOnlineSingle(
        device: DeviceType,
        request: suspend () -> Result<Boolean>
    ) {
        viewModelScope.launch {
            val result = request()
            result.onSuccess { isOnline ->
                Log.d(TAG, "设备 $device 在线状态: $isOnline")
                updateOnlineState(device, isOnline)
            }.onFailure {
                Log.e(TAG, "设备 $device 在线状态获取失败: ${it.message}", it)
                updateOnlineState(device, false)
            }
        }
    }


    private fun updateOnlineState(device: DeviceType, isOnline: Boolean) {
        currentOnlineMap[device] = isOnline
        _deviceOnlineMap.value = currentOnlineMap.toMap()
    }
}