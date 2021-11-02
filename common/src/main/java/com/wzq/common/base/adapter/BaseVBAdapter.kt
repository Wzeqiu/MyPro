package com.wzq.common.base.adapter

import android.view.LayoutInflater
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


class BaseVBViewHolder<VB : ViewBinding>(val viewBinding: VB) : RecyclerView.ViewHolder(viewBinding.root)

class BaseVBAdapter<DATA, VB : ViewBinding>(
    private val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
    private var viewBinding: VB.(DATA) -> Unit = { }
) : RecyclerView.Adapter<BaseVBViewHolder<VB>>() {
    private val datas: MutableList<DATA> = mutableListOf()
    private var itemOnClickListener: ItemOnClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVBViewHolder<VB> {
        val viewBinding: VB = inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = BaseVBViewHolder(viewBinding)
        bindViewClick(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holderVB: BaseVBViewHolder<VB>, position: Int) {
        viewBinding.invoke(holderVB.viewBinding, datas[position]!!)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun setDatas(datas: MutableList<DATA>) {
        this.datas.clear()
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }

    fun addDatas(datas: MutableList<DATA>) {
        this.datas.addAll(datas)
        notifyDataSetChanged()
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

    public interface ItemOnClickListener {
        fun onItemClick(adapter: RecyclerView.Adapter<*>, view: View, position: Int)
    }
}

