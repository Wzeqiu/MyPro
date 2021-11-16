package com.wzq.common.net

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 11:05
 * Version: 1.0
 * Description: java类作用描述
 */
class BaseResponse<DATA>(
    val result: DATA,
    val message: String,
    val code: Int
) {
    fun isSuccess() = code == 200

}
