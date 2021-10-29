package com.wzq.common.net

import com.wzq.common.net.ex.ServerErrorNotifyInterface
import java.lang.RuntimeException
import java.util.*

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 11:27
 * Version: 1.0
 * Description: java类作用描述
 */

class ApiException(val code: Int, message: String) : RuntimeException(message) {

    private val apiCode = mutableMapOf(
        Pair(403, "没有登录"),
    )

    fun getApiError(): Pair<Int, String?> {
        errorCode(code)
        return Pair(code, apiCode[code] ?: message)
    }


    /**
     * 异常状态全局通知
     */
    private fun errorCode(code: Int) {
        val notify = ServiceLoader.load(ServerErrorNotifyInterface::class.java)
        val it: Iterator<ServerErrorNotifyInterface> = notify.iterator()
        while (it.hasNext()) {
            val service = it.next()
            service.errorNotify(code)
        }

    }
}