package com.wzq.mypro

import com.wzq.common.net.BaseResponse
import com.wzq.common.net.HttpRequest

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 11:24
 * Version: 1.0
 * Description: java类作用描述
 */

val netServer: Net by lazy { HttpRequest.getRetrofit().create(Net::class.java) }

@JvmSuppressWildcards
interface Net {
    suspend fun test(): BaseResponse<String>
}