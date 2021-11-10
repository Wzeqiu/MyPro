package com.wzq.common.net.ex

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 17:49
 * Version: 1.0
 * Description: java类作用描述
 */
 sealed class Result {
    class Success<DATA>(val data: DATA) : Result()

    class Failure(val code: Int, val message: String) : Result()

    object Cancel : Result()
}


