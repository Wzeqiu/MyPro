package com.wzq.common.net.ex

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.wzq.common.net.*
import kotlinx.coroutines.*

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 17:31
 * Version: 1.0
 * Description: java类作用描述
 */
internal fun <DATA : Any> LifecycleOwner.requestResult(
    block: suspend () -> BaseResponse<DATA>,
    result: (Result<DATA>) -> Unit = {}
): Job {
    return lifecycleScope.launch {
        try {
            runCatching {
                delay(10_000)
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
        } finally {
            result.invoke(Cancel(""))
        }
    }

}