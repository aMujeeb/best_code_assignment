package com.example.bestandroidcode.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.MainFragmentBinding
import com.example.bestandroidcode.ui.activities.main.MainActivityViewModel
import com.example.bestandroidcode.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: MainViewModel by viewModels()
    private val mMainActivityViewModel: MainActivityViewModel by activityViewModels()
    private var mMainFragmentBinding : MainFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mMainFragmentBinding =
            MainFragmentBinding.inflate(inflater, container, false)
        return mMainFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMainFragmentBinding!!.btnLoadCat.setOnClickListener(this)
        mMainFragmentBinding!!.btnProUser.setOnClickListener(this)

        viewModel.mMainViewState.observe(viewLifecycleOwner, Observer {
            when {
                it?.isLoading == true -> {
                    mMainFragmentBinding!!.mMainProgress.visibility = View.VISIBLE
                }
                it?.imageUrl != null ->{
                    mMainFragmentBinding!!.mMainProgress.visibility = View.GONE
                    Glide.with(this@MainFragment)
                        .load(it.imageUrl)
                        .into(mMainFragmentBinding!!.ivCat)
                }
                it?.isAlreadySaved == true -> {
                    mMainActivityViewModel.mIsAddedSuccessfully.value = true
                }
                it?.isAdded == true -> {
                    mMainActivityViewModel.mIsAddedSuccessfully.value = true
                }
            }
        })
    }

    override fun onClick(p0: View?) {
        if(p0 == mMainFragmentBinding!!.btnLoadCat) {
            viewModel.requestRandomCatImage()
        } else if(p0 == mMainFragmentBinding!!.btnProUser) {
            findNavController().navigate(R.id.action_mainFragment_to_advanceFragment)
        }
    }

    override fun addToFavoriteList() {
        Log.d("BestApp", "Add at Main")
        viewModel.saveCatRecord()
    }
}