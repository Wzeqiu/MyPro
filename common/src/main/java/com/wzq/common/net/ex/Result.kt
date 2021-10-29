package com.wzq.common.net.ex

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 17:49
 * Version: 1.0
 * Description: java类作用描述
 */
internal sealed class Result<DATA>

internal data class Success<DATA>(val data: DATA) : Result<DATA>()

internal data class Failure<Any>(val code: Int, val message: String) : Result<Any>()

internal data class Cancel<Any>(val data: String) : Result<Any>()
