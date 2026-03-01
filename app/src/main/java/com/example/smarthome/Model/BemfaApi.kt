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

    // 获取消息
    @GET("va/getmsg")
    suspend fun getMessages(
        @Query("uid") uid: String,
        @Query("topic") topic: String,
        @Query("type") type: Int,
        @Query("num") num: Int = 1
    ): BaseResponse<List<MessageItem>>
}