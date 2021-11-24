package com.wzq.common.basemvvm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.viewbinding.ViewBinding
import kotlin.math.abs

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/24 11:43
 * Version: 1.0
 * Description: java类作用描述
 */
open class BaseWindow<VB : ViewBinding> constructor(val context: Context, private val inflate: (inflate: LayoutInflater) -> VB) {
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    /**
     * 窗口属性
     */
    private val layoutParams = WindowManager.LayoutParams()
    protected val handler = Handler(Looper.getMainLooper())

    protected lateinit var viewBinding: VB

    /**
     * 屏幕大小
     */
    protected val screenSize = Point()


    /**
     * 滑动偏移
     */
    private val offset = 15

    /**
     * 当前是否显示
     */
    private var show = false

    init {
        windowManager.defaultDisplay.getSize(screenSize)
        defaultLayoutParams()
        createVB()
    }

    protected fun getWindowManager(): WindowManager {
        return windowManager
    }

    /**
     * 初始化布局信息
     */
    private fun defaultLayoutParams() {
        layoutParams.gravity = Gravity.START or Gravity.TOP
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.format = PixelFormat.TRANSPARENT
        layoutParams.type = getSupportedWindowType()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
    }

    protected fun getLayoutParams(): WindowManager.LayoutParams {
        return layoutParams

    }

    private fun getSupportedWindowType(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
    }

    /**
     * 加载布局
     */
    private fun createVB() {
        if (!this::viewBinding.isInitialized) {
            viewBinding = inflate.invoke(LayoutInflater.from(context))
        }
    }

    protected fun getContent(): ViewGroup {
        return viewBinding.root as ViewGroup
    }

    /**
     * 显示窗口
     */
    fun show() {
        if (getContent().parent == null) {
            windowManager.addView(getContent(), layoutParams)
        }
        show = true
    }

    protected fun isShow(): Boolean {
        return show
    }

    protected fun updateViewLayout() {
        windowManager.updateViewLayout(getContent(), layoutParams)
    }

    /**
     * 隐藏弹框
     */
    fun dismiss() {
        if (isMainThread()) {
            onDestroyView()
        } else {
            handler.post { onDestroyView() }
        }
    }

    /**
     * 窗口中移除View
     */
    protected open fun onDestroyView() {
        if (this::viewBinding.isInitialized) {
            getContent().parent?.let {
                windowManager.removeViewImmediate(getContent())
            }
        }
        show = false
    }

    /**
     * 判断当前线程是否主线程
     */
    private fun isMainThread(): Boolean {
        return Looper.getMainLooper() == Looper.myLooper()
    }


    /**
     * 拖动窗口
     */
    @SuppressLint("ClickableViewAccessibility")
    protected fun setDragWindow() {
        var touchRawY = 0.0f
        var touchRawX = 0.0f
        var isSlide = false

        var offsetY: Float
        var offsetX: Float
        viewBinding.root.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchRawY = event.rawY
                    touchRawX = event.rawX
                    isSlide = false
                }
                MotionEvent.ACTION_MOVE -> {
                    offsetY = event.rawY - touchRawY
                    offsetX = event.rawX - touchRawX
                    if (isSlide || abs(offsetY) >= offset || abs(offsetX) >= offset) {
                        layoutParams.y += offsetY.toInt()
                        layoutParams.x -= offsetX.toInt()
                        windowManager.updateViewLayout(v, layoutParams)
                        touchRawY = event.rawY
                        touchRawX = event.rawX
                        isSlide = true
                    }
                }
            }
            return@setOnTouchListener isSlide
        }
    }
}