package com.wzq.mypro

import com.wzq.common.net.BaseResponse
import com.wzq.common.net.HttpRequest
import retrofit2.http.GET

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 11:24
 * Version: 1.0
 * Description: java类作用描述
 */

val netServer: Net by lazy { HttpRequest.createServer(Net::class.java) }

@JvmSuppressWildcards
interface Net {
    @GET("/virtualcurrency/config")
    suspend fun test(): BaseResponse<List<Any>>
}