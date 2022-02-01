package com.example.bestandroidcode.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.MainActivityBinding
import com.example.bestandroidcode.ui.activities.favourite.FavoriteListActivity
import com.example.bestandroidcode.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Best Android Code does not need comments to explain the code
    private var mMainActivityBinding: MainActivityBinding? = null
    private val mMainActivityViewModel: MainActivityViewModel by viewModels()
    private var mOptionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mMainActivityBinding!!.root)

        mMainActivityViewModel.mIsAddedSuccessfully.observe(this, Observer {
            if (mOptionsMenu != null) {
                val favoriteItem = mOptionsMenu!!.findItem(R.id.action_favorite)
                if (it) {
                    favoriteItem.setIcon(R.drawable.baseline_favorite_black_24)
                } else {
                    favoriteItem.setIcon(R.drawable.baseline_favorite_border_black_24)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        mOptionsMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            val f : BaseFragment? = navHostFragment?.childFragmentManager?.fragments?.get(0) as BaseFragment?
            f?.addToFavoriteList()
            true
        }

        R.id.action_favorite_list -> {
            val intent = Intent(this, FavoriteListActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}