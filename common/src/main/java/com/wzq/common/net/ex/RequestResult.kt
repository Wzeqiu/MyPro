package com.wzq.common.net.ex

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 17:49
 * Version: 1.0
 * Description: java类作用描述
 */
sealed class RequestResult<out DATA> {
    class Success<DATA>(val data: DATA) : RequestResult<DATA>()

    class Failure(val code: Int, val message: String) : RequestResult<Nothing>()

    object Cancel : RequestResult<Nothing>()
}


