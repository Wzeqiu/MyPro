package com.wzq.common.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/21 17:49
 * Version: 1.0
 * Description: java类作用描述
 */
abstract class BaseVBAdapter<VB : ViewBinding> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var _viewBinding: VB
    protected val viewBinding get() = _viewBinding
    private var itemOnClickListener: ItemOnClickListener? = null

    /**
     * 创建ViewBinding
     */
    protected abstract fun createViewBinding(): VB

    /**
     * 绑定数据
     */
    protected abstract fun onBindData(viewBinding: ViewBinding, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        _viewBinding = createViewBinding()
        val viewHolder = object : RecyclerView.ViewHolder(viewBinding.root) {}
        bindViewClick(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(viewBinding, position)
    }

    /**
     * 绑定点击事件
     */
    private fun bindViewClick(viewHolder: RecyclerView.ViewHolder) {
        itemOnClickListener?.let { itemOnClickListener ->
            viewHolder.itemView.setOnClickListener {
                itemOnClickListener.onItemClick(this, it, viewHolder.adapterPosition)
            }
        }
    }


    /**
     * 设置点击事件监听
     */
    fun setOnItemOnClickListener(itemOnClickListener: ItemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener
    }


    /**
     * 点击事件接口
     */
    interface ItemOnClickListener {
        fun onItemClick(adapter: RecyclerView.Adapter<*>, view: View, position: Int)
    }

}