package com.shang.taipeitour.utility

import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.util.*

@Suppress("UNCHECKED_CAST")
fun <T> Activity.lazyExtra(name: String, defaultValue: T): Lazy<T> {
    return lazy {
        when (defaultValue) {
            is String -> intent.getStringExtra(name) as T ?: defaultValue
            is Int -> intent.getIntExtra(name, defaultValue) as T
            is Boolean -> intent.getBooleanExtra(name, defaultValue) as T
            else -> throw RuntimeException("Intent don't have this type please check your defaultValue")
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.lazyExtra(name: String, defaultValue: T): Lazy<T> {
    return lazy {
        when (defaultValue) {
            is String -> requireArguments().getString(name, defaultValue) as T ?: defaultValue
            is Int -> requireArguments().getInt(name, defaultValue) as T ?: defaultValue
            is Boolean -> requireArguments().getBoolean(name, defaultValue) as T ?: defaultValue
            else -> throw RuntimeException("Argument don't have this type please check your defaultValue")
        }
    }
}


/**
 * Glide四個圓角
 */
val Int.roundedCorners: RequestOptions
    get() = RequestOptions.bitmapTransform(RoundedCorners(this.dp))

/**
 * RecyclerView滾到底後呼叫callback
 * @param lifecycle = Activity或Fragment的生命週期
 * @param callback = 滾到底會呼叫
 */
fun RecyclerView.setLoadMore(lifecycle: Lifecycle, callback: () -> Unit) {

    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = when (recyclerView.layoutManager) {
                is GridLayoutManager -> recyclerView.layoutManager as GridLayoutManager
                else -> recyclerView.layoutManager as LinearLayoutManager
            }
            val itemCount = recyclerView.adapter?.itemCount ?: 0
            if (itemCount != 0 && (layoutManager.findLastCompletelyVisibleItemPosition() == itemCount - 1)) {
                callback.invoke()
            }
        }
    }

    lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            addOnScrollListener(onScrollListener)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            removeOnScrollListener(onScrollListener)
        }
    })
}

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp: Int get() = toFloat().dp.toInt()