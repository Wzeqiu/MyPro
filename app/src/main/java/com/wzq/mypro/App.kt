package com.wzq.mypro

import android.app.Application
import com.wzq.common.net.HttpRequest

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 15:58
 * Version: 1.0
 * Description: java类作用描述
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        HttpRequest.Builder()
            .setBaseUrl("https://api.apiopen.top/")
            .setDebug(true)
            .build()

    }
}