package com.wzq.mypro

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.wzq.common.base.BaseVBActivity
import com.wzq.common.base.adapter.*
import com.wzq.common.net.ex.request.request
import com.wzq.common.utils.*
import com.wzq.mypro.databinding.ActivityMainBinding
import com.wzq.mypro.databinding.ItemAdapterBinding
import com.wzq.mypro.databinding.ItemAdapterTwoBinding
import kotlinx.coroutines.Job

class MainActivity : BaseVBActivity<ActivityMainBinding>() {
    val datas = mutableListOf<MultiItem<Any>>()
    var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleBarTitle("")
        eventBusObserver(String::class.java) {

            Log.e("data", "eventBusObserver is $it")
        }
        eventBusObserver(Integer::class.java) {

            Log.e("data", "eventBusObserver is $it")
        }
        eventBusObserver(Integer::class.java) {

            Log.e("data", "eventBusObserver is $it")
        }
        viewBinding.tvContent.isVisible = true
        viewBinding.tvContent.setSingleOnClickListener {
            job = if (job != null) {
                job!!.cancel()
                null
            } else {

                request({ netServer.test() }, isShowDialog = true) {
                    startActivity(TwoActivity::class.java)
                }
            }

        }.onClickUp()
        viewBinding.lin.onClickUp()


        val adapter = initDefaultAdapter<String, ItemAdapterBinding> {
            tvName.text = it
        }

        val adapter1 = initDefaultMultiAdapter {
            addItemLayout<String, ItemAdapterBinding>(0, ItemAdapterBinding::inflate) {
                tvName.text = it
            }

            addItemLayout<Int, ItemAdapterTwoBinding>(1, ItemAdapterTwoBinding::inflate) {
                tvName.text = it.toString()
            }
        }

        for (i in 0..100) {
            datas.add(MultiItem(0, i.toString()))
            datas.add(MultiItem(1, i * 10))
        }
        adapter1.setNewInstance(datas)
        viewBinding.rv.adapter = adapter1
    }
}

class MyAdapter : DefaultAdapter<String, ItemAdapterBinding>({
    tvName.text = it
}) {
    override fun setOnItemClick(v: View, position: Int) {
        super.setOnItemClick(v, position)
    }

    override fun setList(list: Collection<String>?) {
        super.setList(list)
    }


}


class MyBaseAdapter : DefaultMultiAdapter() {
    init {
        addItemLayout<MultiItem<String>, ItemAdapterBinding>(0, ItemAdapterBinding::inflate) {
            tvName.text = it.data
        }

        addItemLayout<MultiItem<Int>, ItemAdapterTwoBinding>(1, ItemAdapterTwoBinding::inflate) {
            tvName.text = it.data.toString()
        }
    }
}
