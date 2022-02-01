package com.example.bestandroidcode.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.MainFragmentBinding
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.ui.activities.main.FavouriteCallBack
import com.example.bestandroidcode.ui.activities.main.MainActivity
import com.example.bestandroidcode.ui.fragments.BaseFragment
import com.example.bestandroidcode.ui.fragments.advanced.AdvanceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(), View.OnClickListener {

    private lateinit var iFavouriteCallBack : FavouriteCallBack

    companion object {
        fun newInstance(favouriteCallBack : FavouriteCallBack) :MainFragment {
            var instance = MainFragment()
            instance.iFavouriteCallBack = favouriteCallBack
            return instance
        }
    }

    private val viewModel: MainViewModel by viewModels()
    private var mMainFragmentBinding : MainFragmentBinding? = null

    var currentCatObject: Cat? = null

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
                    iFavouriteCallBack.isFavouriteSelected(false)
                    Glide.with(this@MainFragment)
                        .load(it.imageUrl)
                        .into(mMainFragmentBinding!!.ivCat)
                }
                it?.isAlreadySaved == true -> {
                    iFavouriteCallBack.isFavouriteSelected(false)
                }
                it?.isAdded == true -> {
                    iFavouriteCallBack.isFavouriteSelected(true)
                }
            }
        })
    }

    override fun onClick(p0: View?) {
        if(p0 == mMainFragmentBinding!!.btnLoadCat) {
            viewModel.requestRandomCatImage()
        } else if(p0 == mMainFragmentBinding!!.btnProUser) {
            val advanceFragment = AdvanceFragment()

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, AdvanceFragment.newInstance(activity as MainActivity))
            transaction.addToBackStack("")
            transaction.commit()
        }
    }

    override fun addToFavoriteList() {
        Log.d("BestApp", "Add at Main")
        viewModel.saveCatRecord()
    }
}