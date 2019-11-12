package github.xuqk.bindingitemtestdemo

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class Item(title: String, subTitle: String, playing: Boolean) : BaseObservable() {

//    @get:Bindable
    var title: String = title
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.title)
//        }

//    @get:Bindable
    var subTitle: String = subTitle
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.subTitle)
//        }

//    @get:Bindable
    var playing: Boolean = playing
//        set(value) {
//            field = value
//            Log.d("测试", value.toString())
//            notifyPropertyChanged(BR.playing)
//        }
}