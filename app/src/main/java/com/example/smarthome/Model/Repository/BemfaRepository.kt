package com.example.smarthome.Model.Repository

import android.util.Log
import com.example.smarthome.Model.MessageItem
import com.example.smarthome.Model.PushRequest
import com.example.smarthome.Model.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BemfaRepository {
    private val api = RetrofitClient.api
    private val TAG = "BemfaRepository"

    suspend fun sendCommand(msg: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.pushMessage(
                    PushRequest(
                        uid = "6bf8a0c06931436296f8845bf2069fc3",
                        topic = "at002",
                        type = 3,
                        msg = msg,
                        wemsg = "设备状态：$msg"
                    )
                )

                if (response.code == 0) {
                    Result.success("发送成功")
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getLatestMessage(): Result<List<MessageItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMessages(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "at002",
                    type = 3,
                    num = 5
                )
                Log.d(TAG,"request code is ==="+response.code)
                Log.d(TAG,"request msg is ==="+response.data)
                if (response.code == 0) {
                    Result.success(response.data ?: emptyList())
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}