package com.shang.taipeitour.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.ItemDetailTourImageBinding
import com.shang.taipeitour.responses.AttractionsResponse

class TourImageAdapter :
    ListAdapter<AttractionsResponse.Data.Image, TourImageAdapter.TourImageViewHolder>(
        TourImageDiffUtil
    ) {

    object TourImageDiffUtil : ItemCallback<AttractionsResponse.Data.Image>() {
        override fun areItemsTheSame(
            oldItem: AttractionsResponse.Data.Image,
            newItem: AttractionsResponse.Data.Image
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AttractionsResponse.Data.Image,
            newItem: AttractionsResponse.Data.Image
        ): Boolean {
            return oldItem.src == newItem.src
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourImageViewHolder {
        val binding = DataBindingUtil.inflate<ItemDetailTourImageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_detail_tour_image,
            parent, false
        )
        return TourImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class TourImageViewHolder(private val mBinding: ItemDetailTourImageBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(image: AttractionsResponse.Data.Image) {
            mBinding.image = image
            mBinding.executePendingBindings()
        }
    }
}