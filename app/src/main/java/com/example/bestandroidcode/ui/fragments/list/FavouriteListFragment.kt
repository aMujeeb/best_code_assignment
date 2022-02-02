package com.example.bestandroidcode.ui.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.FavouritesFragmentBinding
import com.example.bestandroidcode.ui.activities.main.MainActivityViewModel
import com.example.bestandroidcode.ui.adapters.FavoriteAdapter
import com.example.bestandroidcode.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteListFragment: BaseFragment() {

    private val mFavoriteViewModel : FavouriteViewModel by viewModels()
    private var mListFragBinding : FavouritesFragmentBinding? = null
    private val mMainActivityViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mListFragBinding = FavouritesFragmentBinding.inflate(inflater, container, false)
        return mListFragBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.your_fav_cats)
        viewManager = LinearLayoutManager(requireContext())
        mListFragBinding!!.lstFavorite.layoutManager = viewManager

        mFavoriteViewModel.mAddedFavourites.observe(viewLifecycleOwner, Observer {
            viewAdapter = FavoriteAdapter(it)
            mListFragBinding!!.lstFavorite.adapter = viewAdapter
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                mMainActivityViewModel.mIsAddedSuccessfully.value = false
                this.remove()
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}