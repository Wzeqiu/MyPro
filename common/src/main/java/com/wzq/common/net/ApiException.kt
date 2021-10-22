package com.wzq.common.net

import java.lang.RuntimeException

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 11:27
 * Version: 1.0
 * Description: java类作用描述
 */
class ApiException(resultCode: Int, error: String) : RuntimeException() {
    private val USER_LOGINOUT = 403 // 用户身份失效

    var errorMessage: String? = null
    var code = 0

    init {
        getApiExceptionMessage(resultCode, error)
    }


    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private fun getApiExceptionMessage(code: Int, error: String): String? {
        this.code = code
        errorMessage = when (code) {
            USER_LOGINOUT -> "用户不存在"
            else -> error
        }
        return errorMessage
    }
}