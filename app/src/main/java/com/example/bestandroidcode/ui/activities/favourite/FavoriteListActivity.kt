package com.example.bestandroidcode.ui.activities.favourite

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.ActivityFavoriteListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListActivity : AppCompatActivity() {

    private var mFavouriteListBinding : ActivityFavoriteListBinding? = null
    private val mFavoriteViewModel : FavouriteViewModel by viewModels()

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFavouriteListBinding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(mFavouriteListBinding!!.root)

        title = getString(R.string.your_fav_cats)

        viewManager = LinearLayoutManager(this)

        mFavouriteListBinding!!.rvFavorite.layoutManager = viewManager
    }

    override fun onResume() {
        super.onResume()
        mFavoriteViewModel.mAddedFavourites.observe(this, Observer {
            viewAdapter = FavoriteAdapter(it)
            mFavouriteListBinding!!.rvFavorite.adapter = viewAdapter
        })
    }
}