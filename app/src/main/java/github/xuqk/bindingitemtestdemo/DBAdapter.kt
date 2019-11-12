package github.xuqk.bindingitemtestdemo

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter

abstract class DBAdapter<B : ViewDataBinding, T, K : DBViewHolder>(layoutResId: Int, data: MutableList<T> = mutableListOf()) : BaseQuickAdapter<T, K>(layoutResId, data) {

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false) ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }

    @Suppress("UNCHECKED_CAST")
    override fun convert(helper: K, item: T) {
        convert(helper, helper.binding as B, item)
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding?.executePendingBindings()
    }

    abstract fun convert(helper: K, binding: B, item: T)
}