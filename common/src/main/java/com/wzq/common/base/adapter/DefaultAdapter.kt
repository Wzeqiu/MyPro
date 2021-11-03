package com.wzq.common.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/2 15:36
 * Version: 1.0
 * Description: java类作用描述
 */

class BaseHolder<VB : ViewBinding>(val viewBinding: VB) : BaseViewHolder(viewBinding.root)

open class DefaultAdapter<T, VB : ViewBinding>(
    private val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
    private var block: VB.(T) -> Unit = { }
) : BaseQuickAdapter<T, BaseHolder<VB>>(0) {

    open fun setItemLayout(block: VB.(T) -> Unit = {}) {
        this.block = block
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<VB> {
        val viewBinding: VB = inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseHolder(viewBinding)
    }


    override fun convert(holder: BaseHolder<VB>, item: T) {
        block.invoke(holder.viewBinding, item)
    }
}


class ViewBingPair<T : MultiItemEntity, VB : ViewBinding>(
    val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
    val block: VB.(T) -> Unit = {}
)

open class DefaultMultiAdapter<T : MultiItemEntity> : BaseMultiItemQuickAdapter<T, BaseHolder<ViewBinding>>() {
    private var viewBindings: HashMap<Int, ViewBingPair<T, ViewBinding>> = HashMap()

    open fun <VB : ViewBinding> addItemLayout(
        type: Int,
        inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
        block: VB.(T) -> Unit = {}
    ) {
        super.addItemType(type, 0)
        viewBindings[type] = ViewBingPair(inflate, block as ViewBinding.(T) -> Unit)
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ViewBinding> {
        val pair = viewBindings[viewType]
        val viewBinding = pair?.inflate?.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseHolder(viewBinding!!)
    }

    override fun convert(holder: BaseHolder<ViewBinding>, item: T) {
        viewBindings[holder.itemViewType]?.block?.invoke(holder.viewBinding, item)
    }
}



