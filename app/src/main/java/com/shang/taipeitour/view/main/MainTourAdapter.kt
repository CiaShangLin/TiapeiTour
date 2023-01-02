package com.shang.taipeitour.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.ItemMainTourBinding
import com.shang.taipeitour.responses.AttractionsResponse

class MainTourAdapter :
    ListAdapter<AttractionsResponse.Data, MainTourViewHolder>(MainTourDiffUtil) {

    object MainTourDiffUtil : ItemCallback<AttractionsResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: AttractionsResponse.Data,
            newItem: AttractionsResponse.Data
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AttractionsResponse.Data,
            newItem: AttractionsResponse.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTourViewHolder {
        val binding = DataBindingUtil.inflate<ItemMainTourBinding>(
            LayoutInflater.from(parent.context), R.layout.item_main_tour, parent, false
        )
        return MainTourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTourViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}