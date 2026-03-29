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

    /**
     * 发送获取空调数据的请求
     * */
    suspend fun sendAirConditionerCommend(msg: String): Result<String> {
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
                Log.d(TAG, "空调发送响应: code=${response.code}, message=${response.message}, data=${response.data}")
                if (response.code == 0) {
                    Result.success("发送成功")
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Log.e(TAG, "空调发送异常", e)
                Result.failure(e)
            }
        }
    }

    /***
     *获取空调数据
     */
    suspend fun getAirConditionerMessage(): Result<List<MessageItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMessages(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "at002",
                    type = 3
                )
                Log.d(TAG, "空调完整响应: $response")
                Log.d(TAG, "空调响应: code=${response.code}, message=${response.message}, data=${response.data}")
                Log.d(TAG, "空调data类型: ${response.data?.javaClass}")
                if (response.code == 0) {
                    Result.success(response.data ?: emptyList())
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Log.e(TAG, "空调获取异常", e)
                Result.failure(e)
            }
        }
    }

    /**
     * 获取空调是否在线
     * */
    suspend fun getAirConditionerOnline(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getOnline(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "at002",
                    type = 3
                )
                if (response.code == 0) {
                    Result.success(response.data ?: false)
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * 改变热水器状态
     * */
    suspend fun sendWaterHeaterCommend(msg: String) : Result<String>{
        return withContext(Dispatchers.IO){
            try {
                val response = api.pushMessage(
                    PushRequest(
                        uid = "6bf8a0c06931436296f8845bf2069fc3",
                        topic = "water002",
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

    /**
     * 获取热水器状态
     * */
    suspend fun getWaterHeaterMessage() : Result<List<MessageItem>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMessages(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "water002",
                    type = 3
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

    /**
     * 获取热水器是否在线
     * */
    suspend fun getWaterHeaterOnline(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getOnline(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "water002",
                    type = 3
                )
                if (response.code == 0) {
                    Result.success(response.data ?: false)
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    suspend fun sendTvCommend(msg : String) : Result<String>{
        return withContext(Dispatchers.IO){
            try {
                val response = api.pushMessage(
                    PushRequest(
                        uid = "6bf8a0c06931436296f8845bf2069fc3",
                        topic = "TV002",
                        type = 3,
                        msg = msg,
                        wemsg = "设备状态：$msg"
                    )
                )
                Log.d(TAG, "电视发送响应: code=${response.code}, message=${response.message}, data=${response.data}")
                if (response.code == 0) {
                    Result.success("发送成功")
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Log.e(TAG, "电视发送异常", e)
                Result.failure(e)
            }
        }
    }

    suspend fun getTVMessage() : Result<List<MessageItem>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMessages(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "TV002",
                    type = 3
                )
                Log.d(TAG, "电视响应: code=${response.code}, message=${response.message}, data=${response.data}")
                if (response.code == 0) {
                    Result.success(response.data ?: emptyList())
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Log.e(TAG, "电视获取异常", e)
                Result.failure(e)
            }
        }
    }

    /**
     * 获取电视是否在线
     * */
    suspend fun getTVOnline(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getOnline(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "TV002",
                    type = 3
                )
                if (response.code == 0) {
                    Result.success(response.data ?: false)
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun sendLivingLampCommend(msg : String) : Result<String>{
        return withContext(Dispatchers.IO){
            try {
                val response = api.pushMessage(
                    PushRequest(
                        uid = "6bf8a0c06931436296f8845bf2069fc3",
                        topic = "lamp002",
                        type = 3,
                        msg = msg,
                        wemsg = "设备状态：$msg"
                    )
                )
                Log.d(TAG, "客厅灯发送响应: code=${response.code}, message=${response.message}, data=${response.data}")
                if (response.code == 0) {
                    Result.success("发送成功")
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Log.e(TAG, "客厅灯发送异常", e)
                Result.failure(e)
            }
        }
    }

    suspend fun getLivingLampMessage() : Result<List<MessageItem>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMessages(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "lamp002",
                    type = 3
                )
                Log.d(TAG, "客厅灯响应: code=${response.code}, message=${response.message}, data=${response.data}")
                if (response.code == 0) {
                    Result.success(response.data ?: emptyList())
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Log.e(TAG, "客厅灯获取异常", e)
                Result.failure(e)
            }
        }
    }

    /**
     * 获取客厅灯光是否在线
     * */
    suspend fun getLivingLampOnline(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getOnline(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "lamp002",
                    type = 3
                )
                if (response.code == 0) {
                    Result.success(response.data ?: false)
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun sendBadRoomLampCommend(msg : String) : Result<String>{
        return withContext(Dispatchers.IO){
            try {
                val response = api.pushMessage(
                    PushRequest(
                        uid = "6bf8a0c06931436296f8845bf2069fc3",
                        topic = "fant002",
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

    suspend fun getBadRoomLampMessage() : Result<List<MessageItem>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMessages(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "fant002",
                    type = 3
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

    /**
     * 获取卧室灯光是否在线
     * */
    suspend fun getBadRoomLampOnline(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getOnline(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "fant002",
                    type = 3
                )
                if (response.code == 0) {
                    Result.success(response.data ?: false)
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun sendCurtainCommend(msg : String) : Result<String>{
        return withContext(Dispatchers.IO){
            try {
                val response = api.pushMessage(
                    PushRequest(
                        uid = "6bf8a0c06931436296f8845bf2069fc3",
                        topic = "air002",
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

    suspend fun getCurtainMessage() : Result<List<MessageItem>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMessages(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "air002",
                    type = 3
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

    /**
     * 获取窗帘是否在线
     * */
    suspend fun getCurtainOnline(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getOnline(
                    uid = "6bf8a0c06931436296f8845bf2069fc3",
                    topic = "air002",
                    type = 3
                )
                if (response.code == 0) {
                    Result.success(response.data ?: false)
                } else {
                    Result.failure(Exception(response.message))
                }

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}