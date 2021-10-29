package com.wzq.common.net.ex.request

import android.app.Service
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.wzq.common.net.ApiException
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.ex.Failure
import com.wzq.common.net.ex.Result
import com.wzq.common.net.ex.Success
import com.wzq.common.net.requestError
import kotlinx.coroutines.*

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/29 17:32
 * Version: 1.0
 * Description: java类作用描述
 */

internal fun <DATA : Any> Service.request(
    block: suspend () -> BaseResponse<DATA>,
    result: (Result<DATA>) -> Unit = {}
): Job {
    return GlobalScope.launch {
        runCatching {
            block.invoke()
        }.mapCatching {
            if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                result.invoke(Success(it))
            }
        }.onFailure {
            val httpError = requestError(it)
            withContext(Dispatchers.Main) {
                result.invoke(Failure(httpError.first, httpError.second ?: ""))
            }
        }
    }
}