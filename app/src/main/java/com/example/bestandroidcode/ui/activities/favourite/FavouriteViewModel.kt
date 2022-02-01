package com.example.bestandroidcode.ui.activities.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bestandroidcode.data.db.dao.CatDao
import com.example.bestandroidcode.model.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    mCatDao: CatDao
): ViewModel(){

    var mAddedFavourites : LiveData<List<Cat>> = mCatDao.getAllFavouriteCats()
}