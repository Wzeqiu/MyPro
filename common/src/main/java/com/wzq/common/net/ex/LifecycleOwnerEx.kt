package com.wzq.common.net.ex

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.wzq.common.net.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
) {
    lifecycleScope.launch {
        runCatching {
            block.invoke()
        }.mapCatching {
            if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                result.invoke(Success(it))
            }
        }.onFailure {
            requestError(it, error = { code: Int, message: String ->
                withContext(Dispatchers.Main) {
                    result.invoke(Failure(code, message))
                }
            })
        }
    }
}