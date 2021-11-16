package com.wzq.common.net.ex.request

import android.util.Log
import com.wzq.common.net.ApiException
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.ex.RequestResult
import com.wzq.common.net.requestError
import kotlinx.coroutines.*

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/29 17:32
 * Version: 1.0
 * Description: 具体网络请求
 */
internal fun <DATA> request(
    coroutineScope: CoroutineScope,
    block: suspend () -> BaseResponse<DATA>,
    result: suspend CoroutineScope.(RequestResult<DATA>) -> Unit,
): Job {
    return coroutineScope.launch(Dispatchers.IO) {
        Log.e("RequestEx", "onStart   ${Thread.currentThread().name}")
        runCatching {
            block.invoke()
        }.mapCatching {
            if (it.isSuccess()) it.result else throw ApiException(it.code, it.message)
        }.onSuccess {
            Log.e("RequestEx", "onSuccess   ${Thread.currentThread().name}")
            result.invoke(this, RequestResult.Success(it))
        }.onFailure {
            Log.e("RequestEx", "Failure result${result}   ${Thread.currentThread().name}")
            val httpError = requestError(it)
            result.invoke(this, RequestResult.Failure(httpError.first, httpError.second ?: ""))
        }
    }
}