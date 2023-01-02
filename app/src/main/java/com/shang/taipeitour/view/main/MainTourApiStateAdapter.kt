package com.shang.taipeitour.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.ItemMainTourApiStateBinding
import com.shang.taipeitour.network.ApiState

class MainTourApiStateAdapter :
    RecyclerView.Adapter<MainTourApiStateAdapter.MainTourApiStateViewHolder>() {

    private var mApiState = ApiState.CAN_LOADING

    fun setApiState(apiState: ApiState) {
        mApiState = apiState
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTourApiStateViewHolder {
        val binding = DataBindingUtil.inflate<ItemMainTourApiStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_main_tour_api_state,
            parent,
            false
        )
        return MainTourApiStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTourApiStateViewHolder, position: Int) {
        holder.bind(mApiState)
    }

    override fun getItemCount(): Int = 1

    inner class MainTourApiStateViewHolder(private val mBinding: ItemMainTourApiStateBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(apiState: ApiState) {
            mBinding.apiState = apiState
            mBinding.executePendingBindings()
        }
    }

}