package com.shang.taipeitour.view.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.FragmentDetailBinding
import com.shang.taipeitour.responses.AttractionsResponse
import com.shang.taipeitour.utility.NavigationAnimation
import com.shang.taipeitour.view.webview.WebViewFragment


class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        const val DATA = "DATA"
    }

    private lateinit var mBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_detail,
            container,
            false
        )
        mBinding.data = arguments?.getSerializable(DATA) as? AttractionsResponse.Data
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = mBinding.data?.name

        LinearSnapHelper().attachToRecyclerView(mBinding.rvImage)
        mBinding.rvImage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.rvImage.adapter = TourImageAdapter().apply {
            this.submitList(mBinding.data?.images)
        }

        mBinding.tvUrl.setOnClickListener {
            val bundle = bundleOf(
                WebViewFragment.URL to mBinding.data?.url,
                WebViewFragment.TITLE to mBinding.data?.name
            )
            findNavController().navigate(
                R.id.webViewFragment,
                bundle,
                NavigationAnimation.getSlideIn()
            )
        }

        mBinding.tvOfficialSite.setOnClickListener {
            val bundle = bundleOf(
                WebViewFragment.URL to mBinding.data?.official_site,
                WebViewFragment.TITLE to mBinding.data?.name
            )
            findNavController().navigate(
                R.id.webViewFragment,
                bundle,
                NavigationAnimation.getSlideIn()
            )
        }
    }
}