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

    private val _uiState = MutableLiveData<String>()
    val uiState: LiveData<String> = _uiState

    private val _messages = MutableLiveData<List<MessageItem>>()
    val messages: LiveData<List<MessageItem>> = _messages

    fun controlDevice(msg: String) {
        viewModelScope.launch {
            _uiState.value = "发送中..."

            val result = repository.sendCommand(msg)

            result.onSuccess {
                _uiState.value = it
            }.onFailure {
                _uiState.value = "失败：${it.message}"
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            val result = repository.getLatestMessage()

            result.onSuccess {
                _messages.value = it
            }
        }
    }
}