package github.xuqk.bindingitemtestdemo

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

open class DBViewHolder(view: View) : BaseViewHolder(view) {

    val binding: ViewDataBinding?
        get() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as? ViewDataBinding
}