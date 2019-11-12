package github.xuqk.bindingitemtestdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import github.xuqk.bindingitemtestdemo.databinding.ActivityMainBinding
import github.xuqk.bindingitemtestdemo.databinding.AdapterPlayItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)
    }
    private val listAdapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initList()

        binding.btnPrev.setOnClickListener {
            listAdapter.changeToPrev()
        }

        binding.btnNext.setOnClickListener {
            listAdapter.changeToNext()
        }
    }

    private fun initList() {
        listAdapter.run {
            setOnLoadMoreListener({vm.getNextPage {
                listAdapter.addData(it)
                listAdapter.loadMoreComplete()
            }}, binding.rv)

            setOnItemClickListener { adapter, view, position ->
                listAdapter.changeToIndex(position)
            }
        }
        binding.rv.run {
            itemAnimator = null
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
        vm.getNextPage {
            listAdapter.setNewData(it)
        }
    }
}

//private class DBListAdapter :
//    DBAdapter<AdapterPlayItemBinding, Item, DBViewHolder>(R.layout.adapter_play_item, mutableListOf()) {
//    override fun convert(helper: DBViewHolder, binding: AdapterPlayItemBinding, item: Item) {
//        binding.item = item
//    }
//
//    /**
//     * 当前播放曲目index，没有返回-1
//     */
//    fun currentPlayingIndex() = data.indexOfFirst { it.playing }
//
//    fun changeToIndex(index: Int) {
//        val currentIndex = currentPlayingIndex()
//        if (index == currentIndex) return
//
//        if (currentIndex != -1) {
//            data[currentIndex].playing = false
//        }
//        data[index].playing = true
//    }
//
//    fun changeToNext() {
//        val currentIndex = currentPlayingIndex()
//        if (currentIndex == data.size - 1) {
//            Toast.makeText(mContext, "已经是最后一首了", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        changeToIndex(currentPlayingIndex() + 1)
//    }
//
//    fun changeToPrev() {
//        val currentIndex = currentPlayingIndex()
//        if (currentIndex == 0) {
//            Toast.makeText(mContext, "已经是第一首了", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        changeToIndex(currentPlayingIndex() - 1)
//    }
//}

private class ListAdapter :
    BaseQuickAdapter<Item, DBViewHolder>(R.layout.adapter_play_item, mutableListOf()) {
    override fun convert(helper: DBViewHolder, item: Item) {
        helper.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_artist, item.subTitle)
            .setVisible(R.id.iv_play_status, item.playing)
    }

    /**
     * 当前播放曲目index，没有返回-1
     */
    fun currentPlayingIndex() = data.indexOfFirst { it.playing }

    fun changeToIndex(index: Int) {
        val currentIndex = currentPlayingIndex()
        if (index == currentIndex) return

        if (currentIndex != -1) {
            data[currentIndex].playing = false
            notifyItemChanged(currentIndex)
        }
        data[index].playing = true
        notifyItemChanged(index)
        notifyDataSetChanged()
    }

    fun changeToNext() {
        val currentIndex = currentPlayingIndex()
        if (currentIndex == data.size - 1) {
            Toast.makeText(mContext, "已经是最后一首了", Toast.LENGTH_SHORT).show()
            return
        }

        changeToIndex(currentPlayingIndex() + 1)
    }

    fun changeToPrev() {
        val currentIndex = currentPlayingIndex()
        if (currentIndex == 0) {
            Toast.makeText(mContext, "已经是第一首了", Toast.LENGTH_SHORT).show()
            return
        }

        changeToIndex(currentPlayingIndex() - 1)
    }
}

class MainViewModel : ViewModel() {
    private var nextPage = 1

    fun getNextPage(success: (data: MutableList<Item>) -> Unit) {
        val data = mutableListOf<Item>()
        for (i in 0..19) {
            data.add(Item("第 $nextPage 页", "这是本页第 $i 首歌", false))
        }
        ++nextPage
        success(data)
    }
}