package com.example.bestandroidcode.ui.activities.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.MainActivityBinding
import com.example.bestandroidcode.ui.fragments.advanced.AdvanceFragment
import com.example.bestandroidcode.ui.activities.favourite.FavoriteListActivity
import com.example.bestandroidcode.ui.fragments.BaseFragment
import com.example.bestandroidcode.ui.fragments.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , FavouriteCallBack {

    // Best Android Code does not need comments to explain the code
    private var mMainActivityBinding : MainActivityBinding? = null

    private val mMainActivityViewModel : MainActivityViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

    private var mOptionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mMainActivityBinding!!.root)

        sharedPref = getSharedPreferences("default", Context.MODE_PRIVATE)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(mMainActivityBinding!!.container.id, MainFragment.newInstance(this))
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        mOptionsMenu = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {

            val f: BaseFragment? = supportFragmentManager.findFragmentById(mMainActivityBinding!!.container.id) as BaseFragment?
            f?.addToFavoriteList()

            /*if (f is MainFragment) {
                if (f.currentCatObject != null) {
                    val catImageUrl = f.currentCatObject!!.url

                    if (catImageUrl.isNotBlank()) {
                        if (currentFavoriteList!!.contains(catImageUrl)) {
                            currentFavoriteList!!.remove(catImageUrl)
                        } else {
                            currentFavoriteList!!.add(catImageUrl)
                        }

                        val e: SharedPreferences.Editor = sharedPref.edit()
                        e.remove("FAVORITE_LIST")
                        e.commit()

                        e.putStringSet("FAVORITE_LIST", currentFavoriteList)
                        e.commit()

                        refreshFavoriteButton(catImageUrl)
                    }
                }
            }

            if (f is AdvanceFragment) {
                if (f.currentCatObject != null) {
                    val catImageUrl = f.currentCatObject!!.url

                    if (catImageUrl.isNotBlank()) {
                        if (currentFavoriteList!!.contains(catImageUrl)) {
                            currentFavoriteList!!.remove(catImageUrl)
                        } else {
                            currentFavoriteList!!.add(catImageUrl)
                        }

                        val e: SharedPreferences.Editor = sharedPref.edit()
                        e.remove("FAVORITE_LIST")
                        e.commit()

                        e.putStringSet("FAVORITE_LIST", currentFavoriteList)
                        e.commit()

                        refreshFavoriteButton(catImageUrl)
                    }
                }
            }*/

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

    override fun isFavouriteSelected(isSelected: Boolean) {
        if (mOptionsMenu != null) {
            val favoriteItem = mOptionsMenu!!.findItem(R.id.action_favorite)
            if (isSelected) {
                favoriteItem.setIcon(R.drawable.baseline_favorite_black_24)
            } else {
                favoriteItem.setIcon(R.drawable.baseline_favorite_border_black_24)
            }
        }
    }
}