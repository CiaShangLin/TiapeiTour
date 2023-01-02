package com.shang.taipeitour.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.DialogSwitchLanduageBinding
import com.shang.taipeitour.databinding.ItemSwitchLanguageBinding
import com.shang.taipeitour.utility.Language

class SwitchLanguageDialog(context: Context, private val mCallback: Callback?) :
    Dialog(context, R.style.SwitchLanguageDialog) {

    interface Callback {
        fun switchLanguage(language: Language)
    }

    object SwitchLanguageDiffUtil : ItemCallback<Language>() {
        override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
            return oldItem.language == newItem.language
        }
    }

    private lateinit var mBinding: DialogSwitchLanduageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_switch_landuage,
            null,
            false
        )
        setContentView(mBinding.root)

        mBinding.rvLanguage.layoutManager = LinearLayoutManager(context)
        mBinding.rvLanguage.adapter = SwitchLanguageAdapter().apply {
            this.submitList(Language.values().toList())
        }
    }

    inner class SwitchLanguageAdapter :
        ListAdapter<Language, SwitchLanguageViewHolder>(SwitchLanguageDiffUtil) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SwitchLanguageViewHolder {
            val binding = DataBindingUtil.inflate<ItemSwitchLanguageBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_switch_language,
                parent,
                false
            )
            return SwitchLanguageViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SwitchLanguageViewHolder, position: Int) {
            getItem(position)?.let {
                holder.bind(it)
            }
        }
    }

    inner class SwitchLanguageViewHolder(private val mBinding: ItemSwitchLanguageBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.tvLanguage.setOnClickListener {
                mBinding.language?.let {
                    mCallback?.switchLanguage(it)
                    dismiss()
                }
            }
        }

        fun bind(language: Language) {
            mBinding.language = language
            mBinding.executePendingBindings()
        }
    }


}