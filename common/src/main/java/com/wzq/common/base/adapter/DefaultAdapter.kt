package com.wzq.common.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wzq.common.utils.reflectInflate
import com.wzq.common.utils.reflectInflateBinding

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/2 15:36
 * Version: 1.0
 * Description: java类作用描述
 */

class BaseHolder<VB : ViewBinding>(val viewBinding: VB) : BaseViewHolder(viewBinding.root)


/**
 * 唯一布局
 */
open class DefaultAdapter<T, VB : ViewBinding>(
    private var block: VB.(T) -> Unit = { }
) : BaseQuickAdapter<T, BaseHolder<VB>>(View.NO_ID) {

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


/**
 * 多布局实现类
 */
open class DefaultMultiAdapter : BaseMultiItemQuickAdapter<MultiItem<Any>, BaseViewHolder>() {
    var viewBindings: HashMap<Int, ViewBingPair<Any, ViewBinding>> = HashMap()

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val pair = viewBindings[viewType]
        val viewBinding =
            pair?.vBClass?.reflectInflateBinding(LayoutInflater.from(parent.context), parent, false)
                ?: return BaseViewHolder(View(parent.context))
        return BaseHolder(viewBinding)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItem<Any>) {
        viewBindings[holder.itemViewType]?.block?.invoke(
            (holder as BaseHolder<*>).viewBinding,
            item.data
        )
    }

    @PublishedApi
    internal fun addItemTypeN(type: Int, @LayoutRes layoutResId: Int) =
        super.addItemType(type, layoutResId)
}

/**
 * 添加多布局
 */
inline fun <T, reified VB : ViewBinding> DefaultMultiAdapter.addItemLayout(
    type: Int,
    noinline block: VB.(T) -> Unit = {}
) {
    addItemTypeN(type, View.NO_ID)
    viewBindings[type] = ViewBingPair(VB::class.java, block as ViewBinding.(Any) -> Unit)
}

open class MultiItem<T>(override val itemType: Int, open val data: T) : MultiItemEntity

class ViewBingPair<T, VB : ViewBinding>(
    val vBClass: Class<*>,
    val block: VB.(T) -> Unit = {}
)

/**
 * 直接初始化多布局
 */
fun initDefaultMultiAdapter(
    block: DefaultMultiAdapter.() -> Unit = { }
) = DefaultMultiAdapter().apply {
    block.invoke(this)
}


