package com.example.smarthome.Model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BemfaApi {
    // 推送消息
    @POST("va/postJsonMsg")
    suspend fun pushMessage(
        @Body request: PushRequest
    ): BaseResponse<Int>

    // 获取消息 - 使用具体的类型避免泛型擦除问题
    @GET("va/getmsg")
    suspend fun getMessages(
        @Query("uid") uid: String,
        @Query("topic") topic: String,
        @Query("type") type: Int,
    ): BaseResponse<List<MessageItem>>
}