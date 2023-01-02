package com.shang.taipeitour.view.main

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.ItemMainTourBinding
import com.shang.taipeitour.responses.AttractionsResponse
import com.shang.taipeitour.utility.NavigationAnimation
import com.shang.taipeitour.view.detail.DetailFragment

class MainTourViewHolder(private val mBinding: ItemMainTourBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    init {
        mBinding.ivArrow.setOnClickListener {
            val bundle = bundleOf(
                DetailFragment.DATA to mBinding.data
            )
            it.findNavController()
                .navigate(R.id.detailFragment, bundle, NavigationAnimation.getSlideIn())
        }
    }

    fun bind(data: AttractionsResponse.Data) {
        mBinding.data = data
        mBinding.executePendingBindings()
    }
}