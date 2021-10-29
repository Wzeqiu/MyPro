package com.wzq.common.net

import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 15:26
 * Version: 1.0
 * Description: java类作用描述
 */

private val httpCode = mutableMapOf(
    Pair(204, "请求被受理但没有资源可以返回"),
    Pair(400, "请求报文语法有误，服务器无法识别"),
    Pair(401, "请求需要认证"),
    Pair(403, "请求的对应资源禁止被访问"),
    Pair(404, "服务器无法找到对应资源"),
    Pair(500, "服务器内部错误"),
    Pair(503, "服务器正忙"),

    Pair(1000, "请求失败"),
    Pair(1001, "数据解析错误"),
    Pair(1002, "请求超时,请稍后重试"),
    Pair(1003, "请求地址错误"),
    Pair(1004, "参数非法"),
)

fun requestError(throwable: Throwable): Pair<Int, String?> {
    return if (throwable is HttpException) {
        Pair(throwable.code(), httpCode[throwable.code()] ?: throwable.message())
    } else if (throwable is JsonParseException || throwable is JSONException) {
        Pair(1001, httpCode[1001])
    } else if (throwable is SocketTimeoutException || throwable is ConnectTimeoutException) {
        Pair(1002, httpCode[1002])
    } else if (throwable is UnknownHostException) {
        Pair(1003, httpCode[1003])
    } else if (throwable is IllegalArgumentException) {
        Pair(1004, httpCode[1004])
    } else if (throwable is ApiException) {
        throwable.getApiError()
    } else {
        Pair(1000, throwable.message)
    }

}