package com.wzq.common.net

import android.util.Log
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


const val UNAUTHORIZED = 401
const val FORBIDDEN = 403
const val NOT_FOUND = 404
const val REQUEST_TIMEOUT = 408
const val INTERNAL_SERVER_ERROR = 500
const val BAD_GATEWAY = 502
const val SERVICE_UNAVAILABLE = 503
const val GATEWAY_TIMEOUT = 504


inline fun requestError(
    throwable: Throwable,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> }
) {
    val message: String
    var code = 0
    if (throwable is HttpException) {
        code = throwable.code()
        Log.e("OKhttp", "code is $code")
        message = when (code) {
            GATEWAY_TIMEOUT -> "当前网络不可用，请检查网络情况"
            UNAUTHORIZED,
            FORBIDDEN,
            NOT_FOUND,
            REQUEST_TIMEOUT,
            INTERNAL_SERVER_ERROR,
            BAD_GATEWAY,
            SERVICE_UNAVAILABLE -> "服务器繁忙,请稍后重试"
            else -> "服务器繁忙,请稍后重试"
        }
    } else if (throwable is JsonParseException || throwable is JSONException) {
        throwable.message?.let { Log.e("error", it) }
        message = "数据解析错误"
    } else if (throwable is SocketTimeoutException || throwable is ConnectTimeoutException) {
        message = "网络请求超时,请稍后重试"
    } else if (throwable is UnknownHostException) {
        message = "当前网络不可用，请检查网络情况"
    } else if (throwable is IllegalArgumentException) {
        message = throwable.message.toString()
    } else if (throwable is ApiException) {
        message = throwable.message.toString()
    } else {
        message = throwable.message.toString()
//            message = "服务器开小差了";
    }
    error.invoke(code, message)
}