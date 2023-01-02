package com.shang.taipeitour.utility

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.shang.taipeitour.R
import com.shang.taipeitour.network.ApiState

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("bindImageByGlide")
    fun bindImageByGlide(
        imageView: ImageView,
        url: String?
    ) {
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.icon_tour_placeholder)
            .error(R.drawable.icon_tour_error)
            .into(imageView)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("bindApiState")
    fun bindApiState(textView: TextView, apiState: ApiState) {
        when (apiState) {
            ApiState.CAN_LOADING,
            ApiState.LOADING -> {
                textView.setText(R.string.api_state_loading)
            }
            ApiState.ERROR -> {
                textView.setText(R.string.api_state_error)
            }
            ApiState.END -> {
                textView.setText(R.string.api_state_end)
            }
        }
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("setBottomLine")
    fun setBottomLine(textView: TextView,showBottomLine:Boolean){
        if(showBottomLine){
            textView.paint.flags = Paint.UNDERLINE_TEXT_FLAG
            textView.paint.isAntiAlias = true
        }
    }
}