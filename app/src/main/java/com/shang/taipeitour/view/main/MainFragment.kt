package com.shang.taipeitour.view.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.shang.taipeitour.R
import com.shang.taipeitour.databinding.FragmentMainBinding
import com.shang.taipeitour.network.ApiState
import com.shang.taipeitour.network.Response
import com.shang.taipeitour.utility.Language
import com.shang.taipeitour.utility.setLoadMore
import com.shang.taipeitour.view.dialog.SwitchLanguageDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding
    private val mViewModel by viewModel<MainViewModel>()
    private val mMainTourAdapter by lazy {
        MainTourAdapter()
    }
    private val mMainApiStateAdapter by lazy {
        MainTourApiStateAdapter()
    }
    private val mConcatAdapter by lazy {
        ConcatAdapter(
            mMainTourAdapter, mMainApiStateAdapter
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_main,
            container,
            false
        )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvTour()
        initViewModel()
    }

    private fun initRvTour(){
        mBinding.rvTour.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvTour.adapter = mConcatAdapter
        mBinding.rvTour.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        mBinding.rvTour.setLoadMore(lifecycle) {
            mViewModel.loadMoreAttractions()
        }
    }

    private fun initViewModel(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.attractionDataFlow.collect {
                    val beforeSize = mMainTourAdapter.itemCount
                    mMainTourAdapter.submitList(it) {
                        if (beforeSize == 0) {
                            mBinding.rvTour.scrollToPosition(0)
                        }
                    }
                }
            }
        }

        mViewModel.attractionApiStateLiveData.observe(viewLifecycleOwner){
            mMainApiStateAdapter.setApiState(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_translate -> {
                SwitchLanguageDialog(requireContext(),object :SwitchLanguageDialog.Callback{
                    override fun switchLanguage(language: Language) {
                        mViewModel.switchLanguage(language.language)
                    }
                }).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}