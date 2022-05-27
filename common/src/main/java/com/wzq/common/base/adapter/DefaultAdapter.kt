package com.wzq.common.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wzq.common.utils.reflectInflate

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/2 15:36
 * Version: 1.0
 * Description: java类作用描述
 */

class BaseHolder<VB : ViewBinding>(val viewBinding: VB) : BaseViewHolder(viewBinding.root)

open class DefaultAdapter<T, VB : ViewBinding>(
    private var block: VB.(T) -> Unit = { }
) : BaseQuickAdapter<T, BaseHolder<VB>>(0) {

    open fun setItemLayout(block: VB.(T) -> Unit = {}) {
        this.block = block
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<VB> {
        val viewBinding: VB = reflectInflate(1, LayoutInflater.from(parent.context), parent, false)
        return BaseHolder(viewBinding)
    }


    override fun convert(holder: BaseHolder<VB>, item: T) {
        block.invoke(holder.viewBinding, item)
    }
}

/**
 * 直接初始化
 */
fun <T, VB : ViewBinding> initDefaultAdapter(
    block: VB.(T) -> Unit = { }
) = DefaultAdapter(block)

open class MultiItem<T>(override val itemType: Int, open val data: T) : MultiItemEntity

class ViewBingPair<T, VB : ViewBinding>(
    val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
    val block: VB.(T) -> Unit = {}
)


open class DefaultMultiAdapter : BaseMultiItemQuickAdapter<MultiItem<Any>, BaseViewHolder>() {
    private var viewBindings: HashMap<Int, ViewBingPair<Any, ViewBinding>> = HashMap()

    open fun <T, VB : ViewBinding> addItemLayout(
        type: Int,
        inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
        block: VB.(T) -> Unit = {}
    ) {
        super.addItemType(type, 0)
        viewBindings[type] = ViewBingPair(inflate, block as ViewBinding.(Any) -> Unit)
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val pair = viewBindings[viewType]
        val viewBinding = pair?.inflate?.invoke(LayoutInflater.from(parent.context), parent, false)
            ?: return BaseViewHolder(View(parent.context))
        return BaseHolder(viewBinding)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItem<Any>) {
        viewBindings[holder.itemViewType]?.block?.invoke(
            (holder as BaseHolder<*>).viewBinding,
            item.data
        )
    }
}

/**
 * 直接初始化多布局
 */
fun initDefaultMultiAdapter(
    block: DefaultMultiAdapter.() -> Unit = { }
) = DefaultMultiAdapter().apply {
    block.invoke(this)
}


