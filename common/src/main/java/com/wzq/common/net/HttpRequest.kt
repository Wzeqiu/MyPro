package com.wzq.common.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 11:41
 * Version: 1.0
 * Description: java类作用描述
 */
class HttpRequest private constructor() {


    companion object {
        private val instance: HttpRequest by lazy { HttpRequest() }

        /**
         * 获取 retrofit 实例
         */
        @JvmStatic
        fun getRetrofit(): Retrofit {
            return instance.retrofit
        }

        /**
         * 设置配置信息
         */
        @JvmStatic
        private fun setBuilder(builder: Builder) {
            instance.setBuilderConfig(builder)
        }
    }


    internal val retrofit: Retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { createRetrofit() }

    private lateinit var builder: Builder

    private fun setBuilderConfig(builder: Builder?) {
        if (builder == null) {
            throw RuntimeException("HttpRequest Builder Cannot be null")
        }
        this.builder = builder
        this.builder.interceptors += HttpLoggingInterceptor().apply {
            level = if (builder.debug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }


    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(builder.baseUrl)
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
        okBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okBuilder.readTimeout(20, TimeUnit.SECONDS)
        okBuilder.writeTimeout(20, TimeUnit.SECONDS)
        okBuilder.retryOnConnectionFailure(true)
        builder.interceptors.forEach {
            okBuilder.addInterceptor(it)
        }
        okBuilder.sslSocketFactory(getSocketFactory(), trustManager)
        okBuilder.hostnameVerifier { _, _ -> true }
        return okBuilder.build()
    }


    private fun getSocketFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(trustManager), SecureRandom())
        return sslContext.socketFactory
    }

    //不验证ssl证书
    private val trustManager: X509TrustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }


    class Builder {
        /**
         * 请求地址公共部分
         */
        internal var baseUrl = ""

        /**
         * debug
         */
        internal var debug = true

        /**
         * 拦截器
         */
        internal val interceptors: MutableList<Interceptor> = mutableListOf()

        fun setBaseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            this.interceptors += interceptor
            return this
        }

        fun setDebug(debug: Boolean): Builder {
            this.debug = debug
            return this
        }

        fun build() {
            setBuilder(this)
        }
    }


}


