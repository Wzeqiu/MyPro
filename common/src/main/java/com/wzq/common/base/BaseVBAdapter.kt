package com.wzq.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.wzq.common.R

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/21 17:49
 * Version: 1.0
 * Description: java类作用描述
 */
abstract class BaseVBAdapter<DATA, VB : ViewBinding>(
    private val datas: MutableList<DATA>,
    private val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemOnClickListener: ItemOnClickListener? = null

    /**
     * 绑定数据
     */
    protected abstract fun onBindData(viewBinding: VB, data: DATA, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding: VB = inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = object : RecyclerView.ViewHolder(viewBinding.root) {}
        viewHolder.itemView.setTag(R.id.recyclerview_item_tag_id, viewBinding)
        bindViewClick(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewBinding = holder.itemView.getTag(R.id.recyclerview_item_tag_id)
        onBindData(viewBinding as VB, datas[position]!!, position)
    }

    override fun getItemCount(): Int {
        return datas.size
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