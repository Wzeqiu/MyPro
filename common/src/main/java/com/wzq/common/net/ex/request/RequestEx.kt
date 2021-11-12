package com.wzq.common.net.ex.request

import com.wzq.common.net.ApiException
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.ex.RequestResult
import com.wzq.common.net.requestError

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/29 17:32
 * Version: 1.0
 * Description: 具体网络请求
 */

suspend fun <DATA> request(
    block: suspend () -> BaseResponse<DATA>
): RequestResult<DATA> {
    var requestResult: RequestResult
    try {
        runCatching {
            block.invoke()
        }.mapCatching {
            if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
        }.onSuccess {
            requestResult = RequestResult.Success(it)
        }.onFailure {
            val httpError = requestError(it)
            requestResult = RequestResult.Failure(httpError.first, httpError.second ?: "")
        }
    } finally {
        requestResult = RequestResult.Cancel
    }
    return requestResult
}