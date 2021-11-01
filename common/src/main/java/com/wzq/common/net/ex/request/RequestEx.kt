package com.wzq.common.net.ex.request

import android.util.Log
import com.wzq.common.net.ApiException
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.requestError
import kotlinx.coroutines.*

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/29 17:32
 * Version: 1.0
 * Description: java类作用描述
 */

fun <DATA : Any> request(
    block: suspend () -> BaseResponse<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    return GlobalScope.launch(Dispatchers.IO) {
        try {
            runCatching {
                block.invoke()
            }.mapCatching {
                if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    success.invoke(it)
                }
            }.onFailure {
                val httpError = requestError(it)
                withContext(Dispatchers.Main) {
                    error.invoke(httpError.first, httpError.second ?: "")
                }
            }
        } finally {
            Log.e("request", "request cancel")
        }
    }
}