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
    private var viewBinding: VB.(T) -> Unit = { }
) : BaseQuickAdapter<T, BaseHolder<VB>>(0) {
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<VB> {
        val viewBinding: VB = inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseHolder(viewBinding)
    }

    override fun convert(holder: BaseHolder<VB>, item: T) {
        viewBinding.invoke(holder.viewBinding, item)
    }
}

open class DefaultMultiAdapter<T : MultiItemEntity> : BaseMultiItemQuickAdapter<T, BaseHolder<ViewBinding>>() {

    private val viewBindings: HashMap<Int,
            Pair<(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> ViewBinding,
                    BaseHolder<ViewBinding>.(T) -> Unit>> = HashMap()

    open fun <VB : ViewBinding> addItemType(
        type: Int,
        inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
        viewBinding: BaseHolder<VB>.(T) -> Unit = {}
    ) {
        super.addItemType(type, 0)
        viewBindings[type] = Pair(inflate, viewBinding)
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ViewBinding> {
        val pair = viewBindings[viewType]
        val viewBinding = pair?.first?.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseHolder(viewBinding!!)
    }

    override fun convert(holder: BaseHolder<ViewBinding>, item: T) {
        viewBindings[holder.itemViewType]?.second?.invoke(holder, item)
    }


}


