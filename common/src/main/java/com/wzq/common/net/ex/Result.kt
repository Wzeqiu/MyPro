package com.wzq.common.net.ex

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 17:49
 * Version: 1.0
 * Description: java类作用描述
 */
internal sealed class Result {
    internal class Success(val data: Any) : Result()

    internal class Failure(val code: Int, val message: String) : Result()

    internal object Cancel : Result()
}


