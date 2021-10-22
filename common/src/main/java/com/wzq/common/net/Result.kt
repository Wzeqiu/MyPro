package com.wzq.common.net

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 17:49
 * Version: 1.0
 * Description: java类作用描述
 */
sealed class Result<DATA>

data class Success<DATA>(val data: DATA) : Result<DATA>()

data class Failure(val code: Int, val message: String) : Result<Any>()
