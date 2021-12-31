package com.wzq.common.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import org.jetbrains.annotations.NotNull

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/26 16:56
 * Version: 1.0
 * Description: java类作用描述
 */
inline fun <reified T> eventBusPost(@NotNull value: T) {
    LiveEventBus.get<T>(value!!::class.java.toString()).post(value)
}

inline fun <reified T> eventBusPostDelay(@NotNull value: T, delay: Long = 0) {
    LiveEventBus.get<T>(value!!::class.java.toString()).postDelay(value, delay)
}


inline fun <reified T> LifecycleOwner.eventBusObserver(key: Class<T>, observer: Observer<T>) {
    eventBusObserver(key, this, observer)
}

inline fun <reified T> eventBusObserver(key: Class<T>, lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    LiveEventBus.get<T>(key.toString()).observe(lifecycleOwner, observer)
}

inline fun <reified T> eventBusObserver(key: Class<T>, observer: Observer<T>) {
    LiveEventBus.get<T>(key.toString()).observeForever(observer)
}

inline fun <reified T> eventBusRemoveObserver(key: Class<T>, observer: Observer<T>) {
    LiveEventBus.get<T>(key.toString()).removeObserver(observer)
}