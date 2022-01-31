package com.example.bestandroidcode.ui.favourite

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestandroidcode.databinding.ActivityFavoriteListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListActivity : AppCompatActivity() {

    private var mFavouriteListBinding : ActivityFavoriteListBinding? = null

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFavouriteListBinding = ActivityFavoriteListBinding.inflate(layoutInflater)

        title = "Your Favorite Cats"

        val sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE)
        val currentFavoriteList = sharedPref.getStringSet("FAVORITE_LIST", HashSet())

        viewManager = LinearLayoutManager(this)
        viewAdapter = FavoriteAdapter(currentFavoriteList!!.toTypedArray())

        mFavouriteListBinding!!.rvFavorite.layoutManager = viewManager
        mFavouriteListBinding!!.rvFavorite.adapter = viewAdapter
    }
}